package com.tekcapzule.references.application.function;

import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.references.application.config.AppConfig;
import com.tekcapzule.references.application.function.input.GetByDurationInput;
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
public class GetByDurationFunction implements Function<Message<GetByDurationInput>, Message<List<References>>> {

    private final ReferenceService referenceService;

    private final AppConfig appConfig;

    public GetByDurationFunction(final ReferenceService referenceService, final AppConfig appConfig) {
        this.referenceService = referenceService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<List<References>> apply(Message<GetByDurationInput> getInputMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        List<References> courses = new ArrayList<>();

        String stage = appConfig.getStage().toUpperCase();

        try {
            GetByDurationInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get course by Duration Function -Duration :%s", getInput.getDuration()));
            courses = referenceService.findAllByDuration(getInput.getTopicCode(), getInput.getDuration());
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