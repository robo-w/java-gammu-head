package rwi.sms.broker.gammu.head.configuration;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import rwi.sms.broker.gammu.head.configuration.json.GsonProvider;
import rwi.sms.broker.gammu.head.configuration.json.JsonTransformer;
import rwi.sms.broker.gammu.head.controller.send.AustrianPhoneNumberValidator;
import rwi.sms.broker.gammu.head.controller.send.PhoneNumberValidator;

import javax.inject.Singleton;
import java.util.Objects;

public class ConfigurationGuiceModule extends AbstractModule {
    private final WebServerConfiguration webServerConfiguration;

    public ConfigurationGuiceModule(final WebServerConfiguration webServerConfiguration) {
        this.webServerConfiguration = Objects.requireNonNull(webServerConfiguration);
    }

    @Override
    protected void configure() {
        bind(Gson.class).toProvider(GsonProvider.class).in(Singleton.class);
        bind(JsonTransformer.class).in(Singleton.class);
        bind(PhoneNumberValidator.class).to(AustrianPhoneNumberValidator.class).in(Singleton.class);
        bind(WebServerConfiguration.class).toInstance(webServerConfiguration);
    }
}
