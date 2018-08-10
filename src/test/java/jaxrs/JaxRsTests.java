package jaxrs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class JaxRsTests {
  private final WebTarget target = ClientBuilder.newClient().target("http://jsonplaceholder.typicode.com");

  @Nested
  class SyncTests {
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
  class AsyncTests {
    @Test
    void testAsync() throws ExecutionException, TimeoutException, InterruptedException {
      Future<Response> future = target.path("/posts/1").request().async().get();
      Response response = future.get(1, TimeUnit.SECONDS);
      assertNotNull(response);
      assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
      assertEquals(Response.Status.OK.getReasonPhrase(), response.getStatusInfo().getReasonPhrase());
    }

    @Test
    void testAsync2() throws ExecutionException, TimeoutException, InterruptedException {
      InvocationCallback<Post> callback = new InvocationCallback<Post>() {
        @Override
        public void completed(Post post) {
          System.out.println("completed");
        }

        @Override
        public void failed(Throwable throwable) {
          System.out.println("failed");
        }
      };
      Future<Post> future = target.path("/posts/1").request().async().get(callback);
      Post post = future.get(1, TimeUnit.SECONDS);
      assertEquals(1, post.userId);
      assertEquals(1, post.id);
    }
  }
}