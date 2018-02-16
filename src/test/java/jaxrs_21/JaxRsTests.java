package jaxrs_21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

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
  @ExtendWith(VertxExtension.class)
  static class AsyncTests {

    private final WebTarget target = ClientBuilder.newClient().target("http://jsonplaceholder.typicode.com");

    private Vertx vertx;

    @BeforeEach
    void prepare() {
      vertx = Vertx.vertx(new VertxOptions().setMaxEventLoopExecuteTime(1000).setPreferNativeTransport(true)
          .setFileResolverCachingEnabled(true));
    }

    @AfterEach
    void cleanup() {
      vertx.close();
    }

    @Test
    void testAsync(Vertx vertx, VertxTestContext testContext) throws InterruptedException, ExecutionException {
      Future<Response> future = target.path("/posts/1").request().async().get();
      Response resp = future.get();
      assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
      assertEquals(Response.Status.OK.getReasonPhrase(), resp.getStatusInfo().getReasonPhrase());
      testContext.completeNow();
    }

    @Test
    void testAsyn2(Vertx vertx, VertxTestContext testContext) throws InterruptedException, ExecutionException {
      InvocationCallback<Post> callback = new InvocationCallback<Post>() {
        @Override
        public void completed(Post post) {
          assertEquals(1, post.userId);
          assertEquals(1, post.id);
          testContext.completeNow();
        }

        @Override
        public void failed(Throwable throwable) {
          testContext.completeNow();
        }
      };
      target.path("/posts/1").request().async().get(callback);
    }
  }
}