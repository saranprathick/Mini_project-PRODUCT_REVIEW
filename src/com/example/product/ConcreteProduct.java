package com.example.product;

public class ConcreteProduct extends Product {
    private int productId;
    private String productName;
    private String productDescription;

    public ConcreteProduct() {
    }

    public ConcreteProduct(int productId, String productName, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
    }

    // Getters and Setters for productId and productName
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    

    public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Override
    public String toString() {
        return "ConcreteProduct [productId=" + productId + ", productName=" + productName + ", productDescription="
                + productDescription + "]";
    }

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}