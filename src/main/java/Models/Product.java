package Models;

public class Product{
    private Integer productId;
    private String productName;
    private String category;
    private String subCategory;
    private Boolean availability;
    private String condition;
    private String brand;
    private Integer price;

    public Product(Integer productId, String productName, String category, String subCategory, Boolean availability, String condition, String brand, Integer price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.subCategory = subCategory;
        this.availability = availability;
        this.condition = condition;
        this.brand = brand;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", availability='" + availability + '\'' +
                ", condition='" + condition + '\'' +
                ", Brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
