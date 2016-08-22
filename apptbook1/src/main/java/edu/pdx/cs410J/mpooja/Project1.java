package edu.pdx.cs410J.mpooja;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class for the CS410J appointment book Project
 * It creates appointment book with appointment as per arguments entered through commandline.
 * -print prints appointment details or README file accordingly
 */
public class Project1 {
    /**
     * Entry point for execution of code.
     * has argList as List of arguments for storing appointment details
     * Creates Appointment book with Appointment details as entered through command line
     * print appointment details if print option is passed through commandline
     * Parses Date time entered in correct format
     * Exception handling for date parsing , if less or more arguments are provided and description is empty
     * checks for README options and prints README accordingly
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        //System.err.println("Missing command line arguments");
        AppointmentBook aptbook;
        Appointment appointment;
        List<String> argList = new ArrayList(); // arglist stores appointment details entered through command line
        for (String arg : args) {
            argList.add(arg);
        }
        if (argList.contains("-README")) {

            System.out.println("\nREADME FILE - PROJECT 1 - POOJA MANE\n" +
                    "This project is used to create appointment book for owner including appointment details entered through commandline.\n" +
                    "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
                    "args are (in this order):\n" +
                    "owner -The person whose owns the appointment book\n" +
                    "description- A description of the appointment\n" +
                    "beginTime- When the appointment begins (24-hour time)\n" +
                    "endTime- When the appointment ends (24-hour time)\n" +
                    "options are (options may appear in any order):\n" +
                    "-print Prints a description of the new appointment\n" +
                    "-README Prints a README for this project and exits\n" +
                    "Date and time should be in the format: mm/dd/yyyy hh:mm\n" +
                    "Description should not be empty\n" +
                    "If -print and appointment details are specified it prints appointment description");

            System.exit(1);
        } else {
            try {
                boolean printFlag = false;
                if ((argList.size() == 7 && argList.get(0).equals("-print"))) {
                    argList.remove(0);
                    printFlag = true;
                }
                if ((argList.size() == 6 && !(argList.get(0).equals("-print")))) {

                    //if dates are invalid format and description is not empty creates appointmentbook with specified appointment
                    checkValidDate(argList.get(2) + " " + argList.get(3));
                    checkValidDate(argList.get(4) + " " + argList.get(5));

                    // check for description is not empty
                    if ((!(argList.get(1).trim().isEmpty())))  {
                        aptbook = new AppointmentBook(argList.get(0));
                        appointment = new Appointment(argList.get(1), argList.get(2) + " " + argList.get(3), argList.get(4) + " " + argList.get(5));
                        aptbook.addAppointment(appointment);

                        if (printFlag)
                            System.out.println(appointment);
                        else {
                            System.out.println(aptbook);
                            System.out.println(appointment);
                        }
                    } else
                        throw new Exception("Empty string in description.. Please try again");
                } else
                    throw new Exception(" ");
            } catch (ParseException e) {
                System.out.println("Invalid Date Formats .. Please try again");
            } catch (Exception e) {
                if (e.getMessage().equalsIgnoreCase("Empty string in description.. Please try again")) {
                    System.out.println(e.getMessage());
                } else {
                    System.out.println("Invalid arguments .. Please try again");
                }
            }
        }
        System.exit(1);
    }
    /**
     * This method checks the correct format for date and time (MM/dd/yyyy HH:mm)
     * This method checks the form of dd/mm/yyyy in the form of dd/mm - 2 digits and yyyy - 4 digits
     * @param parsedate check for entered begindate and end date
     * @throws ParseException if invalid date format
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