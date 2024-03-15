package irfan.fadillah.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductLine {
    private int id_productline;
    private String productLine;
    private String description;


    public ProductLine(int id_productline, String productLine, String description){
        this.id_productline = id_productline;
        this.productLine = productLine;
        this.description = description;
    }

    @Override
    public  String toString(){
        return "ProductLine{" +
                "id_productline=" + id_productline +
                ", productLine='"+ productLine + '\'' +
                "description = " + description +
                '}';
    }

    public static void optionProductLine(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu ProductLine ===");
            System.out.println("1. Add ProductLine");
            System.out.println("2. View All ProductLine");
            System.out.println("3. Update ProductLine");
            System.out.println("4. Delete ProductLine");
            System.out.println("5. Back to Product");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Insert productLine : ");
                    String productLine = scanner.next();
                    System.out.print("Insert description : ");
                    String description = scanner.next();

                    ProductLine.addProductLine(productLine,description);
                    System.out.print("ProductLine added successfully");
                    break;

                case 2:
                    // View All Items
                    List<ProductLine> allProductLines = ProductLine.getAllProductLine();
                    System.out.println("All ProductLine: " + allProductLines);
                    break;
//
                case 3:
                    // Update Item
                    System.out.print("Insert ProductLine ID to update: ");
                    int updateid = scanner.nextInt();
                    System.out.print("Insert productLine : ");
                    String updateproductLine = scanner.next();
                    System.out.print("Insert description : ");
                    String updatedescription = scanner.next();

                    ProductLine.updateProductLines(updateid,  updateproductLine, updatedescription);
                    System.out.println("ProductLine updated successfully!");
                    break;
//
                case 4:
                    // Delete Item
                    System.out.print("Insert ProductLine ID to delete: ");
                    int deleteId = scanner.nextInt();

                    ProductLine.deleteProductLines(deleteId);
                    System.out.println("ProductLine deleted successfully!");
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    public static List<ProductLine> getAllProductLine(){
        List<ProductLine> productLines = new ArrayList<>();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM productlines");
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                int id_productline = resultSet.getInt("id_productline");
                String productLine = resultSet.getString("productLine");
                String description = resultSet.getString("description");
                productLines.add(new ProductLine(id_productline, productLine, description));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return productLines;

    }

    public static void addProductLine(String productLine, String description){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO productlines (productLine, description) VALUES (?,?)")){
            preparedStatement.setString(1, productLine);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void updateProductLines(int id_productline,String productLine, String description){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE productlines SET productLine= ?, description = ? WHERE id_productline = ?")){
            preparedStatement.setString(1, productLine);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, id_productline);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

        public static void deleteProductLines(int id_productline){
            try(Connection connection = conn.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM productlines WHERE id_productline = ?")){
                preparedStatement.setInt(1, id_productline);
                preparedStatement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


}
