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
 * has searchAppointmentOnly to specify for searching appointment
 * has singleAppointment to add appointment
 * @author pooja on 6/29/2016.
 */
  class AppointmentBook extends AbstractAppointmentBook {

    public String owner;
    public ArrayList<Appointment> appointments;
  Appointment searchAppointmentOnly;
  Appointment singleAppoinement;

  /**
   * Used to initialize private data members
   * @param owner ownerName
   * @param Appointment appointment details to be added to list
     */
  AppointmentBook(String owner, Appointment Appointment)
  {
    this.owner = owner;
    appointments = new ArrayList<Appointment>();
    addAppointment(Appointment);
    searchAppointmentOnly = null;
    singleAppoinement = null;
  }

    /**
     * Used to initialise private data member owner
     * @param owner - ownerName
     */
    AppointmentBook(String owner)
    {
        this.owner = owner;
      appointments = new ArrayList<Appointment>();
      singleAppoinement = null;
      searchAppointmentOnly=null;
    }

  /**
   * Used to initialize private data members as null
   */
  AppointmentBook(){
    this.owner =" ";
    appointments = new ArrayList<Appointment>();
    singleAppoinement = null;
    searchAppointmentOnly = null;
  }

  /**
   * Used to inintialize private data members
   * @param owner ownerName
   * @param tempAppointment Appointment to be added or searched
     * @param s to specify whether the appointment is for search or appointment to be added
     */
  public AppointmentBook(String owner, Appointment tempAppointment, String s) {
    if (s.equals("-search")) {
      this.owner = owner;
      appointments = null;
      searchAppointmentOnly = tempAppointment;
    }

    if(s.equals("-single")) {
      this.owner = owner;
      appointments = null;
      singleAppoinement = tempAppointment;
    }
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
        return appointments;
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
      appointments.add((Appointment)AbstractAppointment);
      Collections.sort(appointments);

    }

  }