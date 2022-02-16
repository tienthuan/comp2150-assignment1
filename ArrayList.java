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
}
