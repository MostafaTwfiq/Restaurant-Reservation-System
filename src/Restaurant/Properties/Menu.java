package Restaurant.Properties;

public final class Menu {
    private String dishName;
    private int price;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "Dish name: " + dishName + "\n"
                + "Price: " + price;
    }
}
