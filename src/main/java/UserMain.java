public class UserMain {
    
    public static void main(String[] args) {
      
        Merchandise merchandise = new Merchandise(1, "T-Shirt", "A comfortable cotton t-shirt", 19.99, 50);

        merchandise.setName("Premium T-Shirt");
        merchandise.setDescription("A premium quality cotton t-shirt");
        merchandise.setPrice(24.99);
        merchandise.setStock(30);

        System.out.println("Merchandise Details:");
        System.out.println("ID: " + merchandise.getMerchandiseId());
        System.out.println("Name: " + merchandise.getName());
        System.out.println("Description: " + merchandise.getDescription());
        System.out.println("Price: $" + merchandise.getPrice());
        System.out.println("Stock: " + merchandise.getStock());

        WorkoutClass workout = new WorkoutClass(1, 101, "Yoga Class", "Monday 10:00 AM");

        workout.setTrainerId(23);
        workout.setDescription("Advanced Yoga Class");
        workout.setSchedule("Wednesday 6:00 PM");

        System.out.println("Workout Class Details:");
        System.out.println("Class ID: " + workout.getClassId());
        System.out.println("Trainer ID: " + workout.getTrainerId());
        System.out.println("Description: " + workout.getDescription());
        System.out.println("Schedule: " + workout.getSchedule());

    }
}
