package com.tekcapzule.references.application.function;

import com.tekcapzule.references.application.config.AppConfig;
import com.tekcapzule.references.application.function.input.GetByTopicInput;
import com.tekcapzule.references.domain.model.References;
import com.tekcapzule.references.domain.service.ReferenceService;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.Stage;
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
public class GetByTopicFunction implements Function<Message<GetByTopicInput>, Message<List<References>>> {

    private final ReferenceService referenceService;

    private final AppConfig appConfig;

    public GetByTopicFunction(final ReferenceService referenceService, final AppConfig appConfig) {
        this.referenceService = referenceService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<List<References>> apply(Message<GetByTopicInput> findByTopicInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        List<References> references =new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            GetByTopicInput getByTopicInput = findByTopicInputMessage.getPayload();
            log.info(String.format("Entering search by topic Function topics %s", getByTopicInput.getTopics()));
            references = referenceService.findByTopics(getByTopicInput.getTopics());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage(references, responseHeaders);
    }
}