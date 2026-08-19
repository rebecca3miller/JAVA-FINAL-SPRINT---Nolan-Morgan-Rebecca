import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MembershipDAO {

    private Connection connection;

    public MembershipDAO(Connection connection) {
        this.connection = connection;
    }

    public void addMembership(Membership membership) {

        String sql = "INSERT INTO memberships (user_id, membership_type, price) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, membership.getUserId());
            statement.setString(2, membership.getMembershipType());
            statement.setDouble(3, membership.getPrice());

            statement.executeUpdate();

            System.out.println("Membership purchased successfully.");
        } catch (SQLException e) {
            System.out.println("Error purchasing membership.");
            System.out.println(e.getMessage());
        }
    }

    public double getTotalExpenses(int userId) {

        double total = 0;
        String sql = "SELECT SUM(price) FROM memberships WHERE user_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                total = result.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error calculating membership expenses.");
            System.out.println(e.getMessage());
        }

        return total;
    }

    public double getTotalRevenue() {

        double total = 0;
        String sql = "SELECT SUM(price) FROM memberships";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                total = result.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error calculating total revenue.");
            System.out.println(e.getMessage());
        }

        return total;
    }
}