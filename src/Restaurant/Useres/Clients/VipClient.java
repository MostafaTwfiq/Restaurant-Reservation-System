package Restaurant.Useres.Clients;

import Restaurant.Useres.Clients.Bills.Bill;
import Restaurant.Vectors.DishesVector;

public final class VipClient extends Client{

    private int clientDiscount;

    public VipClient(String name, String userName,
                  String password, int clientDiscount) {
        super(name, userName, password);
        super.role = "VipClient";
        this.clientDiscount = clientDiscount;
    }

    public int getClientDiscount() {
        return clientDiscount;
    }

    @Override
    public boolean isVipClient(){
        return true;
    }

    private void calculateBill(){
        for (int i = 0; i < dishes.getLength(); i++){
            bill += dishes.get(i).getPrice() + (dishes.get(i).getPrice() * dishes.get(i).getTaxesInt()) / 100f;
        }
    }

    @Override
    public Bill payTheBill(){
        calculateBill();
        applyTheDiscount();
        table.setBooked(false);
        table.setClientName("");
        table = null;
        Bill bill = new Bill(name, dateFormat.format(date), this.bill, dishes);
        this.bill = 0;
        dishes = new DishesVector(10);
        return bill;
    }

    private void applyTheDiscount(){
        bill -= (bill * clientDiscount) / 100.0f;
    }
}
