package kanban.tasks;

import java.util.ArrayList;

public class Epic extends Task {
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
                "IDsOfSubtasks=" + IDsOfSubtasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;

        Epic epic = (Epic) o;

        return getIDsOfSubtasks() != null ? getIDsOfSubtasks().equals(epic.getIDsOfSubtasks()) : epic.getIDsOfSubtasks() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getIDsOfSubtasks() != null ? getIDsOfSubtasks().hashCode() : 0);
        return result;
    }
}
