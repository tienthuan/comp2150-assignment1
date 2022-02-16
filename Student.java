public class Student{
    private String userID;
    private ArrayList<Appointment> appointments;

    public Student(String userid){
        userID = userid;
        appointments = new ArrayList<Appointment>();
    }
    
    public String getUserID(){
        return userID;
    }

    public void setAppointment(String tutor,String subject,int time,int money){
        Appointment appointment = new Appointment(tutor, subject, time, money);
        appointments.add(appointment);
    }

    public ArrayList<Appointment> getAppointments(){
        return appointments;
    }
}
