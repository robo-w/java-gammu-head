package rwi.sms.broker.gammu.head;

import com.google.inject.Guice;
import com.google.inject.Injector;
import rwi.sms.broker.gammu.head.startup.GammuHeadStarter;
import rwi.sms.broker.gammu.head.webserver.WebserverGuiceModule;

public class GammuHeadBootstrapper {
    public static void main(String... args) {
        Injector injector = Guice.createInjector(new WebserverGuiceModule());
        GammuHeadStarter starter = injector.getInstance(GammuHeadStarter.class);
        starter.run();
    }
}
