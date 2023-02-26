package dev.teamaggro;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;

public class InvalidRequestHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(final HttpServerRequest req) {
        req.end();
    }
}
