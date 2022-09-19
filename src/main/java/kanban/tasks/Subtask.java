package kanban.tasks;

import kanban.tasks.enums.TaskType;

import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Subtask extends Task {

    private final Integer epicID;
    private final TaskType taskType;

    public Subtask(String name, String description, Integer epicID) {
        super(name, description);
        this.epicID = epicID;
        this.taskType = TaskType.SUBTASK;
    }

    public Integer getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return id + "," + taskType + "," + name + "," + taskState + "," + description + "," + epicID;
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