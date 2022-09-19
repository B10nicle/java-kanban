package kanban.tasks;

import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;

import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Subtask extends Task {

    private final TaskType taskType;
    private final Integer epicID;

    public Subtask(String name,
                   String description,
                   int epicID) {

        super(name, description);
        this.taskType = TaskType.SUBTASK;
        this.epicID = epicID;
    }

    public Subtask(Integer id,
                   String name,
                   TaskState taskState,
                   String description,
                   int epicID) {

        super(name, description);
        this.taskType = TaskType.SUBTASK;
        this.taskState = taskState;
        this.epicID = epicID;
        this.id = id;
    }

    public Integer getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return id + ","
                + taskType + ","
                + name + ","
                + taskState + ","
                + description + ","
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
        return Objects.hash(epicID);
    }

}