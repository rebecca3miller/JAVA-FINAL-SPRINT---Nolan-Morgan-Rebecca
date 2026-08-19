package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Shared RBAC lookups for menus and service methods. */
public final class Authorization {

    private static final Map<String, List<String>> ROLE_PERMISSIONS = Map.of(
            "Admin", List.of(
                    "view_users",
                    "delete_users",
                    "view_revenue",
                    "add_merchandise",
                    "restock_merchandise",
                    "view_inventory",
                    "manage_classes",
                    "purchase_membership",
                    "browse_merchandise",
                    "export_reports"
            ),
            "Trainer", List.of(
                    "manage_classes",
                    "view_own_classes",
                    "purchase_membership",
                    "browse_merchandise"
            ),
            "Member", List.of(
                    "browse_classes",
                    "view_own_expenses",
                    "purchase_membership",
                    "browse_merchandise"
            )
    );

        private static final Map<String, String> ACTION_LABELS = Map.ofEntries(
            Map.entry("view_users", "View all users & contact info"),
            Map.entry("delete_users", "Delete users from the system"),
            Map.entry("view_revenue", "Track total annual membership revenue"),
            Map.entry("add_merchandise", "Add new merchandise items & set prices"),
            Map.entry("restock_merchandise", "Restock merchandise inventory"),
            Map.entry("view_inventory", "View merchandise stock & total valuation"),
            Map.entry("manage_classes", "Create, update, and delete workout classes"),
            Map.entry("view_own_classes", "View list of self-assigned classes"),
            Map.entry("purchase_membership", "Purchase a gym membership"),
            Map.entry("browse_merchandise", "View merchandise available for purchase"),
            Map.entry("browse_classes", "Browse all available workout classes"),
            Map.entry("view_own_expenses", "View personal total membership expenses"),
            Map.entry("export_reports", "Export Reports to File")
        );

    private Authorization() {
    }

    public static List<String> getMenuActions(String role) {
        return new ArrayList<>(ROLE_PERMISSIONS.getOrDefault(role, List.of()));
    }

    public static String getActionLabel(String action) {
        return ACTION_LABELS.getOrDefault(action, action);
    }

    public static boolean hasPermission(User user, String action) {
        return user != null && ROLE_PERMISSIONS.getOrDefault(user.getRole(), List.of()).contains(action);
    }

    public static void requireRole(User user, String action, String... allowedRoles) throws IOException {
        if (user == null) {
            throw new IOException("A signed-in user is required to " + action + ".");
        }
        for (String allowedRole : allowedRoles) {
            if (allowedRole.equals(user.getRole())) {
                return;
            }
        }
        throw new IOException("The user's role is not allowed to " + action + ".");
    }
}
