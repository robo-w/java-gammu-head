package rwi.sms.broker.gammu.head;

import com.google.inject.Guice;
import com.google.inject.Injector;
import rwi.sms.broker.gammu.head.configuration.ConfigurationGuiceModule;
import rwi.sms.broker.gammu.head.controller.ControllerGuiceModule;
import rwi.sms.broker.gammu.head.message.MessageGuiceModule;
import rwi.sms.broker.gammu.head.startup.GammuHeadStarter;
import rwi.sms.broker.gammu.head.webserver.WebServerGuiceModule;

public class GammuHeadBootstrapper {
    public static void main(String... args) {
        Injector injector = Guice.createInjector(
                new WebServerGuiceModule(),
                new MessageGuiceModule(),
                new ControllerGuiceModule(),
                new ConfigurationGuiceModule()
        );
        GammuHeadStarter starter = injector.getInstance(GammuHeadStarter.class);
        starter.run();
    }
}
