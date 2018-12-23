package rwi.sms.broker.gammu.head.controller.send;

import rwi.sms.broker.gammu.head.contract.send.SendTextMessageRequest;
import rwi.sms.broker.gammu.head.message.data.TextMessage;

import java.util.Optional;

public interface TextMessageFactory {
    /**
     * Creates text message. Returns empty optional if the validation of the parameters fails.
     */
    Optional<TextMessage> createTextMessage(SendTextMessageRequest request);
}
