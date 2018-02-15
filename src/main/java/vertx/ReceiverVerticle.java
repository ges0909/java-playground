package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

class ReceiverVerticle extends AbstractVerticle {
    private String name = null;

    public ReceiverVerticle(String name) {
        this.name = name;
    }

    @Override
    public void start(Future<Void> startFuture) {
        vertx.eventBus().consumer("anAddress", message -> {
            System.out.println(this.name + " received message: " + message.body());
        });
        startFuture.complete();
    }
}