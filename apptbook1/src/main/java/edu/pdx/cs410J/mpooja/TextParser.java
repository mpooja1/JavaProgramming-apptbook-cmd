package edu.pdx.cs410J.mpooja;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class parses data from text file and creates AppointmentBook
 */
class TextParser implements AppointmentBookParser {
    private File file;

    /**
     * This method parses the text file and creates Appointmentbook with associated appointment details.
     * It handles exceptions occurred while reading file , malformed data ,incorrect datetime format in file
     * @return AbstractionAppointmentbook created after parsing file.
     * @throws ParserException
     */
    public AbstractAppointmentBook parse() throws ParserException {
        FileInputStream fis ;
        try {
            fis = new FileInputStream(file);
            //Construct BufferedReader from InputStreamReader+
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line ;
            AppointmentBook appointmentBook = new AppointmentBook(br.readLine());
            while ((line = br.readLine()) != null) {
                String appointmentdetails[] = line.split(",,,,//");
                //System.out.println(appointmentdetails[1]);
                String description = appointmentdetails[0];
                String begintime = appointmentdetails[1];
                String endtime = appointmentdetails[2];

                String[] tempStart = begintime.split(" ");
                String[] tempEnd= endtime.split(" ");

                checkValidDate(tempStart[0]+ " "+ tempStart[1]);
                checkValidDate(tempEnd[0] + " "+ tempEnd[1]);

                //Validates if description is not empty
                if ((!(description).trim().isEmpty())) {
                    Appointment appointment = new Appointment(description, begintime, endtime);
                    appointmentBook.addAppointment(appointment);
                }
                else {
                    System.out.println("Empty description while reading from files... Files Malformed.. Please try again");
                    System.exit(1);

                }
            }
            br.close();
            return  appointmentBook;

        }catch (ParseException e) {
            System.out.println("Dates not in proper format while parsing");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Text Files Malformed.. Please try again");
            System.exit(1);
        }

        return null;
    }

    /**
     * This method is used to provide access to file so that parse() can use it
     * @param file File object created in main()
     * @throws IOException If file error
     */
     void getFileName(File file) throws IOException {
         this.file=file;
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

}

