package rwi.sms.broker.gammu.head.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class WebServerExceptionHandler implements Consumer<Exception> {
    private static final Logger LOG = LoggerFactory.getLogger(WebServerExceptionHandler.class);

    @Override
    public void accept(final Exception exception) {
        LOG.error("Failed to start Web Server! Message: {}", exception.getMessage());
        LOG.debug("Underlying exception", exception);
        LOG.info("Program will shut down in 2 seconds.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOG.debug("interrupted", e);
        }

        System.exit(1);
    }
}
