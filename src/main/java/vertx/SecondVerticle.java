package vertx;

import io.vertx.core.AbstractVerticle;

class SecondVerticle extends AbstractVerticle {
    @Override
    public void start() {
        System.out.println("second verticle started");
    }

    @Override
    public void stop() {
        System.out.println("second verticle stopped");
    }
}
