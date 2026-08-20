
package workout;

import user.Authorization;
import user.User;

import java.io.IOException;
import java.util.List;

public class WorkoutClassService {

    private final WorkoutClassDAO workoutClassDAO;

    public WorkoutClassService(WorkoutClassDAO workoutClassDAO) {
        this.workoutClassDAO = workoutClassDAO;
    }

    public void createWorkoutClass(User requestingUser, WorkoutClass workoutClass) throws IOException {
        Authorization.requireRole(requestingUser, "create workout classes", "Admin", "Trainer");
        if (workoutClass == null) {
            throw new IOException("Workout class details are required.");
        }
        if ("Trainer".equals(requestingUser.getRole())
                && requestingUser.getId() != workoutClass.getTrainerId()) {
            throw new IOException("Trainers can only create their own workout classes.");
        }
        workoutClassDAO.addWorkoutClass(workoutClass);
    }

    public void updateWorkoutClass(User requestingUser, WorkoutClass workoutClass) throws IOException {
        Authorization.requireRole(requestingUser, "update workout classes", "Admin", "Trainer");
        if ("Trainer".equals(requestingUser.getRole()) && requestingUser.getId() != workoutClass.getTrainerId()) {
            throw new IOException("Trainers can only update their own workout classes.");
        }
        workoutClassDAO.updateWorkoutClass(workoutClass);
    }

    public void deleteWorkoutClass(User requestingUser, int classId) throws IOException {
        Authorization.requireRole(requestingUser, "delete workout classes", "Admin", "Trainer");
        if ("Trainer".equals(requestingUser.getRole())) {
            WorkoutClass workoutClass = workoutClassDAO.getAllWorkoutClasses().stream()
                    .filter(item -> item.getClassId() == classId)
                    .findFirst()
                    .orElse(null);
            if (workoutClass == null || workoutClass.getTrainerId() != requestingUser.getId()) {
                throw new IOException("Trainers can only delete their own workout classes.");
            }
        }
        workoutClassDAO.deleteWorkoutClass(classId);
    }

    public List<WorkoutClass> getAllWorkoutClasses(User requestingUser) throws IOException {
        Authorization.requireRole(requestingUser, "view workout classes", "Member");
        return workoutClassDAO.getAllWorkoutClasses();
    }

    public List<WorkoutClass> getWorkoutClassesByTrainerId(User requestingUser, int trainerId) throws IOException {
        Authorization.requireRole(requestingUser, "view trainer workout classes", "Trainer");
        if (requestingUser.getId() != trainerId) {
            throw new IOException("Trainers can only view their own assigned classes.");
        }
        return workoutClassDAO.getWorkoutClassesByTrainerId(trainerId);
    }

    public void assignTrainer(User requestingUser, WorkoutClass workoutClass, int trainerId) throws IOException {
        Authorization.requireRole(requestingUser, "assign a trainer to a workout class", "Admin");
        workoutClass.setTrainerId(trainerId);
        workoutClassDAO.updateWorkoutClass(workoutClass);
        System.out.println("Trainer assigned to workout class successfully.");
    }

    public void browseClasses(User requestingUser) throws IOException {
        Authorization.requireRole(requestingUser, "browse all available workout classes", "Member");
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
