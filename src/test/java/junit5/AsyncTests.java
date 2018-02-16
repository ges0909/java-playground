package junit5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
class AsyncTests {
  private final WebTarget target = ClientBuilder.newClient().target("http://jsonplaceholder.typicode.com");

  @Test
  void testAsync(TestContext context) throws InterruptedException, ExecutionException {
    Future<Response> future = target.path("/posts/1").request().async().get();
    Response resp = future.get();
    context.assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
    context.assertEquals(Response.Status.OK.getReasonPhrase(), resp.getStatusInfo().getReasonPhrase());
  }
}