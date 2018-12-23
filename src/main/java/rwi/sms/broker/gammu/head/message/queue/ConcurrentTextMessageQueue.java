package rwi.sms.broker.gammu.head.message.queue;

import com.google.common.collect.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwi.sms.broker.gammu.head.message.data.TextMessage;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.BlockingQueue;

@ThreadSafe
public class ConcurrentTextMessageQueue implements TextMessageQueue {

    private final BlockingQueue<TextMessage> messageQueue;

    public ConcurrentTextMessageQueue() {
        this.messageQueue = Queues.newLinkedBlockingQueue();
    }

    @Override
    public void queueTextMessageForSending(final TextMessage message) {
        messageQueue.add(message);
    }

    @Override
    public TextMessage waitForNextMessage() throws InterruptedException {
        return messageQueue.take();
    }

    @Override
    public int getWaitingMessageCount() {
        return messageQueue.size();
    }
}
