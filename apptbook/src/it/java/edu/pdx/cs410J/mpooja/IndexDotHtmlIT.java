package edu.pdx.cs410J.mpooja;

public class IndexDotHtmlIT {
  /*
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  @Test
  public void indexDotHtmlExists() throws IOException {
    Response indexDotHtml = fetchIndexDotHtml();
    assertThat(indexDotHtml.getCode(), equalTo(200));
  }

  @Test
  public void indexDotHtmlHasResonableContent() throws IOException {
    Response indexDotHtml = fetchIndexDotHtml();
    assertThat(indexDotHtml.getContent(), containsString("<form"));
  }

  private Response fetchIndexDotHtml() throws IOException {
    int port = Integer.parseInt(PORT);
    return new IndexDotHtmlHelper(HOSTNAME, port).getIndexDotHtml();
  }

  static class IndexDotHtmlHelper extends HttpRequestHelper {
    private static final String WEB_APP = "apptbook";
    private final String url;

    IndexDotHtmlHelper(String hostName, int port) {
      this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, "index.html" );
    }

    public Response getIndexDotHtml() throws IOException {
      return get(this.url);
    }
  }
*/}
