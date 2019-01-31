package vertx;

import io.vertx.core.Vertx;

public class VertxMain {
    public static void main(String[] args) throws InterruptedException {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BasicVerticle());
        vertx.deployVerticle(new ReceiverVerticle("R1"));
        vertx.deployVerticle(new ReceiverVerticle("R2"));
        System.out.println("wait 3 sec. to deploy sender verticle");
        Thread.sleep(3000);
        vertx.deployVerticle(new SenderVerticle());
        System.out.println("vertx main exited");
    }
}
