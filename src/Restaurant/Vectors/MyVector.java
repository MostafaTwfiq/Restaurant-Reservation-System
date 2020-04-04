package Restaurant.Vectors;

import java.util.Arrays;

public abstract class MyVector<T> {
    protected Object arr[];
    protected int count;
    protected int length;

    public MyVector(int length){
        this.arr = new Object[length];
        this.count = 0;
        this.length = length;
    }

    public MyVector(){
        this.count = 0;
        this.length = 0;
    }

    public void add(T obj){
        int index = findObj(obj);
        if (index != -1) {
            arr[index] = obj;
            return;
        }

        if (isFull()){
            if (length == 0) {
                length = 25;
                arr = new Object[length];
            }

            length *= 2;
            arr = Arrays.copyOf(arr, length);
        }
        arr[count++] = obj;
    }

    public void delete(int index){
        if (index < 0 || index >= count) throw new NullPointerException();
        if (index == count - 1) arr[index] = null;
        for (int i = index; i < count - 1; i++){
            arr[i] = arr[i + 1];
        }
        count--;
    }

    public abstract int findObj(Object obj);

    public abstract T get(int index);

    public int getLength(){
        return count;
    }

    public boolean isFull(){
        return count == length;
    }

    public boolean isEmpty(){
        return count == 0;
    }
}
