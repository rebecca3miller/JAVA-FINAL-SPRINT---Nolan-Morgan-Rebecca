import java.sql.Connection;
import java.sql.PreparedStatement;
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
}