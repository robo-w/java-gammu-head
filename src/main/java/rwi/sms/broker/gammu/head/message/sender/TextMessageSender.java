package rwi.sms.broker.gammu.head.message.sender;

import rwi.sms.broker.gammu.head.message.data.PhoneNumber;
import rwi.sms.broker.gammu.head.message.data.TextMessage;

public interface TextMessageSender {
    SendTextMessageResult sendTextMessage(PhoneNumber target, String messageContent);
}
