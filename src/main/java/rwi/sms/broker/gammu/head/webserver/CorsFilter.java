package rwi.sms.broker.gammu.head.webserver;

import spark.Filter;
import spark.Request;
import spark.Response;

public class CorsFilter implements Filter {
    @Override
    public void handle(final Request request, final Response response) {
        response.header("Access-Control-Allow-Origin", "*");
    }
}
