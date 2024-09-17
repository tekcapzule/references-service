package com.tekcapzule.references.application.function;

import com.tekcapzule.references.application.config.AppConfig;
import com.tekcapzule.references.application.function.input.GetByTagsInput;
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
public class GetByTagsFunction implements Function<Message<GetByTagsInput>, Message<List<References>>> {

    private final ReferenceService referenceService;

    private final AppConfig appConfig;

    public GetByTagsFunction(final ReferenceService referenceService, final AppConfig appConfig) {
        this.referenceService = referenceService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<List<References>> apply(Message<GetByTagsInput> getMyReferenceInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        List<References> references = new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get references by tags Function");
            references = referenceService.searchByTags(getMyReferenceInputMessage.getPayload().getTags());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage(references, responseHeaders);

    }
}