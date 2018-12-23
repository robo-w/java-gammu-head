package rwi.sms.broker.gammu.head.controller.send;

import rwi.sms.broker.gammu.head.configuration.json.JsonTransformer;
import rwi.sms.broker.gammu.head.controller.SparkController;

import javax.inject.Inject;

import static spark.Spark.post;

public class SendTextMessageController implements SparkController {
    private final JsonTransformer jsonTransformer;
    private final SendTextMessageHandler sendTextMessageHandler;

    @Inject
    public SendTextMessageController(final JsonTransformer jsonTransformer, final SendTextMessageHandler sendTextMessageHandler) {
        this.jsonTransformer = jsonTransformer;
        this.sendTextMessageHandler = sendTextMessageHandler;
    }

    @Override
    public void initialize() {
        post("/sms/send", this.sendTextMessageHandler, this.jsonTransformer);
    }
}
