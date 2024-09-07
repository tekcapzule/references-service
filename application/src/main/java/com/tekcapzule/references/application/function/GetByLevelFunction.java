package com.tekcapzule.references.application.function;

import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.references.application.config.AppConfig;
import com.tekcapzule.references.application.function.input.GetByLevelInput;
import com.tekcapzule.references.domain.model.References;
import com.tekcapzule.references.domain.service.ReferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetByLevelFunction implements Function<Message<GetByLevelInput>, Message<List<References>>> {

    private final ReferenceService referenceService;

    private final AppConfig appConfig;

    public GetByLevelFunction(final ReferenceService referenceService, final AppConfig appConfig) {
        this.referenceService = referenceService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<References>> apply(Message<GetByLevelInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<References> courses = new ArrayList<>();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetByLevelInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get course by level Function -Topic Code:%s", getInput.getTopicCode()));
            courses = referenceService.findAllByLevel(getInput.getTopicCode(), getInput.getLevel());
            if (courses.isEmpty()) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(courses, responseHeaders);
    }
}