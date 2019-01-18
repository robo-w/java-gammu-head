package rwi.sms.broker.gammu.head.message.worker;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwi.sms.broker.gammu.head.message.data.PhoneNumber;
import rwi.sms.broker.gammu.head.message.data.TextMessage;
import rwi.sms.broker.gammu.head.message.data.TextMessageTarget;
import rwi.sms.broker.gammu.head.message.queue.TextMessageQueue;
import rwi.sms.broker.gammu.head.message.sender.SendTextMessageResult;
import rwi.sms.broker.gammu.head.message.sender.TextMessageSender;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SendTextMessageThread implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(SendTextMessageThread.class);

    private final TextMessageQueue textMessageQueue;
    private final TextMessageSender textMessageSender;

    @Inject
    public SendTextMessageThread(
            final TextMessageQueue textMessageQueue,
            final TextMessageSender textMessageSender) {
        this.textMessageQueue = textMessageQueue;
        this.textMessageSender = textMessageSender;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sendNextMessageFromQueue();
            }
        } catch (InterruptedException e) {
            LOG.debug("Got interrupted while waiting for next text message.", e);
        }

        LOG.info("Worker thread to send text messages ended.");
    }

    private void sendNextMessageFromQueue() throws InterruptedException {
        LOG.trace("Waiting for next message from queue.");
        TextMessage textMessage = textMessageQueue.waitForNextMessage();
        LOG.debug("Writing message to send to external sms daemon: {}", textMessage);
        List<PhoneNumber> failedTargets = sendTextMessage(textMessage);
        reQueueFailedTargetsIfNecessary(textMessage, failedTargets);
    }

    private void reQueueFailedTargetsIfNecessary(final TextMessage textMessage, final List<PhoneNumber> failedTargets) throws InterruptedException {
        if (!failedTargets.isEmpty()) {
            LOG.warn("Failed to send message to targets '{}'", failedTargets);
            TextMessage message = new TextMessage(new TextMessageTarget(ImmutableList.copyOf(failedTargets)), textMessage.getContent());
            textMessageQueue.queueTextMessageForSending(message);

            LOG.debug("Sleeping for 3 seconds to try to recover from error state in underlying provider.");
            Thread.sleep(3000);
        }
    }

    private List<PhoneNumber> sendTextMessage(final TextMessage textMessage) {
        List<PhoneNumber> failedTargets = new ArrayList<>();
        for (PhoneNumber targetNumber : textMessage.getTarget().getTargetNumbers()) {
            SendTextMessageResult sendResult = textMessageSender.sendTextMessage(targetNumber, textMessage.getContent());
            if (sendResult != SendTextMessageResult.SUCCESS) {
                failedTargets.add(targetNumber);
            }
        }
        return failedTargets;
    }
}
