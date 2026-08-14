package user;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides user registration, authentication, and account management services.
 */
public class UserService {

    /** Logger used to record user service events. */
    private static final Logger LOGGER = UserLogger.getLogger();
    /** Roles allowed during user registration. */
    private static final Set<String> ALLOWED_ROLES = Set.of("Admin", "Trainer", "Member");
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
        if (user != null && BCrypt.checkPassword(password, user.getPassword())) {
            return user;
        }
        LOGGER.warning("Failed login attempt for username: " + username);
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
            user.setPassword(BCrypt.hashPassword(user.getPassword()));
            userDAO.createUser(user);
            System.out.println("User created successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating a user.", e);
            throw new IOException("Error creating user.", e);
        }
    }

    /**
     * Changes a user's password when requested by an authorized user.
     *
     * @param requestingUser the signed-in user making the request
     * @param userId the ID of the user whose password will change
     * @param newPassword the new password
     * @throws IOException if the request is unauthorized or the password cannot be changed
     */
    public void changePassword(User requestingUser, int userId, String newPassword) throws IOException {
        try {
            if (requestingUser == null || newPassword == null || newPassword.isBlank()) {
                throw new IOException("A signed-in user and a nonblank password are required.");
            }
            boolean isAdmin = "Admin".equals(requestingUser.getRole());
            if (!isAdmin && requestingUser.getId() != userId) {
                throw new IOException("Users can only change their own password.");
            }

            User userToUpdate = userDAO.getUserById(userId);
            if (userToUpdate == null) {
                throw new IOException("User not found.");
            }
            userToUpdate.setPassword(BCrypt.hashPassword(newPassword));
            userDAO.updateUser(userToUpdate);
            if (isAdmin && requestingUser.getId() != userId) {
                LOGGER.info("Admin override: changed password for user ID " + userId);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error changing a password.", e);
            throw new IOException("Error changing password.", e);
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
            return userDAO.getAllUsers();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving users.", e);
            throw new IOException("Error retrieving users.", e);
        }
    }

    /**
     * Retrieves a user by username.
     *
     * @param username the username to search for
     * @return the matching user, or null if no user is found
     * @throws IOException if the user cannot be retrieved
     */
    public User getUserByUsername(String username) throws IOException {
        try {
            return userDAO.getUserByUsername(username);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving a user by username.", e);
            throw new IOException("Error retrieving user by username.", e);
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
        try {
            return userDAO.getUserById(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving a user by ID.", e);
            throw new IOException("Error retrieving user by ID.", e);
        }
    }

    /**
     * Updates a user's account when requested by an authorized user.
     *
     * @param requestingUser the signed-in user making the request
     * @param userToUpdate the user containing the updated details
     * @throws IOException if the request is unauthorized or the user cannot be updated
     */
    public void updateUser(User requestingUser, User userToUpdate) throws IOException {
        try {
            if (requestingUser == null || userToUpdate == null) {
                throw new IOException("A signed-in user and an updated user are required.");
            }
            boolean isAdmin = "Admin".equals(requestingUser.getRole());
            if (!isAdmin && requestingUser.getId() != userToUpdate.getId()) {
                throw new IOException("Users can only update their own account.");
            }
            if (!isAdmin) {
                userToUpdate.setRole(requestingUser.getRole());
            } else if (requestingUser.getId() != userToUpdate.getId()) {
                LOGGER.info("Admin override: updated user ID " + userToUpdate.getId());
            }
            userDAO.updateUser(userToUpdate);
            System.out.println("User updated successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating a user.", e);
            throw new IOException("Error updating user.", e);
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
            if (requestingUser == null) {
                throw new IOException("A signed-in user is required to delete an account.");
            }
            boolean isAdmin = "Admin".equals(requestingUser.getRole());
            if (!isAdmin && requestingUser.getId() != id) {
                throw new IOException("Users can only delete their own account.");
            }
            if (isAdmin && requestingUser.getId() != id) {
                LOGGER.info("Admin override: deleted user ID " + id);
            }
            userDAO.deleteUser(id);
            System.out.println("User deleted successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting a user.", e);
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
        if (requestingUser == null || !"Admin".equals(requestingUser.getRole())) {
            throw new IOException("Only Admin users can " + action + ".");
        }
        LOGGER.info("Admin override: viewed all users.");
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
        if (isBlank(user.getUsername()) || isBlank(user.getPassword()) || isBlank(user.getEmail())
                || isBlank(user.getPhoneNumber()) || isBlank(user.getAddress())) {
            throw new IOException("Username, password, email, phone number, and address are required.");
        }
        if (!user.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IOException("Enter a valid email address.");
        }
        if (!user.getPhoneNumber().matches("^[0-9()+. -]{7,20}$")) {
            throw new IOException("Enter a valid phone number.");
        }
        if (!ALLOWED_ROLES.contains(user.getRole())) {
            throw new IOException("Role must be Admin, Trainer, or Member.");
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