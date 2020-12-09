package object_App;

public class Product {
    private String ID;
    private String nameProduct;
    private long price;
    private int amount;
    private String type;
    private String describe;
    private int image;
    private String producer;

    public Product(String ID, String nameProduct, long price, int amount, String type, String describe, int image, String producer) {
        this.ID = ID;
        this.nameProduct = nameProduct;
        this.price = price;
        this.amount = amount;
        this.type = type;
        this.describe = describe;
        this.image = image;
        this.producer = producer;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
