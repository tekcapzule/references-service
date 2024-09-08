package com.tekcapzule.references.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.references.application.config.AppConfig;
import com.tekcapzule.references.application.function.input.ApproveReferencesInput;
import com.tekcapzule.references.application.mapper.InputOutputMapper;
import com.tekcapzule.references.domain.command.ApproveCommand;
import com.tekcapzule.references.domain.service.ReferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
@Slf4j
public class ApproveFunction implements Function<Message<ApproveReferencesInput>, Message<Void>> {

    private final ReferenceService referenceService;

    private final AppConfig appConfig;

    public ApproveFunction(final ReferenceService referenceService, final AppConfig appConfig) {
        this.referenceService = referenceService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<ApproveReferencesInput> approveReferencesInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            ApproveReferencesInput approveReferencesInput = approveReferencesInputMessage.getPayload();
            log.info(String.format("Entering approve references Function -  reference Id:%s", approveReferencesInput.getReferencesId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(approveReferencesInputMessage.getHeaders());
            ApproveCommand approveCommand = InputOutputMapper.buildApproveCommandFromApproveReferencesInput.apply(approveReferencesInput, origin);
            referenceService.approve(approveCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return new GenericMessage(payload, responseHeaders);

    }
}