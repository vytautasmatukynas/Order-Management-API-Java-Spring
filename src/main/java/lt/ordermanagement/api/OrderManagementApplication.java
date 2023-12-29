package lt.ordermanagement.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Order Management System.
 *
 * <p>
 * The Order Management System is designed to streamline and manage the process of handling orders.
 * It provides features for creating, updating, and tracking orders for efficient order fulfillment.
 * </p>
 *
 * <p>
 * This class initializes and runs the Spring Boot application, serving as the entry point for the
 * Order Management System.
 * </p>
 *
 * @author Vytautas Matukynas
 * @version 1.0
 * @since 2023-12-31
 *
 * <p>
 * <b>Note:</b> Ensure that the application is configured properly with the required dependencies and settings
 * before running this class.
 * </p>
 */
@SpringBootApplication
public class OrderManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementApplication.class, args);
    }

}
