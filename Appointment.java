public class Appointment {
    private String personID;
    private String topic;
    private int hours; 
    private int cost;
    public Appointment(){
        personID = "none";
        topic = "none";
        hours = 0;
        cost = 0;
    }
    public Appointment(String id, String cipot, int time, int money){
        personID = id;
        topic = cipot;
        hours = time;
        cost = money;
    }
    String getpersonID(){
        return personID;
    }
    String getTopic(){
        return topic;
    }
    int getHours(){
        return hours;
    }
    int getCost(){
        return cost;
    }
}
