package irfan.fadillah.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;


public class Order {
    private int id_order;
    private Date orderDate;
    private Date requiredDate;
    private Date shippedDate;
    private String status;
    private String comments;
    private int id_customers;

    public Order(int id_order, Date orderDate, Date requiredDate, Date shippedDate, String status, String comments,int id_customers){
        this.id_order = id_order;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
        this.id_customers = id_customers;
    }

    @Override
    public  String toString(){
        return "Order{" +
                "id_order =" + id_order +
                ", orderDate='"+ orderDate + '\'' +
                "requiredDate = " + requiredDate +
                "shippedDate = " + shippedDate +
                "status = " + status +
                "comments = " + comments +
                "id_customers = " + id_customers +
                '}';
    }


    public static void optionOrder(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu Order ===");
            System.out.println("1. Add Order");
            System.out.println("2. View All Order");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            switch (choice) {


                case 1:

                    try{
                        System.out.print("Insert orderDate (yyyy-MM-dd): ");
                        String orderDateString = scanner.next();
                        Date orderDate = dateFormat.parse(orderDateString);

                        System.out.print("Insert requiredDate (yyyy-MM-dd): ");
                        String requiredDateString = scanner.next();
                        Date requiredDate = dateFormat.parse(requiredDateString);

                        System.out.print("Insert shippedDate (yyyy-MM-dd): ");
                        String shippedDateDateString = scanner.next();
                        Date shippedDate = dateFormat.parse(shippedDateDateString);


                        String status = "Shipped";

                        System.out.print("Insert comments : ");
                        String comments = scanner.next();

                        System.out.print("Insert id customers : ");
                        int id_customers = scanner.nextInt();

                        Order.addOrder(orderDate,requiredDate,shippedDate,status,comments, id_customers);
                        System.out.print("Order added successfully");

                    }catch (ParseException e){
                        System.out.println("Error parsing date. Make sure to input dates in the correct format (yyyy-MM-dd).");
                        e.printStackTrace();
                    }

                    break;

                case 2:
                    // View All Items
                        List<Order> allOrders = Order.getAllOrder();
                    System.out.println("All Orders: " + allOrders);
                    break;
//
                case 3:
                    // Update Item
                    try{
                        System.out.print("Insert id order : ");
                        int id_order = scanner.nextInt();

                        System.out.print("Insert orderDate (yyyy-MM-dd): ");
                        String orderDateString = scanner.next();
                        Date orderDate = dateFormat.parse(orderDateString);

                        System.out.print("Insert requiredDate (yyyy-MM-dd): ");
                        String requiredDateString = scanner.next();
                        Date requiredDate = dateFormat.parse(requiredDateString);

                        System.out.print("Insert shippedDate (yyyy-MM-dd): ");
                        String shippedDateDateString = scanner.next();
                        Date shippedDate = dateFormat.parse(shippedDateDateString);


                        String status = "Shipped";

                        System.out.print("Insert comments : ");
                        String comments = scanner.next();

                        System.out.print("Insert id customers : ");
                        int id_customers = scanner.nextInt();

                        Order.updateOrder(id_order, orderDate ,requiredDate, shippedDate, status, comments, id_customers);
                        System.out.println("Customers updated successfully!");
                    }catch (ParseException e){
                        System.out.println("Error parsing date. Make sure to input dates in the correct format (yyyy-MM-dd).");
                        e.printStackTrace();
                    }


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

    public static List<Order> getAllOrder(){
        List<Order> orders = new ArrayList<>();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `order`");
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                int id_order = resultSet.getInt("id_order");
                Date orderDate = resultSet.getDate("orderDate");
                Date requiredDate = resultSet.getDate("requiredDate");
                Date shippedDate = resultSet.getDate("shippedDate");
                String status = resultSet.getString("status");
                String comments = resultSet.getString("comments");
                int id_customers = resultSet.getInt("id_customers");
                orders.add(new Order(id_order, orderDate, requiredDate,shippedDate,status,comments,id_customers));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return orders;

    }

    public static void addOrder (Date orderDate, Date requiredDate, Date shippedDate, String status, String comments,int id_customers){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO `order` (orderDate, requiredDate, shippedDate, status, comments, id_customers) VALUES (?,?,?,?,?,?)")) {

            java.sql.Date sqlOrderDate = new java.sql.Date(orderDate.getTime());
            java.sql.Date sqlRequiredDate = new java.sql.Date(requiredDate.getTime());
            java.sql.Date sqlShippedDate = new java.sql.Date(shippedDate.getTime());

            preparedStatement.setDate(1, sqlOrderDate);
            preparedStatement.setDate(2, sqlRequiredDate);
            preparedStatement.setDate(3, sqlShippedDate);

            preparedStatement.setString(4, status);
            preparedStatement.setString(5, comments);
            preparedStatement.setInt(6, id_customers);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void updateOrder(int id_order, Date orderDate, Date requiredDate, Date shippedDate, String status, String comments,int id_customers){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE `order` SET  orderDate = ?,requiredDate= ?, shippedDate = ?, status = ?, comments = ?, id_customers = ?   WHERE id_order = ?")){
            java.sql.Date sqlOrderDate = new java.sql.Date(orderDate.getTime());
            java.sql.Date sqlRequiredDate = new java.sql.Date(requiredDate.getTime());
            java.sql.Date sqlShippedDate = new java.sql.Date(shippedDate.getTime());

            preparedStatement.setDate(1, sqlOrderDate);
            preparedStatement.setDate(2, sqlRequiredDate);
            preparedStatement.setDate(3, sqlShippedDate);

            preparedStatement.setString(4, status);
            preparedStatement.setString(5, comments);
            preparedStatement.setInt(6, id_customers);
            preparedStatement.setInt(7, id_order);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void deleteOrder(int id_order){
        try(Connection connection = conn.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM order WHERE id_order = ?")){
            preparedStatement.setInt(1, id_order);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
