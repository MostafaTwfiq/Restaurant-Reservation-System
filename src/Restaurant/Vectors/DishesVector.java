package Restaurant.Vectors;

import Restaurant.Properties.Dishes.Dish;
import Restaurant.Properties.Menu;

import java.util.Arrays;

public final class DishesVector extends MyVector<Dish> {
    private String dishesType;

    public DishesVector(int length){
        super(length);
        this.dishesType = "";
    }

    public DishesVector(){
        super();
        this.dishesType = "";
    }

    public String getDishesType() { return dishesType; }

    public int find(String dishName){
        for (int i = 0; i < count; i++){
            if (((Dish)arr[i]).getDishName().equals(dishName))
                return i;
        }
        return -1;
    }

    @Override
    public void add(Dish obj) {
        if (count == 0)
            this.dishesType = obj.getDishType();
        addDish(obj);
    }

    private void addDish(Dish obj) {
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

    @Override
    public int findObj(Object obj){
        return find(((Dish)obj).getDishName());
    }

    @Override
    public Dish get(int index){
        if (index < 0 || index >= count)
            throw new NullPointerException();

        return (Dish) arr[index];
    }

    public Menu[] getDishesNames(){
        if (isEmpty())
            return null;

        Menu[] dishes = new Menu[count];
        for (int i = 0; i < count; i++){
            dishes[i] = new Menu();
            dishes[i].setDishName(((Dish)arr[i]).getDishName());
            dishes[i].setPrice(((Dish)arr[i]).getPrice());
        }
        return dishes;
    }
}