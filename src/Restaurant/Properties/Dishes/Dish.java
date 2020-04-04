package Restaurant.Properties.Dishes;

public abstract class Dish {
    private String dishType;
    private String dishName;
    private int price;
    private int taxes;

    public Dish(String dishType, String dishName,
                int price, int taxes){
        this.dishType = dishType;
        this.dishName = dishName;
        this.price = price;
        this.taxes = taxes;
    }

    public String getTaxes() {
        return String.format("%d", taxes) + "%";
    }

    public int getTaxesInt() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        if (!(dishType.equals("main_course") || dishType.equals("appetizer")
                || dishType.equals("desert")))
            throw new IllegalArgumentException();

        this.dishType = dishType;
    }

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
        if (price < 0) throw new IllegalArgumentException();
        this.price = price;
    }

    @Override
    public boolean equals(Object obj){
        return this.dishType.equals(((Dish)obj).getDishType())
                && this.dishName.equals(((Dish)obj).getDishName())
                && this.price == ((Dish)obj).getPrice()
                && this.taxes == ((Dish)obj).getTaxesInt();
    }

    @Override
    public String toString(){
        return "Dish type: " + dishType + "\n"
                + "Dish name: " + dishName + "\n"
                + "Dish price: " + price + "\n"
                + "Dish taxes: " + taxes;
    }
}
