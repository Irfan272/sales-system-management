package irfan.fadillah.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Product {
    private int id_product;
    private String productName;
    private int id_productline;
    private String productVendor;
    private String description;
    private double price;

    public Product(int id_product, String productName, int id_productline, String productVendor, String description, double price){
        this.id_product = id_product;
        this.productName = productName;
        this.id_productline = id_productline;
        this.productVendor = productVendor;
        this.description = description;
        this.price = price;
    }

    @Override
    public  String toString(){
        return "Product{" +
                "id_product =" + id_product +
                ", productName='"+ productName + '\'' +
                "id_productline = " + id_productline +
                "productVendor = " + productVendor +
                "description = " + description +
                "price = " + price +
                '}';
    }

    public static void optionProduct(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu Product ===");
            System.out.println("1. Add Product");
            System.out.println("2. View All Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Insert product Name : ");
                    String productName = scanner.next();
                    System.out.print("Insert id_productline : ");
                    int id_productline = scanner.nextInt();
                    System.out.print("Insert product Vendor : ");
                    String productVendor = scanner.next();
                    System.out.print("Insert description : ");
                    String description = scanner.next();
                    System.out.print("Insert Price : Rp. ");
                    double price = scanner.nextDouble();

                    Product.addProduct(productName,id_productline, productVendor,description,price  );
                    System.out.println("Product added successfully");
                    break;

                case 2:
                    // View All Items
                    List<Product> allProduct = Product.getAllProduct();
                    System.out.println("All Product: " + allProduct);
                    break;
//
                case 3:
                    // Update Item
                    System.out.print("Insert Product ID to update: ");
                    int updateid = scanner.nextInt();
                    System.out.print("Insert product Name : ");
                    String updateproductName = scanner.next();
                    System.out.print("Insert id_productline : ");
                    int updateid_productline = scanner.nextInt();
                    System.out.print("Insert product Vendor : ");
                    String updateproductVendor = scanner.next();
                    System.out.print("Insert description : ");
                    String updatedescription = scanner.next();
                    System.out.print("Insert Price : Rp. ");
                    double updateprice = scanner.nextDouble();

                    Product.updateProduct(updateid,  updateproductName, updateid_productline,updateproductVendor,updatedescription,updateprice   );
                    System.out.println("Product updated successfully!");
                    break;
//
                case 4:
                    // Delete Item
                    System.out.print("Insert Product ID to delete: ");
                    int deleteId = scanner.nextInt();

                    Product.deleteProduct(deleteId);
                    System.out.println("Product deleted successfully!");
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    public static List<Product> getAllProduct(){
        List<Product> products = new ArrayList<>();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product");
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                int id_product = resultSet.getInt("id_product");
                String productName = resultSet.getString("productName");
                int id_productline = resultSet.getInt("id_productline");
                String productVendor = resultSet.getString("productVendor");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                products.add(new Product(id_product, productName, id_productline,productVendor,description,price));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return products;

    }

    public static void addProduct(String productName, int id_productline, String productVendor, String description, double price){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO product (productName, id_productline, productVendor,description,price ) VALUES (?,?,?,?,?)")){
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, id_productline);
            preparedStatement.setString(3, productVendor);
            preparedStatement.setString(4, description);
            preparedStatement.setDouble(5, price);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void updateProduct(int id_product,String productName, int id_productline, String productVendor, String description, double price){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE product SET productName= ?, id_productline = ?,productVendor= ?, description = ?, price = ?  WHERE id_product = ?")){
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, id_productline);
            preparedStatement.setString(3, productVendor);
            preparedStatement.setString(4, description);
            preparedStatement.setDouble(5, price);
            preparedStatement.setInt(6, id_product);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void deleteProduct(int id_product){
        try(Connection connection = conn.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM product WHERE id_product = ?")){
            preparedStatement.setInt(1, id_product);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
