package kanban.tasks;

public class Subtask extends Task {
    private long epicID;

    public Subtask(String name, String description) {
        super(name, description);
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

        Subtask subtask = (Subtask) o;

        return epicID == subtask.epicID;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (epicID ^ (epicID >>> 32));
        return result;
    }
}
