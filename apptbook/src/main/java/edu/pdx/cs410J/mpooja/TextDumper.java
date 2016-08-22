package edu.pdx.cs410J.mpooja;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class dumps the content of Appointmentbook into the text file
 */
class TextDumper implements AppointmentBookDumper {

    private File file;

    /**
     * This method dumps the appointment book created into the text file
     * @param appointmentBook AppointmentBook to be dumped into the text file
     * @throws IOException If file does not exist
     */
    public void dump(AbstractAppointmentBook appointmentBook) throws IOException
    {
        FileWriter fw = new FileWriter(file, false);
        fw.write(appointmentBook.getOwnerName());
        ArrayList<AbstractAppointment> list = (ArrayList<AbstractAppointment>) appointmentBook.getAppointments();
        for (AbstractAppointment appointment : list) {
            fw.write("\n");
            fw.write(appointment.getDescription()+",,,,//"+appointment.getBeginTimeString()+",,,,//"+appointment.getEndTimeString());
        }
        fw.flush();
        fw.close();
    }

    /**
     * This method provides access to File object so that dump() can use it
     * @param file File object created in main()
     * @throws IOException If file error
     */
     void getFileName(File file) throws IOException {
        this.file=file;
    }

}