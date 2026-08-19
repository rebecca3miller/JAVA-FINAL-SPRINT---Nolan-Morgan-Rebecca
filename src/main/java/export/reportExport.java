package export;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import logger.CustomLogger;
import user.Authorization;
import user.User;

public class reportExport {

    public static final String MERCHANDISE_REPORT_FILE = "merchandise_report.txt";
    public static final String MEMBERSHIP_REPORT_FILE = "membership_report.txt";
    private static final Path REPORTS_DIR = Paths.get("reports");

    private final Connection connection;

    public reportExport(Connection connection) {
        this.connection = connection;
    }

    public void getMerchandiseReport(User requestingUser) {
        try {
            Authorization.requireRole(requestingUser, "Export merchandise report", "Admin");
        } catch (IOException e) {
            System.err.println("Access denied. Admin role required.");
            return;
        }
        String sql = "SELECT * FROM merchandise";
        Path reportFile;
        try {
            Files.createDirectories(REPORTS_DIR);
            reportFile = REPORTS_DIR.resolve(MERCHANDISE_REPORT_FILE);
        } catch (IOException e) {
            CustomLogger.logError("Failed to create /reports/ directory.", e);
            System.err.println("Error creating reports directory: " + e.getMessage());
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile.toFile()));
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String itemName = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                writer.write(String.format("Item: %s, Price: %.2f, Stock: %d%n", itemName, price, stock));
            }

            System.out.println("Merchandise report exported successfully to " + reportFile);
            CustomLogger.logInfo("Admin override: " + requestingUser.getUsername() + " exported the merchandise report.");
        } catch (IOException | SQLException e) {
            CustomLogger.logError("Failed to export merchandise report.", e);
            System.err.println("Error exporting merchandise report: " + e.getMessage());
        }
    }

    public void getMembershipReport(User requestingUser) {
        try {
            Authorization.requireRole(requestingUser, "Export membership report", "Admin");
        } catch (IOException e) {
            System.err.println("Access denied. Admin role required.");
            return;
        }
        String sql = "SELECT * FROM memberships";
        Path reportFile;
        try {
            Files.createDirectories(REPORTS_DIR);
            reportFile = REPORTS_DIR.resolve(MEMBERSHIP_REPORT_FILE);
        } catch (IOException e) {
            CustomLogger.logError("Failed to create /reports/ directory.", e);
            System.err.println("Error creating reports directory: " + e.getMessage());
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile.toFile()));
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String membershipType = resultSet.getString("membership_type");
                double price = resultSet.getDouble("price");
                writer.write(String.format("User ID: %d, Membership Type: %s, Price: %.2f%n", userId, membershipType, price));
            }

            System.out.println("Membership report exported successfully to " + reportFile);
            CustomLogger.logInfo("Admin override: " + requestingUser.getUsername() + " exported the membership report.");
        } catch (IOException | SQLException e) {
            CustomLogger.logError("Failed to export membership report.", e);
            System.err.println("Error exporting membership report: " + e.getMessage());
        }
    }
}