package irfan.fadillah.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee {
    private  int  id_employees;
    private String username;
    private String email;
    private int id_offices;

    private String jobtitles;

    public Employee(int id_employees, String username, String email, int id_offices, String jobtitles){


        this.id_employees = id_employees;
        this.username = username;
        this.email = email;
        this.id_offices = id_offices;
        this.jobtitles = jobtitles;
    }

    @Override
    public  String toString(){
        return "Employee{" +
                "id_employees = " + id_employees +
                "username = "+ username  +
                "email=" + email +
                "id_offices = " + id_offices +
                "jobtitles=" + jobtitles +
                '}';
    }

    public static void optionEmployees(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu Employees ===");
            System.out.println("1. Add Employees");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employees");
            System.out.println("4. Delete Employees");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Insert username : ");
                    String username = scanner.next();
                    System.out.print("Insert email : ");
                    String email = scanner.next();
                    System.out.print("Insert id_offices : ");
                    int id_offices = scanner.nextInt();
                    System.out.print("Insert jobtitles : ");
                    String jobtitles = scanner.next();

                    Employee.addEmployee(username,email,id_offices,jobtitles);
                    System.out.print("Employees added successfully");
                    break;

                case 2:
                    // View All Items
                    List<Employee> allEmployees = Employee.getAllEmployees();
                    System.out.println("All Employees: " + allEmployees);
                    break;
//
                case 3:
                    // Update Item
                    System.out.print("Insert employees ID to update: ");
                    int updateid = scanner.nextInt();
                    System.out.print("Insert username : ");
                    String updateusername = scanner.next();
                    System.out.print("Insert email : ");
                    String updateemail = scanner.next();
                    System.out.print("Insert id_offices : ");
                    int updateid_offices = scanner.nextInt();
                    System.out.print("Insert jobtitles : ");
                    String updatejobtitles = scanner.next();

                    Employee.updateEmployees(updateid,  updateusername, updateemail, updateid_offices, updatejobtitles);
                    System.out.println("Employees updated successfully!");
                    break;
//
                case 4:
                    // Delete Item
                    System.out.print("Insert Employees ID to delete: ");
                    int deleteId = scanner.nextInt();

                    Employee.deleteEmployees(deleteId);
                    System.out.println("Employees deleted successfully!");
                    break;

                case 5:
                    return;



                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    public static List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees");
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                int id_employees = resultSet.getInt("id_employees");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                int id_offices = resultSet.getInt("id_offices");
                String job_titles = resultSet.getString("job_title");
                employees.add(new Employee(id_employees, username, email, id_offices, job_titles));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return employees;

    }
//    int id_employees, String username, String email, int id_offices, String jobtitles
    public static void addEmployee(String username, String email, int id_offices, String jobtitles){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO employees (username, email, id_offices, job_title) VALUES (?,?,?,?)")){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, id_offices);
            preparedStatement.setString(4, jobtitles);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void updateEmployees(int id_employees, String username, String email, int id_offices, String jobtitles){
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE employees SET username= ?, email = ?, id_offices = ?, job_title = ? WHERE id_employees = ?")){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, id_offices);
            preparedStatement.setString(4, jobtitles);
            preparedStatement.setInt(5, id_employees);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void deleteEmployees(int id_employees){
        try(Connection connection = conn.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM employees WHERE id_employees = ?")){
            preparedStatement.setInt(1, id_employees);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
