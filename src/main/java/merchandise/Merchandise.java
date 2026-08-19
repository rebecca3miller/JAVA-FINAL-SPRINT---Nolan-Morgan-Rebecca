
package merchandise;

public class Merchandise {

    private int merchandiseId;
    private String name;
    private String description;
    private String itemType;
    private double price;
    private int stock;

    public Merchandise() {
    }

    public Merchandise(int merchandiseId, String name, String description, String itemType, double price, int stock) {
        this.merchandiseId = merchandiseId;
        this.name = name;
        this.description = description;
        this.itemType = itemType;
        this.price = price;
        this.stock = stock;
    }

    public int getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(int merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Merchandise{" +
                "merchandiseId=" + merchandiseId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", itemType='" + itemType + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
