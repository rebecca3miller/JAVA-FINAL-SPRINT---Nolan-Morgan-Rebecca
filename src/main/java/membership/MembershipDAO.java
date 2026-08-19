package membership;

import logger.CustomLogger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAO {

    private Connection connection;

    public MembershipDAO(Connection connection) {
        this.connection = connection;
    }

    public void addMembership(Membership membership) throws IOException {

        String sql = "INSERT INTO memberships (user_id, membership_type, price) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, membership.getUserId());
            statement.setString(2, membership.getMembershipType());
            statement.setDouble(3, membership.getPrice());

            statement.executeUpdate();

            System.out.println("Membership purchased successfully.");
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while purchasing membership.", e);
            throw new IOException("Error purchasing membership.", e);
        }
    }

    public double getTotalExpenses(int userId) throws IOException {

        double total = 0;
        String sql = "SELECT SUM(price) FROM memberships WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                total = result.getDouble(1);
            }
            }
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while calculating membership expenses.", e);
            throw new IOException("Error calculating membership expenses.", e);
        }

        return total;
    }

    public List<Membership> getAllMemberships() throws IOException {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT membership_id, user_id, membership_type, price FROM memberships ORDER BY membership_id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                memberships.add(new Membership(
                        result.getInt("membership_id"),
                        result.getInt("user_id"),
                        result.getString("membership_type"),
                        result.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while retrieving memberships.", e);
            throw new IOException("Error retrieving memberships.", e);
        }

        return memberships;
    }

    public double getTotalRevenue() throws IOException {

        double total = 0;
        String sql = "SELECT COALESCE(SUM(price), 0) FROM memberships "
                + "WHERE purchased_at >= date_trunc('year', CURRENT_DATE) "
                + "AND purchased_at < date_trunc('year', CURRENT_DATE) + INTERVAL '1 year'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                total = result.getDouble(1);
            }
            }
        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while calculating annual membership revenue.", e);
            throw new IOException("Error calculating annual membership revenue.", e);
        }

        return total;
    }
}