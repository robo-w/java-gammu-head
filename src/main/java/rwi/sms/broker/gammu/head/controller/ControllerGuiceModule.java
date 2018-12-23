package rwi.sms.broker.gammu.head.controller;

import com.google.inject.AbstractModule;
import rwi.sms.broker.gammu.head.controller.send.TextMessageFactory;
import rwi.sms.broker.gammu.head.controller.send.ValidatingTextMessageFactory;

import javax.inject.Singleton;

public class ControllerGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TextMessageFactory.class).to(ValidatingTextMessageFactory.class).in(Singleton.class);
    }
}
