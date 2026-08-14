public class Membership {
    private int membershipId;
    private int userId;
    private String membershipType;
    private double price;

    public Membership() {

    }

    public Membership(int membershipId, int userId, String membershipType, double price) {
        this.membershipId = membershipId;
        this.userId = userId;
        this.membershipType = membershipType;
        this.price = price;
    }

    
}
