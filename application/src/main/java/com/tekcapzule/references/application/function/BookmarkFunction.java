package com.tekcapzule.references.application.function;

import com.tekcapzule.references.application.config.AppConfig;
import com.tekcapzule.references.application.function.input.AddBookmarkInput;
import com.tekcapzule.references.application.mapper.InputOutputMapper;
import com.tekcapzule.references.domain.command.AddBookmarkCommand;
import com.tekcapzule.references.domain.service.ReferenceService;
import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class BookmarkFunction implements Function<Message<AddBookmarkInput>, Message<Void>> {

    private final ReferenceService referenceService;

    private final AppConfig appConfig;

    public BookmarkFunction(final ReferenceService referenceService, final AppConfig appConfig) {
        this.referenceService = referenceService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<AddBookmarkInput> addBookmarkInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            AddBookmarkInput addBookmarkInput = addBookmarkInputMessage.getPayload();
            log.info(String.format("Entering bookmark Function -  Reference Id:%s", addBookmarkInput.getReferenceId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(addBookmarkInputMessage.getHeaders());
            AddBookmarkCommand addBookmarkCommand = InputOutputMapper.buildAddBookmarkCommandFromAddBookmarkInput.apply(addBookmarkInput, origin);
            referenceService.addBookMark(addBookmarkCommand);
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