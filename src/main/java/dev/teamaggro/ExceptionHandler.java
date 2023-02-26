package dev.teamaggro;

import io.vertx.core.Handler;

public class ExceptionHandler implements Handler<Throwable> {
    @Override
    public void handle(final Throwable throwable) {
        throwable.printStackTrace();
    }
}
