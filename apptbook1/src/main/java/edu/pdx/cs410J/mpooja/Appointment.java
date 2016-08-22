package edu.pdx.cs410J.mpooja;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Used to create appointment with details including description , begin time and end time.
 * has description for storing description for appointment
 * has begintime for storing begin time for appointment
 * has endtime for storing end time for appointment
 */
 public class Appointment extends AbstractAppointment implements Comparable<AbstractAppointment> {
    private DateFormat ShortDateFormat;
    private Date beginTime;
   private Date endTime;
   private String description;

  /**
   * Used to initialise private members description ,begintime , endtime
   * Checks for entered date in valid format
   * @param description description of appointment
   * @param beginTime begintime of appointment
   * @param endTime endtime of appointment
     */
  Appointment(String description, String beginTime , String endTime){
      ShortDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
      //Check for bad data
      try{
          if(beginTime.contains("\"")||endTime.contains("\""))
              throw new IllegalArgumentException("Date and time cannot contain quotes ");

          String[] tempStart = beginTime.split(" ");
          String[] tempEnd= endTime.split(" ");

          if(!tempStart[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!tempEnd[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")) {
              throw new IllegalArgumentException("Invalid Date Format");
          }

          if(!tempStart[1].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")||!tempEnd[1].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
              throw new IllegalArgumentException("Time format must follow mm:hh (12 hour time)");

          if(!tempStart[2].matches("(am|pm|AM|PM)")&&!tempEnd[2].matches("(am|pm|AM|PM)"))
              throw new IllegalArgumentException("Time must include am/pm");
      }
      catch(IllegalArgumentException ex){
          System.out.println(ex.getMessage());
          System.exit(1);
      }

      setDate(beginTime,endTime);
    this.description = description;

  }

    /**
     * Used to initialize private data members as null
     */
    Appointment(){
        this.description =" ";
        this.beginTime=null;
        this.endTime = null;
    }

    /**
     * This method is used to set begintime and endtime in date format
     * @param beginning begin time to set in date format
     * @param end end time to set in date format
     */
    private void setDate(String beginning, String end){
        try {

            this.beginTime=ShortDateFormat.parse(beginning);
            this.endTime = ShortDateFormat.parse(end);
        }
        catch(ParseException ex){
            System.out.println("Error Parsing the time, please enter valid time, dont forget to include am/pm " +ex.getMessage());
            System.exit(1);
        }
    }
  /**
   * Used to get begin time in date format for appointment created.
   * @return beginTime begin time of appointment
     */
  @Override
  public String getBeginTimeString() {
      if(beginTime != null)
          return (ShortDateFormat.format(beginTime));
      else
          return "";
   }

  /**
   * Used to get end time in date format for the appointment created.
   * @return endtime end time of appointment
     */
  @Override
  public String getEndTimeString() {
      if(endTime != null)
          return (ShortDateFormat.format(endTime));
      else
          return "";
   }

  /**
   * Used to get description for appointment created.
   * @return description description of appointment
     */
  @Override
  public String getDescription() {
    return  description;
  }

    /**
     * Used to get begin time for created appointment
     * @return begintime of appointment
     */
    @Override
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * Used to get end time for created appointment
     * @return endtime of appointment
     */
    @Override
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Used to calculate duration of appointment from begintime to end time (HH:mm format)
     * @return duration of appointment
     */
    public String duration(){
        long duration =beginTime.getTime()-endTime.getTime();
        long diffMinutes = duration / (60 * 1000) % 60;
        long diffHours = duration / (60 * 60 * 1000);
        if(diffHours ==0)
            return -1*diffMinutes + " minutes";
        else
            return "  "+-1*diffHours+":"+ -1*diffMinutes +"min";
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(AbstractAppointment o) {
        try {
            if (this.beginTime == null) {
                throw new NullPointerException("No start time to compare");
            }
            if (o.getBeginTime() == null) {
                throw new NullPointerException("No begin time to compare");
            }
            long diff = this.beginTime.getTime()-o.getBeginTime().getTime();

            if (diff > 0) {
                return 1;
            }
            if (diff < 0) {
                return -1;
            }
            if (diff == 0) {
                long enddiff = this.endTime.getTime()-o.getEndTime().getTime();

                if(enddiff >0){
                    return 1;
                }
                if(enddiff<0){
                    return -1;
                }
                if(enddiff == 0){
                    int descriptiondiff = this.description.compareTo(o.getDescription());
                    if(descriptiondiff >0){
                        return 1;
                    }
                    if(descriptiondiff<0){
                        return -1;
                    }
                                    }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        return 0;
    }
    }

