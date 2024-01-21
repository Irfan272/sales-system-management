package irfan.fadillah.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class App {

    private static Connection getConnection() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/inventory_db";
        String username = "root";
        String password = "";
        try {
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static void addItem(String name, int quantity, double price) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO items (name, quantity, price) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM items")) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                items.add(new Item(id, name, quantity, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void updateItem(int id, String name, int quantity, double price) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE items SET name = ?, quantity = ?, price = ? WHERE id = ?")) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteItem(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM items WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Inventory Management System ===");
            System.out.println("1. Add Item");
            System.out.println("2. View All Items");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add Item
                    System.out.print("Enter product name: ");
                    String name = scanner.next();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    addItem(name, quantity, price);
                    System.out.println("Item added successfully!");
                    break;

                case 2:
                    // View All Items
                    List<Item> allItems = getAllItems();
                    System.out.println("All Items: " + allItems);
                    break;

                case 3:
                    // Update Item
                    System.out.print("Enter item ID to update: ");
                    int updateId = scanner.nextInt();
                    System.out.print("Enter updated product name: ");
                    String updatedName = scanner.next();
                    System.out.print("Enter updated quantity: ");
                    int updatedQuantity = scanner.nextInt();
                    System.out.print("Enter updated price: ");
                    double updatedPrice = scanner.nextDouble();

                    updateItem(updateId, updatedName, updatedQuantity, updatedPrice);
                    System.out.println("Item updated successfully!");
                    break;

                case 4:
                    // Delete Item
                    System.out.print("Enter item ID to delete: ");
                    int deleteId = scanner.nextInt();

                    deleteItem(deleteId);
                    System.out.println("Item deleted successfully!");
                    break;

                case 5:
                    // Exit
                    System.out.println("Exiting the Inventory Management System. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }
}
