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

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public int getMembershipId() {
        return this.membershipId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipType() {
        return this.membershipType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }   

    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", userId=" + userId +
                ", membershipType='" + membershipType + '\'' +
                ", price=" + price +
                '}';
    }
}
