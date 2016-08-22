package edu.pdx.cs410J.mpooja;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * Used to create appointment book for specified owner including appointment details.
 * has owner for storing owner name
 * has list for storing appointment details
 * @author pooja on 6/29/2016.
 */
  class AppointmentBook extends AbstractAppointmentBook {

    private String owner;
    private ArrayList<Appointment> list;

  /**
   * Used to initialize private data members
   * @param owner ownerName
   * @param Appointment appointment details to be added to list
     */
  AppointmentBook(String owner, Appointment Appointment)
  {
    this.owner = owner;
    list = new ArrayList<Appointment>();
    addAppointment(Appointment);
  }

    /**
     * Used to initialise private data member owner
     * @param owner - ownerName
     */
    AppointmentBook(String owner)
    {
        this.owner = owner;
      list = new ArrayList<Appointment>();
    }

  /**
   * Used to initialize private data members as null
   */
  AppointmentBook(){
    this.owner =" ";
    list = new ArrayList<Appointment>();
  }

    /**
     * Used to get owner name
     * @return owner - owner name
     */
    @Override
    public String getOwnerName()
    {
       return owner;
    }

    /**
     * Used to get appointments created
     * @return list - list of appointments
     */
    @Override
    public Collection<Appointment> getAppointments()
    {
        return list;
    }

    /**
     * Used to add appointments created into list
     * @param AbstractAppointment - appointment details
     */
    @Override
    public void addAppointment(AbstractAppointment AbstractAppointment)
    {
    /* code for not allowing duplicate appointment
    boolean addAppointment = true;
      for(AbstractAppointment call:list){
        if(call.toString().equals(AbstractAppointment.toString())) {
          addAppointment = false;
        }
      }
      if(addAppointment) {
        list.add((Appointment)AbstractAppointment);
      }
    */
      list.add((Appointment)AbstractAppointment);
      Collections.sort(list);


    }

  }