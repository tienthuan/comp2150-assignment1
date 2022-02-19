import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main{
     public static void main(String[] args){
        try{
        Database database = new Database();
        //Change from read file to user input here
        File file = new File("sample_input.txt");
        Scanner scanner = new Scanner(file);
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        while(!database.getQuitFlag()){
            //System.out.print("enter your next command: ");
            String input = scanner.nextLine();
            database.input(input);
        }
        scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } 
    }   
}
