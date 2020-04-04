package Restaurant.Properties;

public final class Tables{
    private int tableNumber;
    private int numberOfSeats;
    private boolean inSmokingAreas;
    private boolean booked;
    private String clientName;

    public Tables(int tableNumber, int numberOfSeats,
                  boolean inSmokingAreas){
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        this.inSmokingAreas = inSmokingAreas;
        this.booked = false;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isInSmokingAreas() {
        return inSmokingAreas;
    }

    public void setInSmokingAreas(boolean inSmokingAreas) {
        this.inSmokingAreas = inSmokingAreas;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object obj){
        return this.tableNumber == ((Tables)obj).getTableNumber()
                && this.numberOfSeats == ((Tables)obj).getNumberOfSeats()
                && this.inSmokingAreas == ((Tables)obj).isInSmokingAreas();
    }

    @Override
    public String toString(){
        return "Table number: " + tableNumber + "\n"
                + "Number of seats: " + numberOfSeats + "\n"
                + "Is in a smoking area: " + inSmokingAreas
                + "Is booked: " + booked;
    }

}
