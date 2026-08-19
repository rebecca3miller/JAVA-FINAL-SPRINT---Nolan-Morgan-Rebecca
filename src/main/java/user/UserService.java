package user;

import logger.CustomLogger;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

/**
 * Provides user registration, authentication, and account management services.
 */
public class UserService {
    /** Data access object used for user database operations. */
    private final UserDAO userDAO;

    /**
     * Constructs a user service with a user data access object.
     *
     * @throws IOException if the user data access object cannot be created
     */
    public UserService() throws IOException {
        this.userDAO = new UserDAO();
    }

    /**
     * Authenticates a user with a username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the authenticated user
     * @throws IOException if the credentials are invalid or authentication fails
     */
    public User authenticateUser(String username, String password) throws IOException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        CustomLogger.logInfo("Failed login attempt for username: " + username);
        throw new IOException("Invalid username or password.");
    }

    /**
     * Creates a new user after validating and encrypting the registration details.
     *
     * @param user the user to create
     * @throws IOException if the registration details are invalid or the user cannot be created
     */
    public void createUser(User user) throws IOException {
        try {
            validateRegistration(user);
            if (userDAO.getUserByUsername(user.getUsername()) != null) {
                throw new IOException("That username is already in use.");
            }
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userDAO.createUser(user);
            CustomLogger.logInfo("User registered: " + user.getUsername());
            System.out.println("Registration successful.");
        } catch (Exception e) {
            CustomLogger.logError("Error creating a user.", e);
            throw new IOException("Error creating user.", e);
        }
    }

    /**
     * Retrieves all users for an administrator.
     *
     * @param requestingUser the signed-in administrator making the request
     * @return a list containing all users
     * @throws IOException if the requester is unauthorized or the users cannot be retrieved
     */
    public List<User> getAllUsers(User requestingUser) throws IOException {
        try {
            requireAdmin(requestingUser, "view all users");
            List<User> users = userDAO.getAllUsers();
            users.forEach(user -> user.setPassword(null));
            return users;
        } catch (Exception e) {
            CustomLogger.logError("Error retrieving users.", e);
            throw new IOException("Error retrieving users.", e);
        }
    }

    /**
     * Deletes a user's account when requested by an authorized user.
     *
     * @param requestingUser the signed-in user making the request
     * @param id the ID of the user to delete
     * @throws IOException if the request is unauthorized or the user cannot be deleted
     */
    public void deleteUser(User requestingUser, int id) throws IOException {
        try {
            requireAdmin(requestingUser, "delete users");
            userDAO.deleteUser(id);
            CustomLogger.logInfo("Admin override: deleted user ID " + id);
            System.out.println("User deleted successfully.");
        } catch (Exception e) {
            CustomLogger.logError("Error deleting a user.", e);
            throw new IOException("Error deleting user.", e);
        }
    }

    /**
     * Ensures that the requesting user has administrator privileges.
     *
     * @param requestingUser the signed-in user making the request
     * @param action the action requiring administrator privileges
     * @throws IOException if the requester is not an administrator
     */
    private void requireAdmin(User requestingUser, String action) throws IOException {
        Authorization.requireRole(requestingUser, action, "Admin");
    }

    /**
     * Validates the details supplied for user registration.
     *
     * @param user the user details to validate
     * @throws IOException if any registration detail is invalid
     */
    private void validateRegistration(User user) throws IOException {
        if (user == null) {
            throw new IOException("User registration details are required.");
        }
        if (isBlank(user.getPassword())) {
            throw new IOException("A password is required.");
        }
        validateProfile(user);
    }

    /**
     * Validates the field values required for console registration.
     *
     * @param user the user details to validate
     * @throws IOException if any profile detail is invalid
     */
    private void validateProfile(User user) throws IOException {
        if (isBlank(user.getUsername()) || isBlank(user.getEmail())
                || isBlank(user.getPhoneNumber()) || isBlank(user.getAddress())) {
            throw new IOException("Username, email, phone number, and address are required.");
        }
        if (!user.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IOException("Enter a valid email address.");
        }
        if (!user.getPhoneNumber().matches("^[0-9()+. -]{7,20}$")) {
            throw new IOException("Enter a valid phone number.");
        }
    }

    /**
     * Checks whether a string is null, empty, or contains only whitespace.
     *
     * @param value the string to check
     * @return true if the string is blank; otherwise false
     */
    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}