package junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
class AsyncTests {
  private final WebTarget target = ClientBuilder.newClient().target("http://jsonplaceholder.typicode.com");

  @Test
  void testAsync() throws TimeoutException, InterruptedException, ExecutionException {
    Future<Response> future = target.path("/posts/1").request().async().get();
    Response response = future.get(1, TimeUnit.SECONDS);
    assertNotNull(response);
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertEquals(Response.Status.OK.getReasonPhrase(), response.getStatusInfo().getReasonPhrase());
  }
}