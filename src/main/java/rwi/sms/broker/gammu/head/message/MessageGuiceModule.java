package rwi.sms.broker.gammu.head.message;

import com.google.inject.AbstractModule;
import rwi.sms.broker.gammu.head.message.queue.ConcurrentTextMessageQueue;
import rwi.sms.broker.gammu.head.message.queue.TextMessageQueue;
import rwi.sms.broker.gammu.head.message.sender.TextMessageSender;
import rwi.sms.broker.gammu.head.message.sender.gammu.GammuTextMessageSender;

import javax.inject.Singleton;

public class MessageGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TextMessageSender.class).to(GammuTextMessageSender.class).in(Singleton.class);
        bind(TextMessageQueue.class).to(ConcurrentTextMessageQueue.class).in(Singleton.class);
    }
}
