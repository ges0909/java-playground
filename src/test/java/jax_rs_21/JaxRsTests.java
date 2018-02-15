package jax_rs_21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class JaxRsTests {
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

  @Test
  void testAsync() throws InterruptedException, ExecutionException {
    Future<Response> future = target.path("/posts/1").request().async().get();
    Response resp = future.get();
    assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
    assertEquals(Response.Status.OK.getReasonPhrase(), resp.getStatusInfo().getReasonPhrase());
  }

  @Test
  void testAsyn2() throws InterruptedException, ExecutionException {
    InvocationCallback<Post> callback = new InvocationCallback<Post>() {
      @Override
      public void completed(Post post) {
      }

      @Override
      public void failed(Throwable throwable) {
      }
    };
    Future<Post> future = target.path("/posts/1").request().async().get(callback);
    future.get();
  }
}