package edu.pdx.cs410J.mpooja;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */
public class AppointmentBookServlet extends HttpServlet
{
    private final Map<String, AppointmentBook> data = new HashMap<String, AppointmentBook>();
    private AppointmentBook AppointmentBook = null;
    /**
     * Handles an HTTP GET request from a client by writing the value of the key
     * specified in the "key" HTTP parameter to the HTTP response.  If the "key"
     * parameter is not specified, all of the key/value pairs are written to the
     * HTTP response.
     * @param request HTTPServletRequest
     * @param response HTTPServletResponse
     * @throws ServletException if any Servlet exception occurs
     * @throws IOException if any IO Exception occurs
     */

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        try {
            response.setContentType("text/plain");
            String owner = getParameter("owner", request);
            String startTime = getParameter("beginTime", request);
            String endTime = getParameter("endTime", request);

            if(startTime!=null && endTime!=null) {
                String start[] = startTime.split(" ");
                String end[] = endTime.split(" ");
                checkValidDate(start[0]+" "+start[1],response);
                checkValidDate(end[0]+" "+end[1],response);
                SimpleDateFormat ShortDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
                ShortDateFormat.parse(startTime);
                ShortDateFormat.parse(endTime);
            }
           if (owner != null && startTime != null && endTime != null) {
                Appointment tempAppointment = new Appointment();
                tempAppointment.setDate(startTime, endTime);
                //client is performing a search
                //return all Appointments that begin between the start and end time
                writeSearchValue(new AppointmentBook(owner, tempAppointment, "-search"), response);
            } else if (owner != null && startTime == null && endTime == null) {
                //Client is trying writevalue
                writeValue(owner, response);
            } else {
                writeAllMappings(response);
            }

            }catch (ParseException e){
            PrintWriter pw = response.getWriter();
            pw.write("\nERROR 404 BAD REQUEST \n " +
                    "The request could not be understood by the server due to malformed syntax.Please try again..");
            System.out.println("Invalid data in URL.. Please Try again");
        }
        catch (Exception e)
        {
            PrintWriter pw = response.getWriter();
            pw.write(e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    /**
     * Handles an HTTP POST request by storing the key/value pair specified by the
     * "key" and "value" request parameters.  It writes the key/value pair to the
     * HTTP response.
     * @param request HTTPServlet Request
     * @param response HTTPServlet Response
     * @throws ServletException if any Servlet Exception occurs
     * @throws IOException if any IOException occurs
     */

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {

        response.setContentType( "text/plain" );
        String owner = getParameter( "owner", request );
        if (owner == null) {
            missingRequiredParameter( response, "owner" );
            return;
        }
        String description = getParameter( "description", request );
        if (description == null) {
            missingRequiredParameter( response, "description" );
            return;
        }

        String startTime = getParameter( "beginTime", request );
        if (startTime == null) {
            missingRequiredParameter( response, "beginTime" );
            return;
        }
        String endTime = getParameter( "endTime", request );
        if (endTime == null) {
            missingRequiredParameter( response, "endTime" );
            return;
        }

        PrintWriter pw = response.getWriter();
        //Now we have all relavent information about Owners and their Appointments
        if(data!=null &&data.get(owner) != null){
            //Owner exists, just add a new Appointment
            AppointmentBook = data.get(owner);
            AppointmentBook.addAppointment(new Appointment(description, startTime, endTime));
            data.put(owner, AppointmentBook);
            System.out.println("attempting to add new Appointment");
        }
        else{
            //Owner doesn't exist, create a new Appointment.
            data.put(owner, new AppointmentBook(owner, new Appointment(description, startTime, endTime)));
            System.out.println("new owner added");
            pw.println("attempting to add a new owner");
        }
        int counter=0;
        Collection<Appointment> appointments = data.get(owner).getAppointments();

        pw.println("#     owner      description          Start Time        End Time        Duration \n");
        for(Appointment appointment: appointments){
            pw.println(++counter +" "+ owner+ "  "+appointment.getDescription()+ "  "+appointment.getBeginTimeString()+"  "+appointment.getEndTimeString()+  "   "+appointment.duration()+"\n");
        }

        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK);

    }

    /**
     * Handles an HTTP DELETE request by removing all key/value pairs.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     * @param request HTTPServlet Request
     * @param response HTTPServlet Response
     * @throws ServletException ifantServlet Exception occurs
     * @throws IOException if any IO Exception occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.data.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allMappingsDeleted());
        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     * @param response HTTPServlet Response
     * @param parameterName missing parameter name
     * @throws IOException if any IOException occurs
     */

    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {

        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(parameterName));
        pw.flush();
        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    /**
     * Writes the value of the given key to the HTTP response.
     *
     * The text of the message is formatted with {@link Messages#getMappingCount(int)}
     * and {@link Messages#formatKeyValuePair(String, String)}
     * @param owner ownerName
     * @param response HTTPServlet Response
     * @throws IOException if any IOException occurs
     */

    private void writeValue( String owner, HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        if(data.get(owner)!= null) {
            int counter=0;
            Collection<Appointment> Appointments = data.get(owner).getAppointments();
            pw.println("#     Owner      description         Start Time        End Time        Duration \n");
            for(Appointment appointment: Appointments){
                pw.println(++counter +" "+ owner+ "  "+appointment.getDescription()+ "  "+appointment.getBeginTimeString()+"   "+appointment.getEndTimeString()+"  "+appointment.duration()+"\n");
            }
        }
        else
            pw.println("Owner does not exists");
        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     *  Finds Appointments for given Owner
     * @param book Appointments to be searched for
     * @param response HTTPResponse
     * @throws IOException If any IOException occurs
     */

    private void writeSearchValue( AppointmentBook book, HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        String Owner = book.getOwnerName();
        Long begin = book.searchAppointmentOnly.beginTime.getTime();
        Long end = book.searchAppointmentOnly.endTime.getTime();
        if(data.get(Owner)!= null) {
            pw.println("Searching for: " + data.get(Owner).toString());
            Collection<Appointment> Appointments = data.get(Owner).getAppointments();
            boolean flag = true;
            int x = 0;

            pw.println("#     Owner        Description           Start Time        End Time        Duration \n");
            for(Appointment appointment: Appointments){
                if(begin<= (Long)appointment.beginTime.getTime()&&begin<=(Long)appointment.endTime.getTime()) {
                    if (end >= (Long) appointment.beginTime.getTime() && end >= (Long) appointment.endTime.getTime()) {
                        flag = false;
                        pw.println(++x + " " + book.getOwnerName() + "  " + appointment.getDescription() + "  " + appointment.getBeginTimeString() + "  " + appointment.getEndTimeString() + "   " + appointment.duration() + "\n");
                    }
                }
            }
            if(flag){
                pw.println("No Appointments match for: "+Owner);
            }
        }
        else
            pw.println("Owner does not exists");
        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Writes all of the key/value pairs to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatKeyValuePair(String, String)}
     * @param response HTTPServlet Response
     * @throws IOException if any IOException occurs
     */

    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(data.size()));

        for (Map.Entry<String,AppointmentBook> entry : this.data.entrySet()) {
            pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue().toString()));
        }
        pw.flush();
        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     * @param name Owner name
     * @param request HTTPServlet Request
     * @return String name
     */

    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }
/*
    @VisibleForTesting
    void setValueForKey(String key, String value) {
        this.data.put(key, value);
    }

    @VisibleForTesting
    String getValueForKey(String key) {
        return this.data.get(key);
    }*/


    /**
     * This method checks the correct format for date and time (MM/dd/yyyy hh:mm)
     * This method checks the form of dd/mm/yyyy in the form of dd/mm - 2 digits and yyyy - 4 digits
     * @param parsedate date to be parsed
     * @param response response object to print message on web browser
     * @throws ParseException if Invalid date
     * @throws IOException if IOExceptionis thrown
     */
    private static void checkValidDate(String parsedate,HttpServletResponse response) throws ParseException, IOException {

        if(parsedate == null || !(parsedate.matches("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$")))
        {
            PrintWriter pw = response.getWriter();
            pw.write("\nInvalid data in URL.. Please Try again");
           // System.out.println("Invalid data in URL.. Please Try again");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm"); //Validate Date time format
        dateFormat.setLenient(false);
        dateFormat.parse(parsedate);

    }

}
