package com.example.productDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.product.Product;

public class ProductDao {
    private Connection connection;

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new product to the database
    public void addProduct(Product product) {
        String insertQuery = "INSERT INTO products (product_name, product_description) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductDescription());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int productId = generatedKeys.getInt(1);
                product.setProductId(productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectQuery = "SELECT * FROM products";
        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");

                Product product = new Product(productId, productName, productDescription);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Method to update a product in the database
    public void updateProduct(Product product) {
        String updateQuery = "UPDATE products SET product_name = ?, product_description = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductDescription());
            pstmt.setInt(3, product.getProductId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a product from the database
    public void deleteProduct(int productId) {
        String deleteQuery = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a product by product ID
    public Product getProductById(int productId) {
        String selectQuery = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {
            pstmt.setInt(1, productId);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");

                Product product = new Product(productId, productName, productDescription);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get a product by product name
    public Product getProductByName(String productName) {
        String selectQuery = "SELECT * FROM products WHERE product_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {
            pstmt.setString(1, productName);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productDescription = resultSet.getString("product_description");

                Product product = new Product(productId, productName, productDescription);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getProductIDByName(String productName) {
        String selectQuery = "SELECT product_id FROM products WHERE product_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {
            pstmt.setString(1, productName);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("product_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the product is not found
    }
}
