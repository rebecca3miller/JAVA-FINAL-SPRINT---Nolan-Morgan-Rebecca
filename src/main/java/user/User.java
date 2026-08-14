package user;

/**
 * Represents a user in the system.
 */
public class User {

    /** Unique user ID. */
    private int id;
    /** Username of the user. */
    private String username;
    /** Password of the user. */
    private String password;
    /** Email address of the user. */
    private String email;
    /** Phone number of the user. */
    private String phoneNumber;
    /** Home address of the user. */
    private String address;
    /** Role of the user (e.g., Admin, Trainer, Member). */
    private String role;

    /**
     * Constructs a new User with the specified details.
     *
     * @param id       Unique user ID.
     * @param username Username of the user.
     * @param password Password of the user.
    * @param email       Email address of the user.
     * @param phoneNumber Phone number of the user.
     * @param address     Home address of the user.
    * @param role        Role of the user.
     */
    public User(int id, String username, String password, String email, String phoneNumber, String address, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    // Getters and setters

    /**
     * Returns the ID of the user.
     * @return ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * @param id New ID of the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the username of the user.
     * @return Username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username New username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     * @return Password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password New password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email address of the user.
     * @return Email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * @param email New email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the user.
     * @return Phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     * @param phoneNumber New phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the address of the user.
     * @return Address of the user.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     * @param address New address of the user.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the role of the user.
     * @return Role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * @param role New role of the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

}

