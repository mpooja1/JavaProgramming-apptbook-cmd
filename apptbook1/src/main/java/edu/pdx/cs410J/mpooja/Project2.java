package edu.pdx.cs410J.mpooja;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class for the CS410J appointment book Project
 * It creates appointment book with appointment as per arguments entered through commandline.
  */
public class Project2 {
    /**
     * Entry point for execution of code.
     * has argList as List of arguments for storing appointment details
     * Creates Appointment book with Appointment details as entered through command line
     * Parses Date time entered in correct format
     * Exception handling for date parsing , if less or more arguments are provided and description is empty
     * checks for -README options and prints README accordingly
     * checks for -print option and prints description of appointment details.
     * checks for -textFile option , creates , reads and writes to textile.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        //System.err.println("Missing command line arguments");
        AppointmentBook appointmentBook;
        Appointment appointment;
        List<String> argList = new ArrayList(); // arglist stores appointment details entered through command line
        TextDumper textDumper = new TextDumper();
        TextParser textParser = new TextParser();
        String fileName = null;
        boolean printFlag = false;


       for (String arg : args) argList.add(arg);

        if (argList.contains("-README")) {
            System.out.println("\nREADME FILE - PROJECT 2 - POOJA MANE\n" +
                    "This project is used to create appointment book for owner including appointment details entered through commandline.\n" +
                    "and optionally creating text file for that appointmentbook\n"+
                    "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
                    "args are (n this order):\n" +
                    "owner -The person whose owns the appointment book\n" +
                    "description- A description of the appointment\n" +
                    "beginTime- When the appointment begins (24-hour time)\n" +
                    "endTime- When the appointment ends (24-hour time)\n" +
                    "options are (options may appear in any order):\n" +
                    "-print Prints a description of the new appointment\n" +
                    "-README Prints a README for this project and exits\n" +
                    "-textFile file creates text file for appointment book\n"+
                    "Date and time should be in the format: mm/dd/yyyy hh:mm\n" +
                    "Description should not be empty\n" +
                    "If -print and appointment details are specified it prints appointment description");
            System.exit(1);
        }
        try {
        for (int i = 0; i <= 1; i++) {
            if (argList.get(i).contentEquals("-textFile") && !(argList.get(i + 1).startsWith("-"))) {
                fileName = argList.get(i+1);
                break;
            }
        }

        if(argList.contains("-textFile") && fileName == null)
        {
            System.out.println("Invalid Arguments ..Text file name not provided.. Please try again");
            System.exit(1);
        }

            if (argList.size() == 2 && (fileName != null)){
                System.out.println("Invalid arguments .. appointment details not specified");
                System.exit(1);
            }

            if (argList.size() == 11 && (fileName != null) && ((argList.get(0).contentEquals("-print")) || (argList.get(2).contentEquals("-print")))) {
                    argList.remove(0);
                    argList.remove(0);
                    argList.remove(0);
                    printFlag = true;
            }

            if (argList.size() == 10 && (fileName != null) && !(argList.get(0).contentEquals("-print"))) {
                argList.remove(0);
                argList.remove(0);

            }
            if ((argList.size() == 9 && argList.get(0).equals("-print"))) {
                argList.remove(0);
                printFlag = true;
            }
            if ((argList.size() == 8) && !(argList.get(0).equals("-print"))) {

                //if dates are invalid format and description is not empty creates appointment book with specified appointment
               // checkValidDate(argList.get(2) + " " + argList.get(3));
                //checkValidDate(argList.get(4) + " " + argList.get(5));

                // check for description is not empty
                if ((!(argList.get(1).trim().isEmpty()))) {

                    appointment = new Appointment(argList.get(1), argList.get(2) + " " + argList.get(3) + " "+ argList.get(4), argList.get(5) + " " + argList.get(6)+" "+argList.get(7));
                    appointmentBook = new AppointmentBook(argList.get(0));
                    appointmentBook.addAppointment(appointment);


                    if (printFlag)
                        System.out.println(appointment);

                    if(fileName==null && !(printFlag)) {
                        System.out.println(appointmentBook);
                        System.out.println(appointment);
                    }

                    if (fileName != null) {

                        File file = new File(fileName);
                        AbstractAppointmentBook appointmentBook1;
                        if(file.exists())
                        {
                            FileInputStream fstream = new FileInputStream(file);
                            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                            String ownerName = br.readLine();
                            if ((appointmentBook.getOwnerName() != null && appointmentBook.getOwnerName().equalsIgnoreCase(ownerName))) {
                                textDumper.getFileName(file);
                                textParser.getFileName(file);
                                appointmentBook1=textParser.parse(); //Read file and create Appointmentbook using parse()
                                appointmentBook1.addAppointment(appointment); // Add appointment to created Appointmentbook
                                textDumper.dump(appointmentBook1); //Write back Appointmentbook to file
                                System.out.println("Appointmentbook has been created and added to file..");
                                System.out.println(appointmentBook1);
                            }
                            else {
                                System.out.println("Files are different ..Please try again");
                                System.exit(1);
                            }
                        }
                        else
                        {
                            file.createNewFile();
                            textDumper.getFileName(file);
                            textDumper.dump(appointmentBook);
                            System.out.println("Appointmentbook has been created and added to file..");
                            System.out.println(appointmentBook);
                        }


                    }

                } else
                    throw new Exception("Empty string in description.. Please try again");
            } else
                throw new Exception(" ");
        } catch (IOException e) {
            System.out.println("Bad format of data..");
        } catch (ParseException e) {
            System.out.println("Invalid Date Formats .. Please try again");
        }catch (ParserException e)        {
            System.out.println("Error while parsing files.. Please try again");
        }catch (StringIndexOutOfBoundsException e){
            System.out.println("Data Malformed in text file,unable to parse.. Please try again");
        }
        catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Empty string in description.. Please try again")) {
                System.out.println(e.getMessage());
            } else {
                System.out.println("Invalid arguments .. Please try again");
            }
        }
        System.exit(1);

    }

    /**
     * This method checks the correct format for date and time (MM/dd/yyyy HH:mm)
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm"); //Validate Date time format
        dateFormat.setLenient(false);
        dateFormat.parse(parsedate);

    }
}