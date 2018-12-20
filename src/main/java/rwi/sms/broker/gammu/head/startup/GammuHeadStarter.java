package rwi.sms.broker.gammu.head.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class GammuHeadStarter {
    private static final Logger LOG = LoggerFactory.getLogger(GammuHeadStarter.class);
    private final WebServerStarter webServerStarter;

    @Inject
    public GammuHeadStarter(final WebServerStarter webServerStarter) {
        this.webServerStarter = webServerStarter;
    }

    public void run() {
        LOG.info("Starting Java Gammu Head.");
        this.webServerStarter.startWebserver();
    }
}
