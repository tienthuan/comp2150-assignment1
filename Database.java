public class Database {
    //could use hashtable instead?
    //arraylist used here is a custom one, not one from java lib
    private ArrayList<Tutor> tutorArr = new ArrayList<Tutor>();
    private ArrayList<Student> studentArr = new ArrayList<Student>();
    private Boolean quitFlag = false;

    private String addTutor(String userid, String hoursString){
        int hoursInt = Integer.parseInt(hoursString);
        Tutor tutor = new Tutor(userid,hoursInt);
        for(int i = 0; i < tutorArr.size(); i ++){
            if(tutorArr.get(i).getUserID().equals(userid)) return "DUPLICATE";
        }
        tutorArr.add(tutor);
        return "CONFIRMED";
    }

    private String addStudent(String userid){
        for(int i = 0; i < studentArr.size(); i ++){
            if(studentArr.get(i).getUserID().equals(userid)) return "DUPLICATE";
        }
        Student student = new Student(userid);
        studentArr.add(student);
        return "CONFIRMED";
    }

    private String addTopic(String topicName,String userid,String priceString){
        topicName = topicName.strip();
        userid = userid.strip();
        int price = Integer.parseInt(priceString);

        //look through tutor arr, find specific tutor then loop through topics to find duplicates.
        for(int i = 0; i < tutorArr.size(); i++){
            String ID = tutorArr.get(i).getUserID();
                if(ID.equals(userid)){
                    if(tutorArr.get(i).getTopic().equals(topicName)) return "DUPLICATE";
                    else tutorArr.get(i).setTopic(topicName, price);;
                    return "CONFIRMED";
                }
        }
        return "NOT FOUND";
    }

    private void setAppointment(String studentID,int studentInt, String tutorID,int tutorInt, String topic,int money,int time){
        studentArr.get(studentInt).setAppointment(tutorID, topic, time, money);
        tutorArr.get(tutorInt).setAppointment(studentID,topic,time,money);
    }

    private String findTutor(int studentInt, String topic, int hours){//studentInt is the student's place in the student arraylist
            for(int i = 0; i < tutorArr.size(); i++){
                    int remaining = tutorArr.get(i).getRemaining();
                    if(tutorArr.get(i).getTopic().equals(topic) & remaining > 0){
                        if(remaining >= hours){
                            setAppointment(studentArr.get(studentInt).getUserID(), studentInt, tutorArr.get(i).getUserID(), i, topic, hours, tutorArr.get(i).getTopicPrice());
                            tutorArr.get(i).setRemaining(remaining - hours);
                            hours = 0;
                        }
                        else{
                            setAppointment(studentArr.get(studentInt).getUserID(), studentInt, tutorArr.get(i).getUserID(), i, topic, remaining, tutorArr.get(i).getTopicPrice());
                            hours -= tutorArr.get(i).getRemaining();
                            tutorArr.get(i).setRemaining(0);
                        }
                    }
            }
        if(hours == 0){
            return "SUCCESS";
        }
        else return "FAIL";
    }

    private String request(String studentID,String topicName,String hoursString){
        topicName = topicName.strip();
        int hours = Integer.parseInt(hoursString);
        studentID = studentID.strip();
        String output = "NOT FOUND";
        //loop through student arr
        for(int i = 0; i < studentArr.size(); i++){
            //if student is found
            if(studentArr.get(i).getUserID().equals(studentID)){
                //find suitable tutor
                output = findTutor(i,topicName,hours);
            }
        }
        return output; 
    }

    private String printAppointments(ArrayList<Appointment> arr,boolean isStudent){//only 2 types of users so I use boolean
        String output = "";
        int totalHoursPerPerson = 0;
        int totalMoneyPerPerson = 0;
        output += "_______________________";
        for(int i = 0; i < arr.size(); i++){
            String person = arr.get(i).getpersonID();
            String topic = arr.get(i).getTopic();
            int hours = arr.get(i).getHours();
            int cost = arr.get(i).getCost();
            int totalMoneyPerTopic = hours * cost;
                totalHoursPerPerson += hours;
                totalMoneyPerPerson += totalMoneyPerTopic;
            if(isStudent)
                output += String.format("Appointment:Tutor: %s,topic: %s,hours: %d,total cost: %d \n",person,topic,hours,totalMoneyPerTopic);
            else
                output += String.format("Appointment:Student %s,topic: %s,hours: %d,total revenue: %d \n",person,topic,hours,totalMoneyPerTopic);
        }
        output += "________________________";
        output += "Total number of hours of tutoring: " + String.valueOf(totalHoursPerPerson) + "\n";
        output += "Total cost of tutoring: " + String.valueOf(totalMoneyPerPerson) + "\n";
        return output;
    }

    private String studentReport(String userID){
        String output = String.format("Report for Student %s",userID) + "\n";
        for(int i = 0; i < studentArr.size(); i++){
            if(studentArr.get(i).getUserID().equals(userID)){
                ArrayList<Appointment> studentAppointments = studentArr.get(i).getAppointments();
                String studentAppointmentReport = printAppointments(studentAppointments, true);
                output += studentAppointmentReport;
                return output;
            }
        }
        return "NOT FOUND";
    }

    private String tutorReport(String userID){
        String output = String.format("Report for Tutor %s",userID) + "\n";
        for(int i = 0; i < studentArr.size(); i++){
            if(tutorArr.get(i).getUserID().equals(userID)){
                ArrayList<Appointment> tutorAppointments = tutorArr.get(i).getAppointments();
                String tutorAppointmentReport = printAppointments(tutorAppointments, false);
                output += tutorAppointmentReport;
                return output;
            }
        }
        return "NOT FOUND";
    }

    public boolean getQuitFlag(){
        return quitFlag;
    }

    private String quit(){
        quitFlag = true;
        return "BYE";
    }
    
    public void input(String input){
        //check if comment
        if(input.charAt(0) == '#')
            return;
        String[] split = input.split(" ");
        String command = split[0];
        switch(command){
            case "TUTOR":
                output(addTutor(split[1],split[2]));
                break;
            case "STUDENT":
                output(addStudent(split[1]));
                break;
            case "TOPIC":
                output(addTopic(split[1],split[2],split[3]));
                break;
            case "REQUEST":
                output(request(split[1],split[2],split[3]));
                break;
            case "STUDENTREPORT":
                output(studentReport(split[1]));
                break;
            case "TUTORREPORT":
                output(tutorReport(split[1]));
                break;
            case "QUIT":
                output(quit());
                break;
            default:
                output("Sorry, command is unknown");
                break;
        }
    }

    private void output(String output){
        System.out.println(output);
    }

}
