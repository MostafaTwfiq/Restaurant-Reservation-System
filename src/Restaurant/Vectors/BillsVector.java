package Restaurant.Vectors;

import Restaurant.Useres.Clients.Bills.Bill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class BillsVector extends MyVector<Bill> {
    private DateFormat dateFormat;
    private Date date;

    public BillsVector(int length){
        super(length);
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.date = new Date();
    }

    public BillsVector(){
        super();
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.date = new Date();
    }

    public int find(String clientName){
        for (int i = 0; i < count; i++){
            if (((Bill)arr[i]).getClientName().equals(clientName))
                return i;
        }
        return -1;
    }

    public int find(String clientName, String date){
        for (int i = 0; i < count; i++){
            if ( ((Bill)arr[i]).getClientName().equals(clientName)
                    && ((Bill)arr[i]).getDate().equals(date) )
                return i;
        }
        return -1;
    }

    @Override
    public int findObj(Object obj){
        return find(((Bill)obj).getClientName(), ((Bill)obj).getDate());
    }

    @Override
    public Bill get(int index){
        if (index < 0 || index >= count)
            throw new NullPointerException();

        return (Bill) arr[index];
    }

    public BillsVector getBillsOfToday(){
        BillsVector bills = new BillsVector(super.count);
        for (int i = 0; i < super.count; i++){
            String billDate = getTodayDateWithOutTime(((Bill)arr[i]).getDate());
            String todayDate = getTodayDateWithOutTime(dateFormat.format(date));
            if (billDate.equals(todayDate))
                bills.add(((Bill)arr[i]));
        }
        return bills;
    }

    private String getTodayDateWithOutTime(String date){
        StringBuffer todayDate = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            todayDate.append(date.charAt(i));
        }
        return todayDate.toString();
    }
}
