package dev.teamaggro;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;

public class RequestHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(final HttpServerRequest req) {
        try {
           Common.proxy(req);
        } catch (Exception e) {
            e.printStackTrace();
            req.response().setStatusCode(500).end("Request Handler caught exception, please use this Reference when reporting: " + e.getMessage());
        }
    }
}
