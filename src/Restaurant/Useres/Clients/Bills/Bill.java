package Restaurant.Useres.Clients.Bills;

import Restaurant.Vectors.DishesVector;

public final class Bill {

    private String clientName;
    private String date;
    private float bill;
    private DishesVector dishes;

    public Bill(String clientName, String date,
                float bill, DishesVector dishes){
        this.clientName = clientName;
        this.date = date;
        this.bill = bill;
        this.dishes = dishes;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBill() {
        return String.format("%.2f ", bill) + new EGPCurrency().toString();
    }

    public float getFloatBill(){
        return bill;
    }

    public void setBill(float bill) {
        this.bill = bill;
    }

    public DishesVector getDishes() {
        return dishes;
    }

    public void setDishes(DishesVector dishes) {
        this.dishes = dishes;
    }

    private String getAllDishesString(){
        StringBuffer string = new StringBuffer();
        for (int i = 0; i < dishes.getLength(); i++) {
            string.append(dishes.get(i).toString() + "\n\n");
        }
        return string.toString();
    }

    @Override
    public String toString(){
        return "Client name: " + clientName + "\n"
                + "Bill date: " + date + "\n"
                + "The amount: " + bill + new EGPCurrency() + "\n"
                + "Dishes: \n"
                + getAllDishesString();
    }
}
