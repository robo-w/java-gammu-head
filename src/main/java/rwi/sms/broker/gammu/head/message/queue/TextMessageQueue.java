package rwi.sms.broker.gammu.head.message.queue;

import rwi.sms.broker.gammu.head.message.data.TextMessage;
import rwi.sms.broker.gammu.head.message.data.TextMessageTarget;

import java.util.Optional;

public interface TextMessageQueue {
    /**
     * Queue the text message for sending with the given parameters.
     */
    void queueTextMessageForSending(TextMessage message);

    /**
     * Returns next message to send. Blocks until message is ready.
     */
    TextMessage waitForNextMessage() throws InterruptedException;

    int getWaitingMessageCount();
}
