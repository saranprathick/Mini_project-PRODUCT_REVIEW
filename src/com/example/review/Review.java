package com.example.review;

import com.example.BaseEntity.BaseEntity;

public abstract class Review extends BaseEntity {
    private int productId;
    private String customerName;
    private int rating;
    private String comment;

    public Review() {
    }

    public Review(int reviewId, int productId, String customerName, int rating, String comment) {
        super(reviewId);
        this.productId = productId;
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters for productId, customerName, rating, and comment

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getReviewType();

	public abstract int getReviewId();
	 public String getType() {
	        return "Review";
	    }
	
}
