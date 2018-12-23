package rwi.sms.broker.gammu.head.message.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.UUID;

public class WorkerThreadController {
    private static final Logger LOG = LoggerFactory.getLogger(WorkerThreadController.class);

    private final Provider<SendTextMessageThread> workerThreadProvider;

    @Inject
    public WorkerThreadController(final Provider<SendTextMessageThread> workerThreadProvider) {
        this.workerThreadProvider = workerThreadProvider;
    }

    public void startWorkerThreadAndBlock() {
        boolean wasInterrupted = false;

        while (!wasInterrupted) {
            String threadName = "external-sender-" + UUID.randomUUID();
            Thread thread = new Thread(workerThreadProvider.get(), threadName);
            thread.setDaemon(true);
            thread.start();
            LOG.debug("Worker thread to send text messages out was started.");

            try {
                thread.join();
            } catch (InterruptedException e) {
                LOG.debug("Waiting for worker thread was interrupted.", e);
                wasInterrupted = true;
            }
        }
    }
}
