public class Tutor{
    
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
    }

    //getters
    public String getUserID(){
        return userID;
    }
    public String getTopic(){
        return topic;
    }
    public int getTopicPrice(){
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

//loop through student arr
        /* for(int i = 0; i < studentArr.size(); i++){
            //if student is found
            if(studentArr.get(i).getID().equals(studentID)){
                //find suitable tutor
                for(int j = 0; j < tutorArr.size();j++){
                    int remaining = tutorArr.get(i).getRemaining();
                    //if tutor is correct and have remaining time
                    if(tutorArr.get(i).getTopic().equals(topicName) & remaining > 0){
                        
                        if(remaining >= hours){
                            //first enroll the student into suitable tutor then set remaining hours of tutor  
                            studentArr.get(i).setEnrolment(topicName, Integer.parseInt(hoursString), tutorArr.get(j).getTopicPrice());
                            tutorArr.get(i).setRemaining(remaining - hours);
                            hours = 0;
                        }
                        else{
                            //enroll the student into found tutor, hours will be > 0, tutor remaining hour will be 0 and keep finding
                            studentArr.get(i).setEnrolment(topicName, tutorArr.get(j).getRemaining(), tutorArr.get(j).getTopicPrice());
                            hours -= tutorArr.get(j).getRemaining();
                            tutorArr.get(i).setRemaining(0);
                        }
                    }
                }
                if(hours > 0) return "FAIL";
                else return "SUCCESS";
            }
        }
        return "NOT FOUND"; */