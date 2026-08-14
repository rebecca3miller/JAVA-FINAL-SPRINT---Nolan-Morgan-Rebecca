package user;

/**
 * The Member class represents a member user in the system.
 * It extends the User class and sets the role to "Member".
 */
public class Member extends User {

    /**
     * Constructs a user with the role "Member" with the specified registration details.
     *
     * @param username the username of the member
     * @param password the password of the member
     * @param email the email address of the member
     * @param phoneNumber the phone number of the member
     * @param address the address of the member
     */
    public Member(String username, String password, String email, String phoneNumber, String address) {
        super(0, username, password, email, phoneNumber, address, "Member");
    }

    /**
     * Default constructor for Member class.
     */
    public Member() {
        super(0, "", "", "", "", "", "Member");
    }
}