
package merchandise;

import logger.CustomLogger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MerchandiseDAO {

    private final Connection connection;

    public MerchandiseDAO(Connection connection) {
        this.connection = connection;
    }

    public void addMerchandise(Merchandise merchandise) throws IOException {
        String sql = "INSERT INTO merchandise (name, description, item_type, price, stock) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, merchandise.getName());
            statement.setString(2, merchandise.getDescription());
            statement.setString(3, merchandise.getItemType());
            statement.setDouble(4, merchandise.getPrice());
            statement.setInt(5, merchandise.getStock());

            statement.executeUpdate();
            System.out.println("Merchandise added successfully.");
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while adding merchandise.", e);
            throw new IOException("Error adding merchandise.", e);
        }
    }

    public List<Merchandise> getAllMerchandise() throws IOException {
        List<Merchandise> merchandiseList = new ArrayList<>();
        String sql = "SELECT * FROM merchandise";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Merchandise merchandise = new Merchandise(
                    result.getInt("merchandise_id"),
                    result.getString("name"),
                    result.getString("description"),
                    result.getString("item_type"),
                    result.getDouble("price"),
                    result.getInt("stock")
                );

                merchandiseList.add(merchandise);
            }
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while retrieving merchandise.", e);
            throw new IOException("Error retrieving merchandise.", e);
        }

        return merchandiseList;
    }

    public void updateStock(int merchandiseId, int newStock) throws IOException {
        if (newStock < 0) {
            throw new IOException("Stock cannot be negative.");
        }
        String sql = "UPDATE merchandise SET stock = ? WHERE merchandise_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newStock);
            statement.setInt(2, merchandiseId);
            statement.executeUpdate();
            System.out.println("Stock updated successfully.");
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while updating merchandise stock.", e);
            throw new IOException("Error updating merchandise stock.", e);
        }
    }

    public double getInventoryValue() throws IOException {
        double total = 0;
        String sql = "SELECT COALESCE(SUM(price * stock), 0) FROM merchandise";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                total = result.getDouble(1);
            }
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while retrieving inventory value.", e);
            throw new IOException("Error retrieving inventory value.", e);
        }

        return total;
    }

    public void purchaseMerchandise(int merchandiseId, int quantity) throws IOException {
        if (quantity <= 0) {
            throw new IOException("Purchase quantity must be greater than zero.");
        }
        String sql = "SELECT stock, price FROM merchandise WHERE merchandise_id = ?";

        try (PreparedStatement checkStatement = connection.prepareStatement(sql)) {
            checkStatement.setInt(1, merchandiseId);
            try (ResultSet result = checkStatement.executeQuery()) {

            if (result.next()) {
                int stock = result.getInt("stock");
                double price = result.getDouble("price");

                if (stock < quantity) {
                    System.out.println("Not enough stock available.");
                    return;
                }

                double total = price * quantity;
                String updateSql = "UPDATE merchandise SET stock = stock - ? WHERE merchandise_id = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setInt(1, quantity);
                    updateStatement.setInt(2, merchandiseId);
                    updateStatement.executeUpdate();
                }

                System.out.println("Purchase successful.");
                System.out.println("Total cost: $" + total);
            } else {
                System.out.println("Merchandise not found.");
            }
            }
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while purchasing merchandise.", e);
            throw new IOException("Error purchasing merchandise.", e);
        }
    }
}
