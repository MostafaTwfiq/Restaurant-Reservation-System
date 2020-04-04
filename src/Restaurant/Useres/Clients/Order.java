package Restaurant.Useres.Clients;

import Restaurant.Vectors.DishesVector;

public final class Order {
    private String clientName;
    private int tableNumber;

    private DishesVector dishesVector;

    public Order(String clientName, int tableNumber){
        this.clientName = clientName;
        this.tableNumber = tableNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public DishesVector getDishesVector() {
        return dishesVector;
    }

    public void setDishesVector(DishesVector dishesVector) {
        this.dishesVector = dishesVector;
    }

    @Override
    public String toString(){
        return "Client name: " + clientName + "\n"
                + "Table number: " + tableNumber;
    }
}
