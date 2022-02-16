public class Main{
    public static void main(String[] args){
        ArrayList<Student> list = new ArrayList<Student>();
        //Add elements in custom ArrayList
        Student x = new Student("as1");
        Student y = new Student("as2");
        Student z = new Student("as3");
        Student a = new Student("as4");
        list.add(x);
        list.add(y);
        list.add(z);
        list.add(a);
        list.add(x);
        list.add(y);
        list.add(a);
        list.add(z);

        System.out.println(list.size());
    }
}