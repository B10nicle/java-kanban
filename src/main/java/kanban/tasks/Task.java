package kanban.tasks;

import kanban.tasks.enums.TaskState;

import java.util.Objects;

/**
 * @author Oleg Khilko
 */

public class Task {

    protected final String name;
    protected final String description;
    protected Integer id;
    protected TaskState taskState;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.taskState = TaskState.NEW;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    @Override
    public String toString() {
        return "Kanban.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + taskState +
                '}';
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