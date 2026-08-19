
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

    public void addMerchandise(Merchandise merchandise) {
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
            System.out.println("Error adding merchandise: ");
            System.out.println(e.getMessage());
        }
    }

    public List<Merchandise> getAllMerchandise() {
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
            System.out.println("Error retrieving merchandise: ");
            System.out.println(e.getMessage());
        }

        return merchandiseList;
    }

    public void updateStock(int merchandiseId, int newStock) {
        String sql = "UPDATE merchandise SET stock = ? WHERE merchandise_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newStock);
            statement.setInt(2, merchandiseId);
            statement.executeUpdate();
            System.out.println("Stock updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating stock: ");
            System.out.println(e.getMessage());
        }
    }

    public double getInventoryValue() {
        double total = 0;
        String sql = "SELECT COALESCE(SUM(price * stock), 0) FROM merchandise";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                total = result.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving inventory value: ");
            System.out.println(e.getMessage());
        }

        return total;
    }

    public void purchaseMerchandise(int merchandiseId, int quantity) {
        String sql = "SELECT stock, price FROM merchandise WHERE merchandise_id = ?";

        try (PreparedStatement checkStatement = connection.prepareStatement(sql)) {
            checkStatement.setInt(1, merchandiseId);
            ResultSet result = checkStatement.executeQuery();

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
        } catch (SQLException e) {
            System.out.println("Error purchasing merchandise: ");
            System.out.println(e.getMessage());
        }
    }
}
