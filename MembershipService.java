public class MembershipService {

    private MembershipDAO membershipDAO;

    public MembershipService(MembershipDAO membershipDAO) {
        this.membershipDAO = membershipDAO;
    }

    public void purchaseMembership(Membership membership) {
        membershipDAO.addMembership(membership);
    }

    public void displayUserExpenses(int userId) {

        double totalExpenses = membershipDAO.getTotalExpenses(userId);

        System.out.println("Total expenses for user " + userId + ": $" + totalExpenses);
    }

    public void displayTotalRevenue() {

        double totalRevenue = membershipDAO.getTotalRevenue();

        System.out.println("Total revenue from memberships: $" + totalRevenue);
    }
}