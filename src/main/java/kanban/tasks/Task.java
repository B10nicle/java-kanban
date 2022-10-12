package kanban.tasks;

import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;

import java.time.Instant;
import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Task {

    protected final String description;
    protected TaskState taskState;
    protected TaskType taskType;
    protected final String name;
    protected Instant startTime;
    protected long duration;
    protected int id;

    public Task(String name,
                String description,
                Instant startTime,
                long duration) {

        this.taskState = TaskState.NEW;
        this.description = description;
        this.taskType = TaskType.TASK;
        this.startTime = startTime;
        this.duration = duration;
        this.name = name;

    }

    public Task(int id,
                String name,
                TaskState taskState,
                String description,
                Instant startTime,
                long duration) {

        this.description = description;
        this.taskType = TaskType.TASK;
        this.startTime = startTime;
        this.taskState = taskState;
        this.duration = duration;
        this.name = name;
        this.id = id;

    }

    public Instant getStartTime() {

        return startTime;

    }

    public Instant getEndTime() {

        final byte SECONDS_IN_ONE_MINUTE = 60;

        return startTime.plusSeconds(duration * SECONDS_IN_ONE_MINUTE);

    }

    public String getName() {

        return name;

    }

    public String getDescription() {

        return description;

    }

    public int getId() {

        return id;

    }

    public void setId(int id) {

        this.id = id;

    }

    public TaskState getTaskState() {

        return taskState;

    }

    public void setTaskState(TaskState taskState) {

        this.taskState = taskState;

    }

    public TaskType getTaskType() {

        return taskType;

    }

    public void setStartTime(Instant startTime) {

        this.startTime = startTime;

    }

    public long getDuration() {

        return duration;

    }

    public void setDuration(long duration) {

        this.duration = duration;

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