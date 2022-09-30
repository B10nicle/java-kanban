package kanban.tasks;

import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Epic extends Task {

    private Instant endTime = Instant.ofEpochSecond(0);
    private final ArrayList<Integer> subtasks;
    private final TaskType taskType;

    public Epic(String name,
                String description) {

        super(name, description, Instant.ofEpochSecond(0),0);
        this.subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }

    public Epic(int id,
                String name,
                TaskState taskState,
                String description,
                Instant startTime,
                long duration) {

        super(name, description, startTime, duration);
        this.endTime = super.getEndTime();
        this.subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
        this.taskState = taskState;
        this.id = id;
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask.getId());
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
    }

    @Override
    public Instant getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return id + ","
                + taskType + ","
                + name + ","
                + taskState + ","
                + description + ","
                + getStartTime() + ","
                + duration + ","
                + getEndTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;

        Epic that = (Epic) o;

        return Objects.equals(this.subtasks, that.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks);
    }

}