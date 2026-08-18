package user;

/**
 * The Trainer class represents a trainer user in the system.
 * It extends the User class and sets the role to "Trainer".
 */
public class Trainer extends User {

    /**
     * Constructs a user with the role "Trainer" with the specified registration details.
     *
     * @param username the username of the trainer
     * @param password the password of the trainer
     * @param email the email address of the trainer
     * @param phoneNumber the phone number of the trainer
     * @param address the address of the trainer
     */
    public Trainer(String username, String password, String email, String phoneNumber, String address) {
        super(0, username, password, email, phoneNumber, address, "Trainer");
    }

    /**
     * Default constructor for Trainer class.
     */
    public Trainer() {
        super(0, "", "", "", "", "", "Trainer");
    }
}