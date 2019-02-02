package rwi.sms.broker.gammu.head.controller.status;

import rwi.sms.broker.gammu.head.contract.status.GammuStatistics;
import rwi.sms.broker.gammu.head.contract.status.StatusResponse;
import rwi.sms.broker.gammu.head.message.queue.TextMessageQueue;
import rwi.sms.broker.gammu.head.message.status.ExternalStatusQuery;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.inject.Inject;

public class HomePageHandler implements Route {
    private final TextMessageQueue textMessageQueue;
    private final ExternalStatusQuery externalStatusQuery;

    @Inject
    public HomePageHandler(
            final TextMessageQueue textMessageQueue,
            final ExternalStatusQuery externalStatusQuery) {
        this.textMessageQueue = textMessageQueue;
        this.externalStatusQuery = externalStatusQuery;
    }

    @Override
    public StatusResponse handle(final Request request, final Response response) {
        response.type("application/json");
        GammuStatistics gammuStatistics = createGammuStatistics();
        int waitingMessageCount = this.textMessageQueue.getWaitingMessageCount();
        return new StatusResponse(
                "java-gammu-head",
                "1.0",
                waitingMessageCount,
                gammuStatistics);
    }

    private GammuStatistics createGammuStatistics() {
        return new GammuStatistics(
                externalStatusQuery.getMessageCountInInbox().orElse(null),
                externalStatusQuery.getMessageCountInOutbox().orElse(null),
                externalStatusQuery.getMessageCountOfError().orElse(null),
                externalStatusQuery.getMessageCountOfSent().orElse(null)
        );
    }
}
