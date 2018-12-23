package rwi.sms.broker.gammu.head.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rwi.sms.broker.gammu.head.message.worker.WorkerThreadController;

import javax.inject.Inject;

public class GammuHeadStarter {
    private static final Logger LOG = LoggerFactory.getLogger(GammuHeadStarter.class);
    private final WebServerStarter webServerStarter;
    private final WorkerThreadController workerThreadController;

    @Inject
    public GammuHeadStarter(
            final WebServerStarter webServerStarter,
            final WorkerThreadController workerThreadController) {
        this.webServerStarter = webServerStarter;
        this.workerThreadController = workerThreadController;
    }

    public void run() {
        LOG.info("Starting Java Gammu Head.");
        this.webServerStarter.startWebserver();
        this.workerThreadController.startWorkerThreadAndBlock();
    }
}
