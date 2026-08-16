
import java.util.List;

public class WorkoutClassService {
    
    private WorkoutClassDAO workoutClassDAO;

    public WorkoutClassService(WorkoutClassDAO workoutClassDAO) {
        this.workoutClassDAO = workoutClassDAO;
    }


    public void createWorkoutClass(WorkoutClass workoutClass) {
        workoutClassDAO.addWorkoutClass(workoutClass);
    }

    public void updateWorkoutClass(WorkoutClass workoutClass) {
        workoutClassDAO.updateWorkoutClass(workoutClass);
    }

    public void deleteWorkoutClass(int classId) {
        workoutClassDAO.deleteWorkoutClass(classId);
    }

    public List<WorkoutClass> getAllWorkoutClasses() {
        return workoutClassDAO.getAllWorkoutClasses();
    }

    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) {
        return workoutClassDAO.getWorkoutClassesByTrainerId(trainerId);
    }

    public void assignTrainer(WorkoutClass workoutClass, int trainerId) {
        
        workoutClass.setTrainerId(trainerId);
        workoutClassDAO.updateWorkoutClass(workoutClass);

        System.out.println("Trainer assigned to workout class successfully.");
    }

    public void browseClasses() {

        List<WorkoutClass> workoutClasses = workoutClassDAO.getAllWorkoutClasses();

        System.out.println("Available Workout Classes:");
        for (WorkoutClass workoutClass : workoutClasses) {
            System.out.println("Class ID: " + workoutClass.getClassId());
            System.out.println("Trainer ID: " + workoutClass.getTrainerId());
            System.out.println("Description: " + workoutClass.getDescription());
            System.out.println("Schedule: " + workoutClass.getSchedule());
            System.out.println("---------------------------");
        }
    }
}
