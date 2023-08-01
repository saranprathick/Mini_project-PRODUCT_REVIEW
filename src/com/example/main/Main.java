package com.example.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.example.ReviewDao.ReviewDao;
import com.example.product.Product;
import com.example.productDao.ProductDao;
import com.example.review.ConcreteReview;
import com.example.review.Review;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/minipro";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Saran@2003";

    private enum UserRole {
        ADMIN, USER
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            ProductDao productDao = new ProductDao(connection);
            ReviewDao reviewDao = new ReviewDao(connection);

            Scanner scanner = new Scanner(System.in);

            // Prompt for authentication
            System.out.print("Enter your role (ADMIN/USER): ");
            String roleInput = scanner.nextLine();

            UserRole userRole = UserRole.valueOf(roleInput.toUpperCase());

            if (userRole == UserRole.ADMIN) {
                performAdminActions(scanner, productDao, reviewDao);
            } else if (userRole == UserRole.USER) {
                performUserActions(scanner, productDao, reviewDao);
            } else {
                System.out.println("Invalid role. Exiting...");
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL Connector/J driver not found. Make sure it is added to the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performAdminActions(Scanner scanner, ProductDao productDao, ReviewDao reviewDao) {
        int choice;

        do {
            // Display admin menu
            System.out.println("1. Add a product");
            System.out.println("2. View all products");
            System.out.println("3. Update a product");
            System.out.println("4. Delete a product");
            System.out.println("5. View reviews for a product");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading the integer

            // Declare product-related variables here
            String productName;
            String productDescription;

            switch (choice) {
                case 1:
                    // Add a product
                    System.out.print("Enter the product name: ");
                    productName = scanner.nextLine();
                    // Check if the product name already exists in the database
                    Product existingProduct = productDao.getProductByName(productName);
                    if (existingProduct != null) {
                        System.out.println("Product name already exists.");
                    } else {
                        System.out.print("Enter the product description: ");
                        productDescription = scanner.nextLine();
                        Product newProduct = new Product();
                        newProduct.setProductName(productName);
                        newProduct.setProductDescription(productDescription);
                        productDao.addProduct(newProduct);
                        System.out.println("Product added successfully!");
                    }
                    break;

                case 2:
                    // View all products
                    List<Product> products = productDao.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        System.out.println("Products:");
                        for (Product product : products) {
                            System.out.println("ID: " + product.getProductId());
                            System.out.println("Name: " + product.getProductName());
                            System.out.println("Description: " + product.getProductDescription());
                            System.out.println("------------------------------");
                        }
                    }
                    break;

                case 3:
                    // Update a product
                    System.out.print("Enter the product ID to update: ");
                    int productIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character after reading the integer
                    Product productToUpdate = productDao.getProductById(productIdToUpdate);
                    if (productToUpdate == null) {
                        System.out.println("Product not found with ID: " + productIdToUpdate);
                    } else {
                        System.out.print("Enter the updated product name: ");
                        productName = scanner.nextLine();
                        System.out.print("Enter the updated product description: ");
                        productDescription = scanner.nextLine();
                        productToUpdate.setProductName(productName);
                        productToUpdate.setProductDescription(productDescription);
                        productDao.updateProduct(productToUpdate);
                        System.out.println("Product updated successfully!");
                    }
                    break;

                case 4:
                    // Delete a product
                    System.out.print("Enter the product ID to delete: ");
                    int productIdToDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character after reading the integer
                    productDao.deleteProduct(productIdToDelete);
                    System.out.println("Product deleted successfully!");
                    break;

                case 5:
                    // View reviews for a product by product name
                    System.out.print("Enter the product name to view reviews: ");
                    String productNameForReviews = scanner.nextLine();
                    List<ConcreteReview> reviewsForProduct = reviewDao.getReviewsForProduct(productNameForReviews);
                    if (reviewsForProduct.isEmpty()) {
                        System.out.println("No reviews found for the product.");
                    } else {
                        System.out.println("Product Reviews for " + productNameForReviews + ":");
                        for (ConcreteReview review : reviewsForProduct) {
                            System.out.println("Review ID: " + review.getReviewId());
                            System.out.println("Customer Name: " + review.getCustomerName());
                            System.out.println("Rating: " + review.getRating());
                            System.out.println("Comment: " + review.getComment());
                            System.out.println("------------------------------");
                        }
                    }
                    break;

                case 6:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }


    private static void performUserActions(Scanner scanner, ProductDao productDao, ReviewDao reviewDao) {
        int choice;

        do {
            // Display user menu
            System.out.println("1. View all products");
            System.out.println("2. Review a product");
            System.out.println("3. View reviews for a product");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // View all products
                    List<Product> products = productDao.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        System.out.println("Products:");
                        for (Product product : products) {
                            System.out.println("ID: " + product.getProductId());
                            System.out.println("Name: " + product.getProductName());
                            System.out.println("Description: " + product.getProductDescription());
                            System.out.println("------------------------------");
                        }
                    }
                    break;

                case 2:
                    // Review a product
                    System.out.print("Enter the product name to review: ");
                    String productNameToReview = scanner.nextLine();
                    Product productToReview = productDao.getProductByName(productNameToReview);
                    if (productToReview == null) {
                        System.out.println("Product not found with name: " + productNameToReview);
                    } else {
                        System.out.print("Enter your name: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Enter your rating (1-5): ");
                        int rating = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character after reading the integer
                        System.out.print("Enter your comment: ");
                        String comment = scanner.nextLine();
                        ConcreteReview newReview = new ConcreteReview();
                        newReview.setCustomerName(customerName);
                        newReview.setRating(rating);
                        newReview.setComment(comment);
                        newReview.setProductId(productToReview.getProductId());
                        reviewDao.addReview(newReview);
                        System.out.println("Product reviewed successfully!");
                    }
                    break;

                case 3:
                    // View reviews for a product
                    System.out.print("Enter the product ID or product name to view reviews: ");
                    String productIdentifier = scanner.nextLine();
                    List<ConcreteReview> reviews;
                    try {
                        int productId = Integer.parseInt(productIdentifier);
                        reviews = reviewDao.getReviewsForProduct(productId);
                    } catch (NumberFormatException e) {
                        reviews = reviewDao.getReviewsForProduct(productIdentifier);
                    }

                    if (reviews.isEmpty()) {
                        System.out.println("No reviews found for the product.");
                    } else {
                        System.out.println("Product Reviews:");
                        for (ConcreteReview review : reviews) {
//                            System.out.println("Product" + review);
                            System.out.println("Review ID: " + review.getReviewId());
                            System.out.println("Customer Name: " + review.getCustomerName());
                            System.out.println("Rating: " + review.getRating());
                            System.out.println("Comment: " + review.getComment());
                            System.out.println("Review Type: " + review.getReviewType());
                            System.out.println("------------------------------");
                        }
                    }

                    break;

                case 4:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

}
