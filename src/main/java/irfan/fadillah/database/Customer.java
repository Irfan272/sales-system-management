package irfan.fadillah.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer {
    private  int  id_customers;
    private String customers_name;
    private String phone;
    private String address;
    private String city;
    private int id_employess;

    public Customer(int id_customers, String customers_name, String phone, String address, String city, int id_employess ){
        this.id_customers = id_customers;
        this.customers_name = customers_name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.id_employess = id_employess;
    }

    @Override
    public  String toString(){
        return "Customers{" +
                "id_customers = " + id_customers +
                "customers_name = "+ customers_name  +
                "phone=" + phone +
                "address = " + address +
                "city=" + city +
                "id_employess=" + id_employess +
                '}';
    }

    public static void optionCustomers(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu Customers ===");
            System.out.println("1. Add Customers");
            System.out.println("2. View All Customers");
            System.out.println("3. Update Customers");
            System.out.println("4. Delete Customers");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Insert customers name : ");
                    String customers_name = scanner.next();
                    System.out.print("Insert phone : ");
                    String phone = scanner.next();
                    System.out.print("Insert address : ");
                    String address = scanner.next();
                    System.out.print("Insert city : ");
                    String city = scanner.next();
                    System.out.print("Insert id employess : ");
                    int id_employess = scanner.nextInt();

                    Customer.addCustomer(customers_name,phone,address,city,id_employess);
                    System.out.print("Customers added successfully");
                    break;

                case 2:
                    // View All Items
                    List<Customer> allCustomers = Customer.getAllCustomers();
                    System.out.println("All Customers: " + allCustomers);
                    break;
//
                case 3:
                    // Update Item
                    System.out.print("Insert customers ID to update: ");
                    int updateid = scanner.nextInt();
                    System.out.print("Insert customers name : ");
                    String updatecustomers_name = scanner.next();
                    System.out.print("Insert phone : ");
                    String updatephone = scanner.next();
                    System.out.print("Insert address : ");
                    String updateaddress = scanner.next();
                    System.out.print("Insert city : ");
                    String updatecity = scanner.next();
                    System.out.print("Insert id employess : ");
                    int updateid_employess = scanner.nextInt();

                    Customer.updateCustomers(updateid, updatecustomers_name ,updatephone, updateaddress, updatecity, updateid_employess);
                    System.out.println("Customers updated successfully!");
                    break;
//
                case 4:
                    // Delete Item
                    System.out.print("Insert Customers ID to delete: ");
                    int deleteId = scanner.nextInt();

                    Customer.deleteCustomers(deleteId);
                    System.out.println("Customers deleted successfully!");
                    break;

                case 5:
                    return;



                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    public static List<Customer> getAllCustomers(){
        List<Customer> customer = new ArrayList<>();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customers");
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                int id_customers = resultSet.getInt("id_customers");
                String customers_name = resultSet.getString("customer_name");
                String phone = resultSet.getString("phone");
                String  address = resultSet.getString("address");
                String city = resultSet.getString("city");
                int id_employess = resultSet.getInt("id_employess");
                customer.add(new Customer(id_customers, customers_name, phone, address, city, id_employess));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return customer;

    }

    public static void addCustomer(String customers_name, String phone, String address, String city, int id_employess ){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO customers (customer_name, phone, address,city, id_employess ) VALUES (?,?,?,?,?)")){
            preparedStatement.setString(1, customers_name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, city);
            preparedStatement.setInt(5, id_employess);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void updateCustomers(int id_customers,String customers_name, String phone, String address, String city, int id_employess){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE customers SET customer_name= ?, phone = ?, address = ?,city = ?, id_employess = ? WHERE id_customers = ?")){
            preparedStatement.setString(1, customers_name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, city);
            preparedStatement.setInt(5, id_employess);
            preparedStatement.setInt(6, id_customers);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void deleteCustomers(int id_customers){
        try(Connection connection = conn.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM customers WHERE id_customers = ?")){
            preparedStatement.setInt(1, id_customers);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
