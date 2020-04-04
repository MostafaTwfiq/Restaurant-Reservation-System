package Restaurant.Properties;

import Restaurant.Properties.Dishes.Dish;
import Restaurant.Vectors.DishesVector;

public final class Food {
    private DishesVector dishes[];
    private int length;

    public Food(int numberOfDishesTypes, int numberOfDishes){
        this.length = numberOfDishesTypes;
        dishes = new DishesVector[length];

        for (int i = 0; i < length; i++)
            dishes[i] = new DishesVector(numberOfDishes);

    }

    public DishesVector getDishesVector(int index){
        if (outOfRange(index))
            throw new NullPointerException();

        return dishes[index];
    }

    public int getLength() {
        return length;
    }

    public void addDish(Dish dish, int index){
        if (outOfRange(index))
            throw new NullPointerException();

        if (dishes[index].getLength() != 0
                && !dishes[index].getDishesType().equals(dish.getDishType()))
            throw new IllegalArgumentException();

        dishes[index].add(dish);
    }

    public Dish getDish(int index, String dishName){
        if (outOfRange(index))
            throw new NullPointerException();

        int dishIndex = dishes[index].find(dishName);

        if (dishIndex == -1)
            throw new IllegalArgumentException();

        return dishes[index].get(dishIndex);
    }

    public void deleteDish(int index, String dishName){
        if (outOfRange(index))
            throw new NullPointerException();

        int dishIndex = dishes[index].find(dishName);

        if (dishIndex == -1)
            throw new IllegalArgumentException();

        dishes[index].delete(dishIndex);
    }

    public Menu[] getDishesNames(int index){
        return dishes[index].getDishesNames();
    }

    public String getDishesTypesWithIndex(){
        StringBuffer types = new StringBuffer();

        for (int i = 0; i < length; i++)
            types.append("Type: " + dishes[i].getDishesType() + "\nindex: " + i + "\n");

        return types.toString();
    }

    public String[] dishesTypesToArray(){
        String[] dishesTypes = new String[length];

        for (int i = 0; i < length; i++)
            dishesTypes[i] = dishes[i].getDishesType();

        return dishesTypes;
    }

    public int getDishTypeIndex(String dishType){
        for (int i = 0; i < length; i++) {
            if (dishes[i].getDishesType().equals(dishType))
                return i;
        }
        return -1;
    }

    private boolean outOfRange(int index){
        return index >= length;
    }
}
