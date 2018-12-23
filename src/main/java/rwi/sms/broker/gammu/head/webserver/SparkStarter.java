package rwi.sms.broker.gammu.head.webserver;

import rwi.sms.broker.gammu.head.configuration.WebServerConfiguration;
import rwi.sms.broker.gammu.head.controller.ControllerProvider;
import rwi.sms.broker.gammu.head.controller.SparkController;
import rwi.sms.broker.gammu.head.startup.WebServerStarter;

import javax.inject.Inject;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.port;
import static spark.Spark.stop;

public class SparkStarter implements WebServerStarter {
    private final WebServerExceptionHandler exceptionHandler;
    private final WebServerConfiguration configuration;
    private final ControllerProvider controllerProvider;

    @Inject
    public SparkStarter(
            final WebServerExceptionHandler exceptionHandler,
            final WebServerConfiguration configuration,
            final ControllerProvider controllerProvider) {
        this.exceptionHandler = exceptionHandler;
        this.configuration = configuration;
        this.controllerProvider = controllerProvider;
    }

    @Override
    public void startWebserver() {
        initExceptionHandler(exceptionHandler);
        port(configuration.getServerPort());
        this.controllerProvider.get().forEach(SparkController::initialize);
    }

    @Override
    public void close() {
        stop();
    }
}
