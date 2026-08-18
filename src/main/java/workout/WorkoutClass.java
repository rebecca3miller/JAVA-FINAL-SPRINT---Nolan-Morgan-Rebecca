public class WorkoutClass {
    

    private int classId;
    private int trainerId;
    private String description;
    private String schedule;


    public WorkoutClass() {

    }

    public WorkoutClass(int classId, int trainerId, String description, String schedule) {
        this.classId = classId;
        this.trainerId = trainerId;
        this.description = description;
        this.schedule = schedule;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "WorkoutClass{" +
                "classId=" + classId +
                ", trainerId=" + trainerId +
                ", description='" + description + '\'' +
                ", schedule='" + schedule + '\'' +
                '}';
    }

}
