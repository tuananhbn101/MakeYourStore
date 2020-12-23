package object_App;

public class Report {
    private String date;
    private int id;
    private long totalImport;
    private long totalSale;

    public Report(String date, int id, long totalimport, long totalsale) {
        this.date = date;
        this.id = id;
        this.totalImport = totalimport;
        this.totalSale = totalsale;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTotalImport() {
        return totalImport;
    }

    public void setTotalImport(long totalimport) {
        this.totalImport = totalimport;
    }

    public long getTotalSale() {
        return totalSale;
    }

    public void setTotalsale(long totalsale) {
        this.totalSale = totalsale;
    }
}
