package Models;

public class ProductFilter {

    private String productName;
    private String genderForProduct;
    private String productBrand;

    ProductFilter(String productName){
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGenderForProduct() {
        return genderForProduct;
    }

    public void setGenderForProduct(String genderForProduct) {
        this.genderForProduct = genderForProduct;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }
}
