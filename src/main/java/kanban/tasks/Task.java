package kanban.tasks;

import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Task {

    protected final String description;
    protected final TaskType taskType;
    protected TaskState taskState;
    protected final String name;
    protected LocalDateTime startTime;
    protected Long duration;
    protected Integer id;

    public Task(String name,
                String description,
                LocalDateTime startTime,
                Long duration) {

        this.taskState = TaskState.NEW;
        this.description = description;
        this.taskType = TaskType.TASK;
        this.startTime = startTime;
        this.duration = duration;
        this.name = name;
    }

    public Task(Integer id,
                String name,
                TaskState taskState,
                String description,
                LocalDateTime startTime,
                Long duration) {

        this.description = description;
        this.taskType = TaskType.TASK;
        this.startTime = startTime;
        this.taskState = taskState;
        this.duration = duration;
        this.name = name;
        this.id = id;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    @Override
    public String toString() {
        return id + ","
                + taskType + ","
                + name + ","
                + taskState + ","
                + description + ","
                + startTime + ","
                + duration + ","
                + getEndTime();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task that = (Task) o;

        return Objects.equals(this.name, that.name)
                && Objects.equals(this.description, that.description)
                && Objects.equals(this.id, that.id)
                && Objects.equals(this.taskState, that.taskState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, taskState);
    }

}