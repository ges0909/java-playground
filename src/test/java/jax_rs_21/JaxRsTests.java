package jax_rs_21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

public class JaxRsTests {
  @Test
  void testSyncClient() {
    Response response = ClientBuilder.newClient().target("http://www.google.de").request().get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertEquals(Response.Status.OK.getReasonPhrase(), response.getStatusInfo().getReasonPhrase());
  }

  @Test
  void testAsyncClient() throws InterruptedException, ExecutionException {
    Future<Response> response = ClientBuilder.newClient().target("http://www.google.de").request().async().get();
    assertEquals(Response.Status.OK.getStatusCode(), response.get().getStatus());
    assertEquals(Response.Status.OK.getReasonPhrase(), response.get().getStatusInfo().getReasonPhrase());
  }
}