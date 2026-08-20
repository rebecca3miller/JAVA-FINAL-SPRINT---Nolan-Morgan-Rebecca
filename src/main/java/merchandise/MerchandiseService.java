
package merchandise;

import user.Authorization;
import user.User;

import java.io.IOException;
import java.util.List;

public class MerchandiseService {

    private final MerchandiseDAO merchandiseDAO;

    public MerchandiseService(MerchandiseDAO merchandiseDAO) {
        this.merchandiseDAO = merchandiseDAO;
    }

    public void addMerchandise(User requestingUser, Merchandise merchandise) throws IOException {
        Authorization.requireRole(requestingUser, "add new merchandise items and set prices", "Admin");
        merchandiseDAO.addMerchandise(merchandise);
    }

    public List<Merchandise> getAllMerchandise(User requestingUser) throws IOException {
        Authorization.requireRole(requestingUser, "view merchandise available for purchase", "Trainer", "Member");
        return merchandiseDAO.getAllMerchandise();
    }

    public void purchaseMerchandise(User requestingUser, int merchandiseId, int quantity) throws IOException {
        Authorization.requireRole(requestingUser, "purchase merchandise", "Trainer", "Member");
        merchandiseDAO.purchaseMerchandise(merchandiseId, quantity);
    }

    public double getInventoryValue(User requestingUser) throws IOException {
        Authorization.requireRole(requestingUser, "view merchandise stock and total valuation", "Admin");
        return merchandiseDAO.getInventoryValue();
    }

    public void displayInventory(User requestingUser) throws IOException {
        Authorization.requireRole(requestingUser, "view merchandise stock and total valuation", "Admin");
        List<Merchandise> merchandiseList = merchandiseDAO.getAllMerchandise();

        System.out.println("Merchandise Inventory:");
        for (Merchandise merchandise : merchandiseList) {
            System.out.printf("ID: %d, Name: %s, Type: %s, Price: $%.2f, Stock: %d%n",
                    merchandise.getMerchandiseId(), merchandise.getName(), merchandise.getItemType(),
                    merchandise.getPrice(), merchandise.getStock());
        }
        System.out.printf("Total inventory valuation: $%.2f%n", merchandiseDAO.getInventoryValue());
    }

    public void updateStock(User requestingUser, int merchandiseId, int newStock) throws IOException {
        Authorization.requireRole(requestingUser, "update merchandise stock", "Admin");
        merchandiseDAO.updateStock(merchandiseId, newStock);
    }

    public void updatePrice(User requestingUser, int merchandiseId, double newPrice) throws IOException {
        Authorization.requireRole(requestingUser, "update merchandise prices", "Admin");
        merchandiseDAO.updatePrice(merchandiseId, newPrice);
    }

    public void browseMerchandise(User requestingUser) throws IOException {
        Authorization.requireRole(requestingUser, "view merchandise available for purchase", "Trainer", "Member");

        List<Merchandise> merchandiseList = merchandiseDAO.getAllMerchandise();

        System.out.println("Available Merchandise:");
        for (Merchandise merchandise : merchandiseList) {
            System.out.println("ID: " + merchandise.getMerchandiseId());
            System.out.println("Name: " + merchandise.getName());
            System.out.println("Description: " + merchandise.getDescription());
            System.out.println("Price: $" + merchandise.getPrice());
            System.out.println("Stock: " + merchandise.getStock());
            System.out.println("---------------------------");
        }
    }
}
