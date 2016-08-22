package edu.pdx.cs410J.mpooja;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    private static final String MISSING_ARGS = "Missing command line arguments";
   // private static ArrayList<String> commands;
    private static AppointmentBook MySearchBook;
    private static AppointmentBook MySingleBook;

    /**
     * This is entry point for execution of Project 4
     * Checks for arguments entered are in proper order or not.
     * @param args command line arguments
     */

    public static void main(String... args)  {
        String hostName = null;
        String portString = null;
        boolean search = false;
        boolean print = false;
        int port=0;
        List<String> argList = new ArrayList<>();
         try {
            for (String arg : args) argList.add(arg);

            if(argList.contains("-README")){
                Readme();
            }

             if(!(argList.contains("-host")) && !(argList.contains("-port"))&& !(argList.contains("-search")))
             {
                 if(argList.size()==9)
                 {
                     if(argList.get(0).contentEquals("-print"))
                     {
                         argList.remove(0);
                         print = true;
                     }
                     else
                     {
                         System.out.println("Invalid argument");
                         System.exit(1);
                     }
                 }

                 if(argList.size()==8)
                 {
                     AppointmentBook appointmentBook;
                     try{
                     checkValidDate(argList.get(2) + " " + argList.get(3));
                     checkValidDate(argList.get(5) + " " + argList.get(6));
                 } catch (ParseException e) {
                 System.out.println("Dates not in valid format.. Please try again");
                 System.exit(1);
                }
                 // check for description is not empty
                 if ((!(argList.get(1).trim().isEmpty()))) {
                     Appointment appointment = new Appointment(argList.get(1), argList.get(2) + " " + argList.get(3) + " " + argList.get(4), argList.get(5) + " " + argList.get(6) + " " + argList.get(7));
                     appointmentBook = new AppointmentBook(argList.get(0));
                     appointmentBook.addAppointment(appointment);
                     System.out.println("New Appointment added!!");
                     if(print){
                         System.out.println(appointment);
                     }

                 } else {
                     System.out.println("Description not provided..or empty description..error");
                     System.exit(1);
                 }
                    System.exit(1);
                 }
             }


             for (int i = 0; i < 6; i++) {
            if (argList.get(i).contentEquals("-host")) {
                hostName = argList.get(i + 1).toString();
            }
            if (argList.get(i).contentEquals("-port")) {
                portString = argList.get(i + 1).toString();
            }
            if (argList.get(i).contentEquals("-search")) {
                search = true;
            }
            if(argList.get(i).contentEquals("-print")){
                print = true;
             }
        }

    if (hostName == null) {
        usage(MISSING_ARGS);

    } else if (portString == null) {
        usage("Missing port");
    }

    try {
        port = Integer.parseInt(portString);

    } catch (NumberFormatException ex) {
        usage("Port \"" + portString + "\" must be an integer");
        return;
    }
    if (hostName != null && portString != null && !search && !print) {
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);
    } else if (hostName != null && portString != null && ((search && !print)||(!search  && print))) {
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);
    }else if(hostName != null && portString != null && search && print){
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);
        argList.remove(0);

    }

    else {
        System.out.println("invalid arguments.. Please try again..");
        System.exit(1);
    }
}catch (ArrayIndexOutOfBoundsException e){
    System.out.println("Invalid arguments");
}
catch (Exception e)
{
    System.out.println(e.getMessage());
}

        if (argList.size() == 8&& !search ) {
            try {
                checkValidDate(argList.get(2) + " " + argList.get(3));

                checkValidDate(argList.get(5) + " " + argList.get(6));
            } catch (ParseException e) {
                System.out.println("Dates not in valid format.. Please try again");
                System.exit(1);
            }
            // check for description is not empty
                if ((!(argList.get(1).trim().isEmpty()))) {
                    Appointment appointment = new Appointment(argList.get(1), argList.get(2) + " " + argList.get(3) + " " + argList.get(4), argList.get(5) + " " + argList.get(6) + " " + argList.get(7));
                    MySingleBook = new AppointmentBook(argList.get(0),appointment,"-single");

                } else {
                    System.out.println("Description not provided..or empty description..error");
                    System.exit(1);
                }


            }
        else if (argList.size() == 7&& search) {
            try {
                checkValidDate(argList.get(1) + " " + argList.get(2));

                checkValidDate(argList.get(4) + " " + argList.get(5));
            } catch (ParseException e) {
                System.out.println("Dates not in valid format please try again");
                System.exit(1);
            }
            Appointment appointment = new Appointment(" ", argList.get(1) + " " + argList.get(2) + " " + argList.get(3), argList.get(4) + " " + argList.get(5) + " " + argList.get(6));
            appointment.setDate(argList.get(1) + " " + argList.get(2) + " " + argList.get(3),argList.get(4) + " " + argList.get(5) + " " + argList.get(6));
            MySearchBook = new AppointmentBook(argList.get(0),appointment,"-search");

        }
        else
        {
            System.out.println("Invalid no of arguments.. Please try again");
            System.exit(1);
        }

            AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

            HttpRequestHelper.Response response = null;

            try {
                if (search) {
                    if (MySearchBook != null) {
                        response = client.getValues(MySearchBook);
                    } else if (MySingleBook != null) {
                        response = client.getValues(MySingleBook);
                    } else {
                        throw new Exception("No data to search for");
                    }
                } else if (MySingleBook != null && MySingleBook.singleAppoinement != null) {
                    response = client.addKeyValuePair(MySingleBook.getOwnerName(), MySingleBook.singleAppoinement);
                    if(print)
                    {
                        //System.out.println(MySingleBook);
                        System.out.println(MySingleBook.singleAppoinement);
                        System.out.println("Appointment Added!!");
                    }
                    else{
                        System.out.println("Appointment Added!!");
                    }
                } else {
                    response = client.getAllKeysAndValues();
                }
                checkResponseCode(HttpURLConnection.HTTP_OK, response);
            } catch (IOException ex) {
                error("While contacting server: " + ex.getMessage() + ",\nPlease try again with a a valid host name\n");
                return;
            } catch (Exception e) {
                System.out.println("Invalid data");
                System.exit(1);
            }
        if(search) {
            System.out.println(response.getContent());
            System.exit(1);
        }
         System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */

    private static void checkResponseCode(int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    /**
     * Used to print error messages
     * @param message message to be printed
     */
    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }
    /**
     * This method checks the correct format for date and time (MM/dd/yyyy hh:mm)
     * This method checks the form of dd/mm/yyyy in the form of dd/mm - 2 digits and yyyy - 4 digits
     * @param parsedate check for entered begindate and end date
     * @throws ParseException if dates are in invalid format
     */
    private static void checkValidDate(String parsedate) throws ParseException {

        if(parsedate == null || !(parsedate.matches("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$")))
        {
            System.out.println("Please enter date in valid format...");
            System.exit(1);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm"); //Validate Date time format
        dateFormat.setLenient(false);
        dateFormat.parse(parsedate);

    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [key] [value]");
        err.println("  host    Host of web server");
        err.println("  port    Port of web server");
        err.println("  key     Key to query");
        err.println("  value   Value to add to server");
        err.println();
        err.println("This simple program posts key/value pairs to the server");
        err.println("If no value is specified, then all values are printed");
        err.println("If no key is specified, all key/value pairs are printed");
        err.println();

        System.exit(1);
    }


    /**
     * Readme function contains the readme of all useful information the user may need to know.
     */
    private static void Readme() {
        System.out.println("README has been called for Project 4 By- POOJA MANE");
        System.out.println("This program is a AppointmentBook application which takes a very specific amount of arguments");
        System.out.println();
        System.out.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>\n" +
                "args are (in this order):\n" +
                "owner The person whose owns the appt book\n" +
                "description A description of the appointment\n" +
                "beginTime When the appt begins\n" +
                "endTime When the appt ends\n" +
                "options are (options may appear in any order):\n" +
                "-host hostname Host computer on which the server runs\n" +
                "-port port Port on which the server is listening\n" +
                "-search Appointments should be searched for\n" +
                "-print Prints a description of the new appointment\n" +
                "-README Prints a README for this project and exits\n");
        System.exit(1);
    }

}
