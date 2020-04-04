package Restaurant.Vectors;

import Restaurant.Properties.Tables;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public final class TablesVector extends MyVector<Tables> {

    public TablesVector(int length){
        super(length);
    }

    public TablesVector(){
        super();
    }

    public int find(int tableNumber){
        for (int i = 0; i < count; i++){
            if (((Tables)arr[i]).getTableNumber() == tableNumber)
                return i;
        }
        return -1;
    }

    public int getUnReservedTable(int numberOfSeats, boolean inSmokingAreas){
        for (int i = 0; i < count; i++) {
            if (isQualified(i, numberOfSeats, inSmokingAreas))
                return i;
        }
        return -1;
    }

    public TablesVector getReservedTables(){
        TablesVector reservedTables = new TablesVector(count/2);
        for (int i = 0; i < count; i++) {
            if (((Tables)arr[i]).isBooked())
                reservedTables.add((Tables)arr[i]);
        }
        return reservedTables;
    }

    private boolean isQualified(int index, int numberOfSeats,
                                boolean inSmokingAreas){
        return !((Tables)arr[index]).isBooked()
                && ((Tables)arr[index]).getNumberOfSeats() == numberOfSeats
                && ((Tables)arr[index]).isInSmokingAreas() == inSmokingAreas;
    }

    public int getNextTableNumber(){
        int[] tablesNumber = new int[count + 1];

        for (int i = 0; i < count; i++)
            tablesNumber[((Tables)arr[i]).getTableNumber() - 1] = 1;

        for (int i = 0; i < count; i++)
            if (tablesNumber[i] != 1) return i + 1;

        return count + 1;
    }

    public void sortTables() {
        Arrays.sort(arr, 0, count, new Comparator<Object>() {
            public int compare(Object a, Object b){
                return ((Tables) a).getTableNumber() - ((Tables) b).getTableNumber();
            }
        });
    }

    @Override
    public int findObj(Object obj){
        return find(((Tables)obj).getTableNumber());
    }

    @Override
    public Tables get(int index){
        if (index < 0 || index >= count)
            throw new NullPointerException();

        return (Tables) arr[index];
    }
}
