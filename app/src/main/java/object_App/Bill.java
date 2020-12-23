package object_App;

public class Bill {
    private int ID;
    private String date;
    private String names;
    private String amount;
    private String price;
    private long total;

    public Bill(int ID, String date, String names, String amount, String price, long total) {
        this.ID = ID;
        this.date = date;
        this.names = names;
        this.amount = amount;
        this.price = price;
        this.total = total;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
