package irfan.fadillah.database;

import java.util.List;
import java.util.Scanner;
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Sales Management System ===");
            System.out.println("1. Offices");
            System.out.println("2. Employee");
            System.out.println("3. Customer");
            System.out.println("4. Product Line");
            System.out.println("5. Product");
            System.out.println("6. Order");
            System.out.println("7. Payment");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    offices.optionOffices();

                    break;

                case 2:
                   Employee.optionEmployees();
                    break;
//
                case 3:
                   Customer.optionCustomers();
                    break;
//
                case 4:
                   ProductLine.optionProductLine();
                    break;
                case 5:
                    Product.optionProduct();
                    break;
                case 6:
                    Order.optionOrder();
                    break;
//
//                case 5:
//                    // Exit
//                    System.out.println("Exiting the Inventory Management System. Goodbye!");
//                    System.exit(0);



                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }
}
