package rwi.sms.broker.gammu.head.controller.status;

import rwi.sms.broker.gammu.head.contract.status.StatusResponse;
import rwi.sms.broker.gammu.head.message.queue.TextMessageQueue;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.inject.Inject;

public class HomePageHandler implements Route {
    private final TextMessageQueue textMessageQueue;

    @Inject
    public HomePageHandler(final TextMessageQueue textMessageQueue) {
        this.textMessageQueue = textMessageQueue;
    }

    @Override
    public StatusResponse handle(final Request request, final Response response) {
        return new StatusResponse("java-gammu-head", "1.0", this.textMessageQueue.getWaitingMessageCount());
    }
}
