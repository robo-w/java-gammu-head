package rwi.sms.broker.gammu.head.webserver;

import com.google.inject.AbstractModule;
import rwi.sms.broker.gammu.head.startup.WebServerStarter;

import javax.inject.Singleton;

public class WebserverGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WebServerStarter.class).to(SparkStarter.class).in(Singleton.class);
    }
}
