package irfan.fadillah.database;
import irfan.fadillah.database.conn;

import java.security.PublicKey;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class offices {
    private int id_offices;
    private String city;
    private String phone;
    private String address;

    private String country;

    public offices(int id_offices, String city, String phone, String address, String country){
        this.id_offices = id_offices;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.country = country;
    }

    @Override
    public  String toString(){
        return "Offices{" +
                "id_offices=" + id_offices +
                ", city='"+ city + '\'' +
                "phone=" + phone +
                "address=" + address +
                "country=" + country +
                '}';
    }

    public static void optionOffices(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu Offices ===");
            System.out.println("1. Add Offices");
            System.out.println("2. View All Offices");
            System.out.println("3. Update Offices");
            System.out.println("4. Delete Offices");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Insert City : ");
                    String city = scanner.next();
                    System.out.print("Insert phone : ");
                    String phone = scanner.next();
                    System.out.print("Insert address : ");
                    String address = scanner.next();
                    System.out.print("Insert country : ");
                    String country = scanner.next();

                    offices.addOffices(city,phone,address,country);
                    System.out.print("Offices added successfully");
                    break;

                case 2:
                    // View All Items
                    List<offices> allOffices = offices.getAllOffices();
                    System.out.println("All Office: " + allOffices);
                    break;
//
                case 3:
                    // Update Item
                    System.out.print("Insert offices ID to update: ");
                    int updateid = scanner.nextInt();
                    System.out.print("Insert City : ");
                    String updatecity = scanner.next();
                    System.out.print("Insert phone : ");
                    String updatephone = scanner.next();
                    System.out.print("Insert address : ");
                    String updateaddress = scanner.next();
                    System.out.print("Insert country : ");
                    String updatecountry = scanner.next();

                    offices.updateOffices(updateid,  updatecity, updatephone, updateaddress, updatecountry);
                    System.out.println("Offices updated successfully!");
                    break;
//
                case 4:
                    // Delete Item
                    System.out.print("Insert offices ID to delete: ");
                    int deleteId = scanner.nextInt();

                    offices.deleteOffices(deleteId);
                    System.out.println("Offices deleted successfully!");
                    break;

                case 5:
                    return;



                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    public static List<offices> getAllOffices(){
        List<offices> officess = new ArrayList<>();
        try (Connection connection = conn.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM offices");
        ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                int id_offices = resultSet.getInt("id_offices");
                String city = resultSet.getString("city");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String country = resultSet.getString("country");
                officess.add(new offices(id_offices, city, phone, address, country));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return officess;

    }

    public static void addOffices(String city, String phone, String address, String country){
        try (Connection connection = conn.getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(
                  "INSERT INTO offices (city, phone, address, country) VALUES (?,?,?,?)")){
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, country);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void updateOffices(int id_offices,String city, String phone, String address, String country){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE offices SET city= ?, phone = ?, address = ?, country = ? WHERE id_offices = ?")){
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, country);
            preparedStatement.setInt(5, id_offices);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        };
    }

    public static void deleteOffices(int id_offices){
        try(Connection connection = conn.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM offices WHERE id_offices = ?")){
            preparedStatement.setInt(1, id_offices);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }








}
