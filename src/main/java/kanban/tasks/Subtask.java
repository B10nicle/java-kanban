package kanban.tasks;

import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;

import java.time.Instant;
import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Subtask extends Task {

    private final TaskType taskType;
    private final int epicID;

    public Subtask(String name,
                   String description,
                   Instant startTime,
                   long duration,
                   int epicID) {

        super(name, description, startTime, duration);
        this.taskType = TaskType.SUBTASK;
        this.epicID = epicID;
    }

    public Subtask(int id,
                   String name,
                   TaskState taskState,
                   String description,
                   Instant startTime,
                   long duration,
                   int epicID) {

        super(name, description, startTime, duration);
        this.taskType = TaskType.SUBTASK;
        this.taskState = taskState;
        this.epicID = epicID;
        this.id = id;
    }

    public int getEpicID() {
        return epicID;
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
                + getEndTime() + ","
                + epicID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        if (!super.equals(o)) return false;

        Subtask that = (Subtask) o;

        return Objects.equals(this.epicID, that.epicID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicID);
    }

}