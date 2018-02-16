package jaxrs_21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
  static class AsyncTests {
    private final WebTarget target = ClientBuilder.newClient().target("http://jsonplaceholder.typicode.com");

    @Test
    void testAsync() throws InterruptedException, ExecutionException {
      Future<Response> future = target.path("/posts/1").request().async().get();
      Response resp = future.get();
      assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
      assertEquals(Response.Status.OK.getReasonPhrase(), resp.getStatusInfo().getReasonPhrase());
    }

    @Test
    void testAsync2() {
      InvocationCallback<Post> callback = new InvocationCallback<Post>() {
        @Override
        public void completed(Post post) {
          assertEquals(1, post.userId);
          assertEquals(1, post.id);
        }

        @Override
        public void failed(Throwable throwable) {
        }
      };
      target.path("/posts/1").request().async().get(callback);
    }
  }
}