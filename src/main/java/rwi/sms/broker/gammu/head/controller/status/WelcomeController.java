package rwi.sms.broker.gammu.head.controller.status;

import rwi.sms.broker.gammu.head.configuration.json.JsonTransformer;
import rwi.sms.broker.gammu.head.controller.SparkController;
import spark.Spark;

import javax.inject.Inject;

import static spark.Spark.get;

public class WelcomeController implements SparkController {

    private final JsonTransformer jsonTransformer;
    private final HomePageHandler homePageHandler;

    @Inject
    public WelcomeController(
            final JsonTransformer jsonTransformer,
            final HomePageHandler homePageHandler) {
        this.jsonTransformer = jsonTransformer;
        this.homePageHandler = homePageHandler;
    }

    @Override
    public void initialize() {
        get("/", homePageHandler, jsonTransformer);
    }
}
