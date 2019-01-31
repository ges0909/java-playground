package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

class ReceiverVerticle extends AbstractVerticle {
    private String name;

    ReceiverVerticle(String name) {
        this.name = name;
    }

    @Override
    public void start(Future<Void> startFuture) {
        vertx.eventBus().consumer("anAddress",
                msg -> System.out.println(this.name + " received message: " + msg.body())
        );
        startFuture.complete();
    }
}
