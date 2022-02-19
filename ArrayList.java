import java.util.Arrays;
public class ArrayList<T> {
    private Object array[];   
    private int count;   

    public ArrayList(){   
    array = new Object[10];//initial size is 10   
    count = 0;   
    }   

    private void increaseCapacity(){
        int doubleCapacity = array.length * 2;
        array = Arrays.copyOf(array, doubleCapacity);
    }

    public void add(T elem){
        if (count == array.length){
            increaseCapacity();
        }
        array[count++] = elem;
    }

    public T get(int index){
        if (index < 0 || index >= count){
               throw new IndexOutOfBoundsException("Index: " + index + ", Size "
                            + index);
        }
        return (T) array[index];
    //idk how to fix the error right here
    
    }
    public int size(){
        return count;
    }

    public ArrayList<T> copy(){
        ArrayList<T> copy = new ArrayList<T>();
        for(int i = 0; i < count; i ++){
            copy.add(this.get(i));
        }
        return copy;
    }
    //had to implement custom bubble sort for tutorArr
    //I think if we were to deal with a big database we'd keep the sorted arr somewhere? idk
    public void PriceSort(ArrayList<Tutor> list)  {
        int n = list.size() - 1;
        while (n != 0) {
            int i;
            for (i = 0; i < n; i++) {
                if (list.get(i).getTopicPrice() - list.get(i + 1).getTopicPrice()  > 0)  { 
                    Tutor temp = list.get(i);
                    list.array[i] = list.get(i + 1);
                    list.array[i + 1] = temp;
                    }
                else if(list.get(i).getTopicPrice() - list.get(i + 1).getTopicPrice() == 0 && list.get(i).getUserID().compareTo(list.get(i + 1).getUserID())  > 0){
                    Tutor temp = list.get(i);
                    list.array[i] = list.get(i + 1);
                    list.array[i + 1] = temp;
                }

            }
        n= i-1;
        }
    }
}
//I tried to sort a generic arraylist but every answer on stack overflow yields no result.
//Trying to take this arraylist apart into student arraylist and tutor arraylist makes it more difficult.