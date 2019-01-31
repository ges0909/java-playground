package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

class BasicVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) {
        System.out.println("basic verticle started");
        vertx.deployVerticle(new SecondVerticle());
        startFuture.complete();
    }

    @Override
    public void stop(Future<Void> stopFuture) {
        System.out.println("basic verticle stopped");
        stopFuture.complete();
    }
}