package membership;

import user.Authorization;
import user.User;

import java.io.IOException;
import java.util.Map;

public class MembershipService {

    private final MembershipDAO membershipDAO;

    public MembershipService(MembershipDAO membershipDAO) {
        this.membershipDAO = membershipDAO;
    }

    public void purchaseMembership(User requestingUser, Membership membership) throws IOException {
        Authorization.requireRole(requestingUser, "purchase a gym membership", "Trainer", "Member");
        if (membership == null || membership.getUserId() != requestingUser.getId()) {
            throw new IOException("A membership purchase must belong to the signed-in user.");
        }
        Double price = getAvailableMembershipPlans().get(membership.getMembershipType());
        if (price == null) {
            throw new IOException("Choose one of the available membership plans.");
        }
        membership.setPrice(price);
        membershipDAO.addMembership(membership);
    }

    public Map<String, Double> getAvailableMembershipPlans() throws IOException {
        return membershipDAO.getMembershipPlans();
    }

    public void displayUserExpenses(User requestingUser, int userId) throws IOException {
        if (requestingUser == null) {
            throw new IOException("A signed-in user is required to view membership expenses.");
        }
        Authorization.requireRole(requestingUser, "view personal total membership expenses", "Member");
        if (requestingUser.getId() != userId) {
            throw new IOException("Members can only view their own membership expenses.");
        }

        double totalExpenses = membershipDAO.getTotalExpenses(userId);
        System.out.println("Total expenses for user " + userId + ": $" + totalExpenses);
    }

    public void displayTotalRevenue(User requestingUser) throws IOException {
        Authorization.requireRole(requestingUser, "track total annual membership revenue", "Admin");
        double totalRevenue = membershipDAO.getTotalRevenue();
        System.out.println("Total revenue from memberships: $" + totalRevenue);
    }
}