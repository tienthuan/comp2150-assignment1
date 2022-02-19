import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class DatabaseTesting {
    static Database database = new Database();
    
    @Test
    public void stage1_testPersonInsertion(){
        String output1 = database.input("STUDENT student1");
        String output2 = database.input("STUDENT student1");
        String expected1 = "CONFIRMED";
        String expected2 = "DUPLICATE";
        assertEquals(output1, expected1);
        assertEquals(output2, expected2);

        String output3 = database.input("TUTOR tutor1 10");
        String output4 = database.input("TUTOR tutor1 10");
        assertEquals(output3, expected1);
        assertEquals(output4, expected2);
    }

    @Test
    public void stage2_testTopic(){
        String output1 = database.input("TOPIC OO tutor1 10");
        String output2 = database.input("TOPIC OO tutor1 10");
        String expected1 = "CONFIRMED";
        String expected2 = "DUPLICATE";
        assertEquals(output1, expected1);
        assertEquals(output2, expected2);

        String output3 = database.input("TOPIC OO tutor69 10");
        String expected3 = "NOT FOUND";
        assertEquals(output3,expected3);
    }

    @Test
    public void stage3_testRequest(){
        database.input("STUDENT student2");
        database.input("TUTOR tutor2 15");
        database.input("TUTOR tutor3 1");
        database.input("TOPIC OO tutor2 10");
        database.input("TOPIC OO tutor3 5");

        String output5 = database.input("REQUEST student31415926535 OO 20");
        String output1 = database.input("REQUEST student1 OO 100");
        String output2 = database.input("REQUEST student1 OO 0");
        String output3 = database.input("REQUEST student1 OO 1");
        String output4 = database.input("REQUEST student2 OO 20");
        

        String expected1 = "FAIL";
        String expected2 = "FAIL";
        String expected3 = "SUCCESS";
        String expected4 = "SUCCESS";
        String expected5 = "NOT FOUND";

        assertEquals(output1,expected1);
        assertEquals(output2,expected2);
        assertEquals(output3,expected3);
        assertEquals(output4,expected4);
        assertEquals(output5,expected5);
    }

    @Test
    public void stage4_testInput(){
        String output1 = database.input("STUDENT          student3         ");
        String output2 = database.input("STUDENT student4    ASDFASDGA   FASDGAE654814  ");
        String output3 = database.input(" ");
        String output4 = database.input("I hate C++, Java is superior");

        String expected1 = "CONFIRMED";
        String expected2 = "CONFIRMED";
        String expected3 = "Input not found";
        String expected4 = "Sorry, command is unknown";
        assertEquals(output1,expected1);
        assertEquals(output2,expected2);
        assertEquals(output3,expected3);
        assertEquals(output4,expected4);
    }

    @Test
    public void stage5_testReport(){
        String output1 = database.input("STUDENTREPORT student1");
        String output2 = database.input("STUDENTREPORT student3");
        String output3 = database.input("TUTORREPORT tutor1");
        String output4 = database.input("TUTORREPORT tutor3");   
        //we choose the cheapest tutor first
        String expected1 = "Report for Student student1\n"+
        "_______________________\n"+
        "Appointment:Tutor: tutor3,topic: OO,hours: 1,total cost: 5 \n"+ 
        "________________________\n"+
        "Total number of hours of tutoring: 1\n"+
        "Total cost of tutoring: 5\n";

        String expected2 = "Report for Student student3\n"+
        "_______________________\n"+
        "________________________\n"+
        "Total number of hours of tutoring: 0\n"+
        "Total cost of tutoring: 0\n";

        String expected3 = "Report for Tutor tutor1\n"+
        "_______________________\n"+
        "Appointment:Student student2,topic: OO,hours: 10,total revenue: 100 \n"+
        "________________________\n"+
        "Total number of hours of tutoring: 10\n"+
        "Total cost of tutoring: 100\n";

        String expected4 = "Report for Tutor tutor3\n"+
        "_______________________\n"+
        "Appointment:Student student1,topic: OO,hours: 1,total revenue: 5 \n"+
        "________________________\n"+
        "Total number of hours of tutoring: 1\n"+
        "Total cost of tutoring: 5\n";
        
        assertEquals(output1,expected1);
        assertEquals(output2,expected2);
        assertEquals(output3,expected3);
        assertEquals(output4,expected4);
    }
    @After
    public void cleanUp(){
        System.out.println("bye");
    }
}
