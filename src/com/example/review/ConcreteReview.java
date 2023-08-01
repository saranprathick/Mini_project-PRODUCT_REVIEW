package com.example.review;

public class ConcreteReview extends Review {
    private int reviewId;
    private int productId;
    private String customerName;
    private int rating;
    private String comment;

    public ConcreteReview() {
    }

    public ConcreteReview(int reviewId, int productId, String customerName, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters for reviewId and productId
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

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

	@Override
    public String toString() {
        return "ConcreteReview [reviewId=" + reviewId + ", productId=" + productId + ", customerName=" + customerName
                + ", rating=" + rating + ", comment=" + comment + "]";
    }

	@Override
    public String getReviewType() {
        return "ConcreteReview";
    }

	
}