package edu.pdx.cs410J.mpooja;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Integration test that tests the REST calls made by {@link AppointmentBookRestClient}
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppointmentBookRestClientIT {
    /*
 private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AppointmentBookRestClient newAppointmentBookRestClient() {
    int port = Integer.parseInt(PORT);
    return new AppointmentBookRestClient(HOSTNAME, port);
  }

  @Test
  public void test0RemoveAllMappings() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    Response response = client.removeAllMappings();
    assertThat(response.getContent(), response.getCode(), equalTo(200));
  }

  @Test
  public void test1EmptyServerContainsNoMappings() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    Response response = client.getAllKeysAndValues();
    String content = response.getContent();
    assertThat(content, response.getCode(), equalTo(200));
    assertThat(content, containsString(Messages.getMappingCount(0)));
  }

  @Test
  public void test2AddOneKeyValuePair() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    String testKey = "TEST KEY";
    String testValue = "TEST VALUE";
    Response response = client.addKeyValuePair(testKey, testValue);
    String content = response.getContent();
    assertThat(content, response.getCode(), equalTo(200));
    assertThat(content, containsString(Messages.mappedKeyValue(testKey, testValue)));
  }

  @Test
  public void missingRequiredParameterReturnsPreconditionFailed() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    Response response = client.postToMyURL();
    assertThat(response.getContent(), containsString(Messages.missingRequiredParameter("key")));
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
  }

*/}
