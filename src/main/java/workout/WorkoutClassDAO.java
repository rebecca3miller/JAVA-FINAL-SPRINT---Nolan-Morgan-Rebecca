
package workout;

package workout;

import logger.CustomLogger;

import java.io.IOException;
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

    public void addWorkoutClass(WorkoutClass workoutClass) throws IOException {

        String sql = "INSERT INTO workout_classes " +
                 "(trainer_id, description, schedule) " +
                 "VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, workoutClass.getTrainerId());
                statement.setString(2, workoutClass.getDescription());
                statement.setString(3, workoutClass.getSchedule());

                statement.executeUpdate();

                System.out.println("Workout class added successfully.");

        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while adding a workout class.", e);
            throw new IOException("Error adding workout class.", e);
        }
    }

    public List<WorkoutClass> getAllWorkoutClasses() throws IOException {
        List<WorkoutClass> workoutClasses = new ArrayList<>();

        String sql = "SELECT * FROM workout_classes";

           try (PreparedStatement statement = connection.prepareStatement(sql);
               ResultSet resultSet = statement.executeQuery()) {

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
            CustomLogger.logError("Database transaction error while retrieving workout classes.", e);
            throw new IOException("Error retrieving workout classes.", e);
        }

        return workoutClasses;
    }

    public List<WorkoutClass> getWorkoutClassesByTrainerId(int trainerId) throws IOException {
        List<WorkoutClass> workoutClasses = new ArrayList<>();

        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trainerId);
            try (ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                
                WorkoutClass workoutClass = new WorkoutClass(
                    result.getInt("class_id"),
                    result.getInt("trainer_id"),
                    result.getString("description"),
                    result.getString("schedule")
                );

                workoutClasses.add(workoutClass);
            }
            }

        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while retrieving trainer workout classes.", e);
            throw new IOException("Error retrieving trainer workout classes.", e);
        }

        return workoutClasses;
    }

    public void updateWorkoutClass(WorkoutClass workoutClass) throws IOException {
        
        String sql = "UPDATE workout_classes " + 
                     "SET trainer_id = ?, description = ?, schedule = ? " + 
                     "WHERE class_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workoutClass.getTrainerId());
            statement.setString(2, workoutClass.getDescription());
            statement.setString(3, workoutClass.getSchedule());
            statement.setInt(4, workoutClass.getClassId());

            statement.executeUpdate();

            System.out.println("Workout class updated successfully.");


        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while updating a workout class.", e);
            throw new IOException("Error updating workout class.", e);
        }
    }


    public void deleteWorkoutClass(int classId) throws IOException {
        
        String sql = "DELETE FROM workout_classes WHERE class_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, classId);

            statement.executeUpdate();

            System.out.println("Workout class deleted successfully.");

        } catch (SQLException e) {
            CustomLogger.logError("Database transaction error while deleting a workout class.", e);
            throw new IOException("Error deleting workout class.", e);
        }
    }






}
