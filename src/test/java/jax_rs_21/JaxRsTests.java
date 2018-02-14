package jax_rs_21;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

public class JaxRsTests {
  @Test
  void testSynchronousClient() {
    Response response = ClientBuilder.newClient().target("http://www.google.de").request().get();
  }
}