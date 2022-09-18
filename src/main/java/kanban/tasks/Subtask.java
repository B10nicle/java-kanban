package kanban.tasks;

import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Subtask extends Task {

    private final Integer epicID;

    public Subtask(String name, String description, Integer epicID) {
        super(name, description);
        this.epicID = epicID;
    }

    public Integer getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "Kanban.Subtask{" +
                "epicID=" + epicID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + taskState +
                '}';
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