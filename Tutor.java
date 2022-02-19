
public class Tutor {
    
    private String userID;
    private int hours;
    private int remaining;
    private String topic;
    private int topicPrice;

    private ArrayList<Appointment> appointments;

    public Tutor(String userid, int noOfHours){
        userID = userid;
        hours = noOfHours;
        remaining = noOfHours;
        topic = "none";
        topicPrice = 0;
        appointments = new ArrayList<Appointment>();
    }

    //getters
    public String getUserID(){
        return userID;
    }
    public String getTopic(){
        return topic;
    }
    public Integer getTopicPrice(){
        return topicPrice;
    }
    public int getHours(){
        return hours;
    }
    public int getRemaining(){
        return remaining;
    }
    public ArrayList<Appointment> getAppointments(){
        return appointments;
    }

    //setters
    public void setRemaining(int x){
        remaining = x;
    }
    public void setTopic(String cipot, int cipotPrice){//cipot is topic backwards
        topic = cipot;
        topicPrice = cipotPrice;
    }

    public void setAppointment(String tutor,String subject,int time,int money){
        Appointment appointment = new Appointment(tutor, subject, time, money);
        appointments.add(appointment);
    }
    
}
