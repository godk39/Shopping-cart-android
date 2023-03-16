package hanu.a2_1901040029.models;

public class CartProduct {
    private int id;
    private String name;
    private String thumbnail;
    private int unitPrice;
    private int quantity;
    private int sumPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public CartProduct(int id, String name, String thumbnail, int unitPrice, int quantity, int sumPrice) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.sumPrice = sumPrice;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", sumPrice=" + sumPrice +
                '}';
    }
}
