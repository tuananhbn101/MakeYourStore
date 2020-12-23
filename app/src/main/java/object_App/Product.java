package object_App;

public class Product {
    private int ID;

    public Product(int ID, String nameProduct, long importprice, long price, int amount, String type, String describe, String image, String producer) {
        this.ID = ID;
        this.nameProduct = nameProduct;
        this.importPrice = importprice;
        this.price = price;
        this.amount = amount;
        this.type = type;
        this.describe = describe;
        this.image = image;
        this.producer = producer;
    }

    private String nameProduct;
    private long importPrice;
    private long price;
    private int amount;
    private String type;
    private String describe;
    private String image;
    private String producer;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getImportprice() {
        return importPrice;
    }

    public void setImportprice(long importprice) {
        this.importPrice = importprice;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
