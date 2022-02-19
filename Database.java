public class Database {
    //could use hashtable instead?
    //arraylist used here is a custom one, not one from java lib
    private ArrayList<Tutor> tutorArr;
    private ArrayList<Student> studentArr;
    private Boolean quitFlag;

    public Database(){
        tutorArr = new ArrayList<Tutor>();
        studentArr = new ArrayList<Student>();
        quitFlag = false;
    }
    private String addTutor(String userid, String hoursString){
        int hoursInt = Integer.parseInt(hoursString);
        Tutor tutor = new Tutor(userid,hoursInt);
        for(int i = 0; i < tutorArr.size(); i ++){
            if(tutorArr.get(i).getUserID().equals(userid)) return String.format("%s has a duplicate",userid);
        }
        tutorArr.add(tutor);
        return String.format("%s has been added",userid);
    }

    private String addStudent(String userid){
        for(int i = 0; i < studentArr.size(); i ++){
            if(studentArr.get(i).getUserID().equals(userid)) return String.format("%s has a duplicate",userid);
        }
        Student student = new Student(userid);
        studentArr.add(student);
        return String.format("%s has been added",userid);
    }

    private String addTopic(String topicName,String userid,String priceString){
        topicName = topicName.strip();
        userid = userid.strip();
        int price = Integer.parseInt(priceString);

        //look through tutor arr, find specific tutor then loop through topics to find duplicates.
        for(int i = 0; i < tutorArr.size(); i++){
            String ID = tutorArr.get(i).getUserID();
                if(ID.equals(userid)){
                    if(tutorArr.get(i).getTopic().equals(topicName)) return String.format("topic %s for %s has a duplicate",topicName,userid);
                    else tutorArr.get(i).setTopic(topicName, price);;
                    return String.format("registered %s for tutor %s",topicName,userid);
                }
        }
        return String.format("%s was not found",userid);
    }
    private int getTotalHoursRemaining(){
        int output = 0;
        for(int i = 0; i < tutorArr.size(); i ++){
            output+=tutorArr.get(i).getRemaining();
        }
        return output;
    }
    private int getTutor(String ID){
        for(int i = 0; i < tutorArr.size(); i ++){
            if(tutorArr.get(i).getUserID().compareTo(ID) == 0){
                return i;
            }
        }
        return -1;
    }
    private void setAppointment(String studentID,int studentInt, String tutorID,int tutorInt, String topic,int money,int time){
        studentArr.get(studentInt).setAppointment(tutorID,topic, time, money);
        tutorArr.get(tutorInt).setAppointment(studentID,topic,time,money);
    }

    private String findTutor(int studentInt, String topic, int hours,ArrayList<Tutor> tutorArrSorted){//studentInt is the student's place in the student arraylist
        int remaining;
        String tutorID;
        int topicPrice;
        int tutorPosInOriginalArr;
        for(int i = 0; i < tutorArrSorted.size(); i++){
                    remaining = tutorArrSorted.get(i).getRemaining();
                    if((tutorArrSorted.get(i).getTopic().equals(topic) & remaining > 0) && hours > 0){
                        tutorID = tutorArrSorted.get(i).getUserID();
                        topicPrice = tutorArrSorted.get(i).getTopicPrice();
                        tutorPosInOriginalArr = getTutor(tutorID);
                        if(remaining >= hours){                          
                            setAppointment(studentArr.get(studentInt).getUserID(), studentInt,tutorID, tutorPosInOriginalArr, topic, topicPrice, hours);
                            tutorArr.get(tutorPosInOriginalArr).setRemaining(remaining - hours);
                            hours = 0;                            
                        }
                        else{
                            setAppointment(studentArr.get(studentInt).getUserID(), studentInt, tutorID, tutorPosInOriginalArr, topic, topicPrice, remaining);
                            hours -= tutorArrSorted.get(i).getRemaining();
                            tutorArr.get(tutorPosInOriginalArr).setRemaining(0);
                        }
                    }
        }
        return String.format("succesfully found tutors for %s, list of tutors will be in the report",studentArr.get(studentInt).getUserID());
    }

    private String request(String studentID,String topicName,String hoursString){
        topicName = topicName.strip();
        int hours = Integer.parseInt(hoursString);
        studentID = studentID.strip();
        String output =  String.format("Student %s was not found in the database",studentID);
        int totalRemainingHours = getTotalHoursRemaining();
        if(totalRemainingHours < hours || hours <= 0){
            return  String.format("Failed to find tutors for student %s in subject %s for %s hours",studentID,topicName,hours);
        }
        //loop through student arr
        for(int i = 0; i < studentArr.size(); i++){
            //if student is found
            if(studentArr.get(i).getUserID().equals(studentID)){
                //find suitable tutor
                ArrayList<Tutor> tutorArrCopy = tutorArr.copy();
                tutorArrCopy.PriceSort(tutorArrCopy);
                output = findTutor(i,topicName,hours,tutorArrCopy);
            }
        }
        return output; 
    }

    private String printAppointments(ArrayList<Appointment> arr,boolean isStudent){//only 2 types of users so I use boolean
        String output = "";
        int totalHoursPerPerson = 0;
        int totalMoneyPerPerson = 0;
        output += "_______________________\n";
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
        output += "________________________\n";
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
        for(int i = 0; i < tutorArr.size(); i++){
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
    
    private String reInitialize(){
        quitFlag = false;
        return "HI AGAIN";
    }
    public String input(String input){
        //check if comment
        if(!input.matches(".*\\w.*"))
            return "input not found";
        if(input.charAt(0) == '#' || quitFlag == true)
            return "";
        String[] split = input.split("\\s+");
        String command = split[0];
        switch(command){
            case "TUTOR":
                return output(addTutor(split[1],split[2]));
                //break;
            case "STUDENT":
                return output(addStudent(split[1]));
                //break;
            case "TOPIC":
                return output(addTopic(split[1],split[2],split[3]));
                //break;
            case "REQUEST":
                return output(request(split[1],split[2],split[3]));
                //break;
            case "STUDENTREPORT":
                return output(studentReport(split[1]));
                //break;
            case "TUTORREPORT":
                return output(tutorReport(split[1]));
                //break;
            case "QUIT":
                return output(quit());
                //break;
            case "END":
                return output(quit());
                //break;
            case "TURNON":
                return output(reInitialize());
            default:
                return output("Sorry, command is unknown");
                //break;
        }
    }

    private String output(String output){
        System.out.println(output);
        return output;
    }

}
