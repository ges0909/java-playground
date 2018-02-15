package jax_rs_21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

public class JaxRsTests {

  @Nested
  class SyncTests {
    private final WebTarget target = ClientBuilder.newClient().target("http://jsonplaceholder.typicode.com");

    @Test
    void testSync() {
      Response resp = target.path("/posts/1").request().get();
      assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
      assertEquals(Response.Status.OK.getReasonPhrase(), resp.getStatusInfo().getReasonPhrase());
    }

    @ParameterizedTest
    @ValueSource(strings = { "/posts/1" })
    void testSync2(String uri) {
      Post post = target.path(uri).request().accept("application/json").get(Post.class);
      assertEquals(1, post.userId);
      assertEquals(1, post.id);
    }
  }

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @RunWith(VertxUnitRunner.class)
  static class AsyncTests {
    private final WebTarget target = ClientBuilder.newClient().target("http://jsonplaceholder.typicode.com");
    private Vertx vertx;

    @BeforeAll
    void setUp() {
      vertx = Vertx.vertx();
    }

    @AfterAll
    void tesDown(TestContext context) {
      vertx.close(context.asyncAssertSuccess());
    }

    @Test
    void testAsync(TestContext context) throws InterruptedException, ExecutionException {
      final Async async = context.async();
      Future<Response> future = target.path("/posts/1").request().async().get();
      Response resp = future.get();
      context.assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
      context.assertEquals(Response.Status.OK.getReasonPhrase(), resp.getStatusInfo().getReasonPhrase());
      async.complete();

    }

    @Test
    void testAsyn2(TestContext context) throws InterruptedException, ExecutionException {
      final Async async = context.async();
      InvocationCallback<Post> callback = new InvocationCallback<Post>() {
        @Override
        public void completed(Post post) {
          context.assertEquals(1, post.userId);
          context.assertEquals(1, post.id);
          async.complete();
        }

        @Override
        public void failed(Throwable throwable) {
          context.asyncAssertFailure();
          async.complete();
        }
      };
      target.path("/posts/1").request().async().get(callback);
    }
  }
}