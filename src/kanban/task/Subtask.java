package kanban.task;

import kanban.task.enums.Status;

import java.util.Objects;

public class Subtask extends Task {
    private long epicID;

    public Subtask(String name, String description, Status status) {
        super(name, description, status);
    }

    public long getEpicID() {
        return epicID;
    }

    public void setEpicID(long epicID) {
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "Kanban.Subtask{" +
                "epicID=" + epicID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
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
