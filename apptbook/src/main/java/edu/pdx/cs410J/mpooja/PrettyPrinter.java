package edu.pdx.cs410J.mpooja;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class prints appointment book along with appointmentdetails in good format
 * Created by pooja on 7/14/2016.
 */
 class PrettyPrinter implements AppointmentBookDumper {
    private File file;
    private boolean flag=false;

    /**
     * Used to assign values to file and flag so that dump method can use it.
     * @param file file in which appointment book is to be printed
     * @param flag decides whether to print on console or in file
     */
     PrettyPrinter(File file , boolean flag){
        this.flag =flag;
        this.file= file;
    }

    /**
     * This method dumps the appointmentbook and prints it either on console or in text file
     * @param abstractAppointmentBook Appointment Book to be printed
     * @throws IOException if issue in file handling operations
     */
    @Override
    public void dump(AbstractAppointmentBook abstractAppointmentBook) throws IOException {
        if(flag){
            System.out.println(abstractAppointmentBook+"\n");
            System.out.println("#\tDescription--\t---Start Time---\t\t---End Time---\t\t--Duration \n");
            int x = 0;

            for (Object obj : abstractAppointmentBook.getAppointments()) {
                Appointment appointmentmine = (Appointment) obj;
                System.out.println(++x + "\t" + appointmentmine.getDescription()+"\t"+appointmentmine.getBeginTime() + "\t" +  appointmentmine.getEndTime() + "\t" + appointmentmine.duration() + "\n");
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println("\nAppointment Book Created on: " + dateFormat.format(date) + "\n");

        }
        else {
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.write(abstractAppointmentBook + "\n");
                writer.write("#\tDescription---\t\t---Start Time---\t\t\t\t\t--End Time--\t\t\t--Duration \n");
                int x = 0;
                for (Object obj : abstractAppointmentBook.getAppointments()) {
                    Appointment appointmentmine = (Appointment) obj;
                    writer.write(++x + "\t" + appointmentmine.getDescription()+"\t"+appointmentmine.getBeginTime() + "\t" +  appointmentmine.getEndTime() + "\t" + appointmentmine.duration() + "\n");
                }
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                writer.write("\nAppointment Book Created on: " + dateFormat.format(date) + "\n");
                // All done
                writer.flush();
                writer.close();
                System.out.println("AppointmentBook formatted.. Please check your file...");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


}
