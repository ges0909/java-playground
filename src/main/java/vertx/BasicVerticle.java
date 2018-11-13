package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

class BasicVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        System.out.println("BasicVerticle started");
        vertx.deployVerticle(new SecondVerticle());
        startFuture.complete();
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        System.out.println("BasicVerticle stopped");
        stopFuture.complete();
    }
}