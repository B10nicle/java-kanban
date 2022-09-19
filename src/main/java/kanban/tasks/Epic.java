package kanban.tasks;

import kanban.tasks.enums.TaskType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Epic extends Task {

    private final ArrayList<Integer> subtasks;
    private final TaskType taskType;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask.getId());
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
    }

    @Override
    public String toString() {
        return id + "," + taskType + "," + name + "," + taskState + "," + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;

        Epic that = (Epic) o;

        return Objects.equals(this.subtasks, that.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtasks);
    }

}