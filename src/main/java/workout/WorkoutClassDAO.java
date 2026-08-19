
package workout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutClassDAO {
    
    private Connection connection;

    public WorkoutClassDAO(Connection connection) {
        this.connection = connection;
    }

    public void addWorkoutClass(WorkoutClass workoutClass) {

        String sql = "INSERT INTO workout_classes " + 
                     "(class_id, trainer_id, description, schedule) " +
                     "VALUES (?, ?, ?, ?)";

        try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, workoutClass.getClassId());
                statement.setInt(2, workoutClass.getTrainerId());
                statement.setString(3, workoutClass.getDescription());
                statement.setString(4, workoutClass.getSchedule());

                statement.executeUpdate();

                System.out.println("Workout class added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding workout class: ");
            System.out.println(e.getMessage());
        }
    }

    public List<WorkoutClass> getAllWorkoutClasses() {
        List<WorkoutClass> workoutClasses = new ArrayList<>();

        String sql = "SELECT * FROM workout_classes";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                
                WorkoutClass workoutClass = new WorkoutClass(
                    resultSet.getInt("class_id"),
                    resultSet.getInt("trainer_id"),
                    resultSet.getString("description"),
                    resultSet.getString("schedule")
                );

                workoutClasses.add(workoutClass);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving workout classes: ");
            System.out.println(e.getMessage());
        }

        return workoutClasses;
    }

    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) {
        List<WorkoutClass> workoutClasses = new ArrayList<>();

        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, trainerId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                
                WorkoutClass workoutClass = new WorkoutClass(
                    result.getInt("class_id"),
                    result.getInt("trainer_id"),
                    result.getString("description"),
                    result.getString("schedule")
                );

                workoutClasses.add(workoutClass);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving workout classes by trainer ID: ");
            System.out.println(e.getMessage());
        }

        return workoutClasses;
    }

    public void updateWorkoutClass(WorkoutClass workoutClass) {
        
        String sql = "UPDATE workout_classes " + 
                     "SET trainer_id = ?, description = ?, schedule = ? " + 
                     "WHERE class_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, workoutClass.getTrainerId());
            statement.setString(2, workoutClass.getDescription());
            statement.setString(3, workoutClass.getSchedule());
            statement.setInt(4, workoutClass.getClassId());

            statement.executeUpdate();

            System.out.println("Workout class updated successfully.");


        } catch (SQLException e) {
            System.out.println("Error updating workout class: ");
            System.out.println(e.getMessage());
        }
    }


    public void deleteWorkoutClass(int classId) {
        
        String sql = "DELETE FROM workout_classes WHERE class_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, classId);

            statement.executeUpdate();

            System.out.println("Workout class deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting workout class: ");
            System.out.println(e.getMessage());
        }
    }






}
