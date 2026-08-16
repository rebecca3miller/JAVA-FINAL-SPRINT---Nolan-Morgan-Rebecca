
import java.util.List;

public class MerchandiseService {
    
    private MerchandiseDAO merchandiseDAO;

    public MerchandiseService(MerchandiseDAO merchandiseDAO) {
        this.merchandiseDAO = merchandiseDAO;
    }

    public void addMerchandise(Merchandise merchandise) {
        merchandiseDAO.addMerchandise(merchandise);
    }

    public List<Merchandise> getAllMerchandise() {
        return merchandiseDAO.getAllMerchandise();
    }

    public void purchaseMerchandise(int merchandiseId, int quantity) {
        merchandiseDAO.purchaseMerchandise(merchandiseId, quantity);
    }

    public double getInventoryValue() {
        return merchandiseDAO.getInventoryValue();
    }

    public void updateStock(int merchandiseId, int newStock) {
        merchandiseDAO.updateStock(merchandiseId, newStock);
    }

    public void browseMerchandise() {
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
