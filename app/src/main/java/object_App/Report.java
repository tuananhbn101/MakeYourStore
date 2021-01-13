package object_App;

public class Report {
    private String date;
    private int ID;
    private long totalImport;
    private long totalSale;
    private int IDEmployee;

    public Report(String date, int ID, long totalImport, long totalSale, int IDEmployee) {
        this.date = date;
        this.ID = ID;
        this.totalImport = totalImport;
        this.totalSale = totalSale;
        this.IDEmployee = IDEmployee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getTotalImport() {
        return totalImport;
    }

    public void setTotalImport(long totalImport) {
        this.totalImport = totalImport;
    }

    public long getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(long totalSale) {
        this.totalSale = totalSale;
    }

    public int getIDEmployee() {
        return IDEmployee;
    }

    public void setIDEmployee(int IDEmployee) {
        this.IDEmployee = IDEmployee;
    }
}
