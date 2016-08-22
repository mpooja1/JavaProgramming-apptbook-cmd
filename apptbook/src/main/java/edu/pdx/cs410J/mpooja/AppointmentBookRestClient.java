package edu.pdx.cs410J.mpooja;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

/**
 * A helper class for accessing the rest client
 */
public class AppointmentBookRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "apptbook";
    private static final String SERVLET = "appointments";
    private final String url;


    /**
     * Creates a client to the appointment book REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AppointmentBookRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all keys and values from the server
     */
    public Response getAllKeysAndValues() throws IOException
    {
        return get(this.url );
    }

    /**
     * Returns all values for the given Appointmentbook
     * @param book Appointmentbook
     */
    public Response getValues(AppointmentBook book ) throws IOException
    {
        try {
            if (book.searchAppointmentOnly != null)
                return get(this.url, "owner", book.getOwnerName(), "beginTime", book.searchAppointmentOnly.getBeginTimeString(), "endTime", book.searchAppointmentOnly.getEndTimeString());
            else {
                if (book.appointments != null)
                    throw new Exception("Please specify search parameters");
                else{
                    return get(this.url, "owner", book.getOwnerName(), "beginTime", book.singleAppoinement.getBeginTimeString(), "endTime", book.singleAppoinement.getEndTimeString());
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            System.exit(1);
            return null;
        }
    }

    /**
     * Adds Appointments for the specific owner
     * @param owner ownerName
     * @param appointment Appointment to be added
     * @return HTTPResponse requiredc to post
     * @throws IOException if any IOException occured
     */
    public Response addKeyValuePair(String owner, Appointment appointment ) throws IOException
    {
        return post( this.url, "owner", owner,"description", appointment.getDescription(), "beginTime", appointment.getBeginTimeString(), "endTime", appointment.getEndTimeString() );
    }

    /**
     * Posts key value pairs on url
     * @param keysAndValues key and values
     * @return HTTPResponse
     * @throws IOException
     */
    @VisibleForTesting
    Response postToMyURL(String... keysAndValues) throws IOException {
        return post(this.url, keysAndValues);
    }

    /**
     * Removes all mappings(key and value pairs)
     * @return HTTPResponse
     * @throws IOException
     */
    public Response removeAllMappings() throws IOException {
        return delete(this.url);
    }
}
