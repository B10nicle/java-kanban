package kanban.task;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task{
    private ArrayList<Long> IDsOfSubtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Long> getIDsOfSubtasks() {
        return IDsOfSubtasks;
    }

    public void setIDsOfSubtasks(ArrayList<Long> IDsOfSubtasks) {
        this.IDsOfSubtasks = IDsOfSubtasks;
    }

    @Override
    public String toString() {
        return "Kanban.Epic{" +
                "subtasks=" + IDsOfSubtasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;

        Epic that = (Epic) o;

        return Objects.equals(this.IDsOfSubtasks, that.IDsOfSubtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IDsOfSubtasks);
    }
}
