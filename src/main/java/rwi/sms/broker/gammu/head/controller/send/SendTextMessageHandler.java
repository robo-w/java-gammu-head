package rwi.sms.broker.gammu.head.controller.send;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwi.sms.broker.gammu.head.configuration.WebServerConfiguration;
import rwi.sms.broker.gammu.head.contract.send.SendResult;
import rwi.sms.broker.gammu.head.contract.send.SendTextMessageRequest;
import rwi.sms.broker.gammu.head.contract.send.SendTextMessageResponse;
import rwi.sms.broker.gammu.head.message.data.TextMessage;
import rwi.sms.broker.gammu.head.message.queue.TextMessageQueue;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class SendTextMessageHandler implements Route {
    private static final Logger LOG = LoggerFactory.getLogger(SendTextMessageHandler.class);

    private final Gson gson;
    private final TextMessageQueue messageQueue;
    private final TextMessageFactory messageFactory;
    private final WebServerConfiguration webServerConfiguration;

    @Inject
    public SendTextMessageHandler(
            final Gson gson,
            final TextMessageQueue messageQueue,
            final TextMessageFactory messageFactory,
            final WebServerConfiguration webServerConfiguration) {
        this.gson = gson;
        this.messageQueue = messageQueue;
        this.messageFactory = messageFactory;
        this.webServerConfiguration = webServerConfiguration;
    }

    @Override
    public SendTextMessageResponse handle(final Request request, final Response response) {
        SendTextMessageResponse sendTextMessageResponse;
        Optional<SendTextMessageRequest> sendTextMessageRequest = readTypedRequest(request);

        if (sendTextMessageRequest.isPresent() && tokenIsValid(sendTextMessageRequest.get().getAuthenticationToken())) {
            Optional<TextMessage> textMessage = messageFactory.createTextMessage(sendTextMessageRequest.get());

            if (textMessage.isPresent()) {
                messageQueue.queueTextMessageForSending(textMessage.get());
                sendTextMessageResponse = new SendTextMessageResponse(SendResult.SUCCESS);
            } else {
                sendTextMessageResponse = new SendTextMessageResponse(SendResult.ARGUMENT_VALIDATION_FAILED);
                response.status(400);
            }

        } else if (sendTextMessageRequest.isPresent()) {
            sendTextMessageResponse = new SendTextMessageResponse(SendResult.AUTHENTICATION_FAILED);
            response.status(403);
        } else {
            sendTextMessageResponse = new SendTextMessageResponse(SendResult.MALFORMED_REQUEST_BODY);
            response.status(400);
        }

        return sendTextMessageResponse;
    }

    private boolean tokenIsValid(final String authenticationToken) {
        boolean valid = false;
        if (StringUtils.isNotBlank(authenticationToken)) {
            String configuredToken = webServerConfiguration.getAuthenticationToken();
            if (Objects.equals(authenticationToken.trim(), configuredToken)) {
                valid = true;
            } else {
                LOG.debug("Provided token '{}' does not match the configured token '{}'.", authenticationToken, configuredToken);
            }
        } else {
            LOG.debug("The provided authentication token '{}' is empty.", authenticationToken);
        }

        return valid;
    }

    private Optional<SendTextMessageRequest> readTypedRequest(final Request request) {
        Optional<SendTextMessageRequest> sendTextMessageRequest = Optional.empty();
        try {
            sendTextMessageRequest = Optional.ofNullable(gson.fromJson(request.body(), SendTextMessageRequest.class));
        } catch (JsonSyntaxException e) {
            LOG.warn("Got invalid request to send text message. Message: {}", e.getMessage());
            LOG.debug("Underlying json syntax exception", e);
        }
        return sendTextMessageRequest;
    }
}
