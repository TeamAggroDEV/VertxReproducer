package dev.teamaggro;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;

import java.util.concurrent.TimeUnit;

public class Reproducer {

    public static final Vertx vertx = Vertx.vertx();
    public static void main(String[] args) throws Exception {
        vertx.createHttpServer(
                new HttpServerOptions()
                        .setIdleTimeout(15000)
                        .setIdleTimeoutUnit(TimeUnit.MILLISECONDS))
                .requestHandler(
                        new RequestHandler())
                .exceptionHandler(new ExceptionHandler())
                .webSocketHandler(new WebSocketHandler())
                .invalidRequestHandler(new InvalidRequestHandler())
                .listen(80, "0.0.0.0").result();

    }

}
