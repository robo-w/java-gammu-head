package rwi.sms.broker.gammu.head;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwi.sms.broker.gammu.head.configuration.ConfigurationGuiceModule;
import rwi.sms.broker.gammu.head.configuration.WebServerConfiguration;
import rwi.sms.broker.gammu.head.controller.ControllerGuiceModule;
import rwi.sms.broker.gammu.head.message.MessageGuiceModule;
import rwi.sms.broker.gammu.head.startup.GammuHeadStarter;
import rwi.sms.broker.gammu.head.webserver.WebServerGuiceModule;

public class GammuHeadBootstrapper {
    private static final Logger LOG = LoggerFactory.getLogger(GammuHeadBootstrapper.class);
    private static final int DEFAULT_WEBSERVER_PORT = 8080;

    public static void main(String... args) {
        if (args.length != 2) {
            throw usage();
        }

        int port = DEFAULT_WEBSERVER_PORT;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            LOG.warn("Invalid port number provided. Default port 8080 will be used.");
        }

        String authenticationToken = args[1].trim();
        LOG.info("Initializing gammu head with port={} and authenticationToken={}", port, authenticationToken);

        Injector injector = Guice.createInjector(
                new WebServerGuiceModule(),
                new MessageGuiceModule(),
                new ControllerGuiceModule(),
                new ConfigurationGuiceModule(new WebServerConfiguration(port, authenticationToken))
        );
        GammuHeadStarter starter = injector.getInstance(GammuHeadStarter.class);
        starter.run();
    }

    private static RuntimeException usage() {
        System.err.println("Usage:");
        System.err.println("  java -jar java-gammu-head <port> <secret-api-key>");
        System.err.println();
        System.err.println("Example:");
        System.err.println("  java -jar java-gammu-head 8080 DEADBEEF42XYZ");
        System.err.println();
        System.exit(1);
        return new RuntimeException();
    }
}
