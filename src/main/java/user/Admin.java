package user;

/**
 * Represents an administrator user in the gym management system.
 */
public class Admin extends User {

    /**
     * Constructs a user with the role "Admin" with the specified registration details.
     *
     * @param username the username of the administrator
     * @param password the password of the administrator
     * @param email the email address of the administrator
     * @param phoneNumber the phone number of the administrator
     * @param address the address of the administrator
     */
    public Admin(String username, String password, String email, String phoneNumber, String address) {
        super(0, username, password, email, phoneNumber, address, "Admin");
    }

    /**
     * Default constructor for Admin class.
     */
    public Admin() {
        super(0, "", "", "", "", "", "Admin");
    }
}