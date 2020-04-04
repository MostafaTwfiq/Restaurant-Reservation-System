package Restaurant.Useres.Clients;

import Restaurant.Properties.Dishes.Dish;
import Restaurant.Useres.Users;
import Restaurant.Vectors.DishesVector;
import Restaurant.Properties.Tables;
import Restaurant.Useres.Clients.Bills.Bill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client extends Users {
    protected DishesVector dishes;
    protected Tables table;
    protected float bill;
    protected DateFormat dateFormat;
    protected Date date;

    public Client(String name, String userName,
                  String password) {
        super(name, userName, password, "Client");
        this.dishes = new DishesVector(10);
        this.bill = 0;
        this.table = null;
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.date = new Date();
    }

    public DishesVector getDishes() {
        return dishes;
    }

    public void setDishes(DishesVector dishes) {
        this.dishes = dishes;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public boolean isVipClient(){
        return false;
    }

    public void addDish(Dish dish){
        dishes.add(dish);
    }

    private void calculateBill(){
        for (int i = 0; i < dishes.getLength(); i++){
            bill += dishes.get(i).getPrice() + (dishes.get(i).getPrice() * dishes.get(i).getTaxesInt()) / 100f;
        }
    }

    public Bill payTheBill(){
        calculateBill();
        table.setBooked(false);
        table.setClientName("");
        table = null;
        Bill bill = new Bill(name, dateFormat.format(date), this.bill, dishes);
        this.bill = 0;
        dishes = new DishesVector(10);
        return bill;
    }

}
