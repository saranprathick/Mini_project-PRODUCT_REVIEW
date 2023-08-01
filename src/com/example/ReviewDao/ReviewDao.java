package com.example.ReviewDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.productDao.ProductDao;
import com.example.review.ConcreteReview;
import com.example.review.Review;

public class ReviewDao {
    private Connection connection;
    private ProductDao productDao; // We need access to ProductDao to get product ID by name

    public ReviewDao(Connection connection) {
        this.connection = connection;
        this.productDao = new ProductDao(connection); // Initialize ProductDao
    }

    // Method to add a new review to the database
    public void addReview(Review newReview) {
        String insertQuery = "INSERT INTO reviews (product_id, customer_name, rating, comment) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setInt(1, newReview.getProductId());
            pstmt.setString(2, newReview.getCustomerName());
            pstmt.setInt(3, newReview.getRating());
            pstmt.setString(4, newReview.getComment());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all reviews for a given product from the database
    public List<ConcreteReview> getReviewsForProduct(int productId) {
        List<ConcreteReview> reviews = new ArrayList<>();
        String selectQuery = "SELECT * FROM reviews WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {
            pstmt.setInt(1, productId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int reviewId = resultSet.getInt("review_id");
                String customerName = resultSet.getString("customer_name");
                int rating = resultSet.getInt("rating");
                String comment = resultSet.getString("comment");

                ConcreteReview review = new ConcreteReview(reviewId, productId, customerName, rating, comment);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Method to fetch all reviews for a given product name from the database
    public List<ConcreteReview> getReviewsForProduct(String productName) {
        // Get the product ID using the product name from the ProductDao
        int productId = productDao.getProductIDByName(productName);
        if (productId == -1) {
            return new ArrayList<>(); // Return an empty list if the product is not found
        }

        return getReviewsForProduct(productId);
    }

    // Method to update a review in the database
    public void updateReview(ConcreteReview review) {
        String updateQuery = "UPDATE reviews SET customer_name = ?, rating = ?, comment = ? WHERE review_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, review.getCustomerName());
            pstmt.setInt(2, review.getRating());
            pstmt.setString(3, review.getComment());
            pstmt.setInt(4, review.getReviewId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a review from the database
    public void deleteReview(int reviewId) {
        String deleteQuery = "DELETE FROM reviews WHERE review_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a review by review ID
    public ConcreteReview getReviewById(int reviewId) {
        String selectQuery = "SELECT * FROM reviews WHERE review_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {
            pstmt.setInt(1, reviewId);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String customerName = resultSet.getString("customer_name");
                int rating = resultSet.getInt("rating");
                String comment = resultSet.getString("comment");

                ConcreteReview review = new ConcreteReview(reviewId, productId, customerName, rating, comment);
                return review;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
