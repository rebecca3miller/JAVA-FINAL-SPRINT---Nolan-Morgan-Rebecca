package user;

import database.DatabaseConnection;
import logger.CustomLogger;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides database access operations for users.
 */
public class UserDAO {

    /** Logger used to record database operation errors. */
    /** Database connection used for user operations. */
    private final Connection connection;

    /**
     * Constructs a user data access object with a database connection.
     *
     * @throws IOException if the database connection cannot be created
     */
    public UserDAO() throws IOException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the user to create
     * @throws IOException if the user cannot be saved
     */
    public void createUser(User user) throws IOException {
        String sql = "INSERT INTO users (username, password, email, phone_number, address, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            CustomLogger.logError("Database transaction error while creating a user.", e);
            throw new IOException("Error saving new user", e);
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list containing all users
     * @throws IOException if the users cannot be retrieved
     */
    public List<User> getAllUsers() throws IOException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("address"),
                        resultSet.getString("role")
                );
                users.add(user);
            }
        } catch (Exception e) {
            CustomLogger.logError("Database transaction error while retrieving users.", e);
            throw new IOException("Error retrieving users", e);
        }
        return users;
    }

    /**
     * Retrieves a user by username.
     *
     * @param username the username to search for
     * @return the matching user, or null if no user is found
     * @throws IOException if the user cannot be retrieved
     */
    public User getUserByUsername(String username) throws IOException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("address"),
                            resultSet.getString("role")
                    );
                } else {
                    return null; // User not found
                }
            }
        } catch (Exception e) {
            CustomLogger.logError("Database transaction error while retrieving a user by username.", e);
            throw new IOException("Error retrieving user by username", e);
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID to search for
     * @return the matching user, or null if no user is found
     * @throws IOException if the user cannot be retrieved
     */
    public User getUserById(int id) throws IOException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("address"),
                            resultSet.getString("role")
                    );
                } else {
                    return null; // User not found
                }
            }
        } catch (Exception e) {
            CustomLogger.logError("Database transaction error while retrieving a user by ID.", e);
            throw new IOException("Error retrieving user by ID", e);
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the user containing the updated details
     * @throws IOException if the user cannot be updated
     */
    public void updateUser(User user) throws IOException {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone_number = ?, address = ?, role = ? WHERE id = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setInt(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            CustomLogger.logError("Database transaction error while updating a user.", e);
            throw new IOException("Error updating user", e);
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param id the ID of the user to delete
     * @throws IOException if the user cannot be deleted
     */
    public void deleteUser(int id) throws IOException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            CustomLogger.logError("Database transaction error while deleting a user.", e);
            throw new IOException("Error deleting user", e);
        }
    }

}