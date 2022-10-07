package kanban.tasks;

import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;

import java.util.ArrayList;
import java.time.Duration;
import java.util.Objects;
import java.time.Instant;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

public class Epic extends Task {

    private Instant endTime = Instant.ofEpochSecond(0);
    private final ArrayList<Integer> subtasks;
    private final TaskType taskType;

    public Epic(String name,
                String description) {

        super(name, description, Instant.ofEpochSecond(0), 0);
        this.subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;

    }

    public Epic(int id,
                String name,
                TaskState taskState,
                String description,
                Instant startTime,
                long duration) {

        super(name, description, startTime, duration);
        this.endTime = super.getEndTime();
        this.subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
        this.taskState = taskState;
        this.id = id;

    }

    // обновление состояния эпика
    public void updateEpicState(Map<Integer, Subtask> subs) {

        Instant startTime = subs.get(subtasks.get(0)).getStartTime();
        Instant endTime = subs.get(subtasks.get(0)).getEndTime();

        int isNew = 0;
        int isDone = 0;

        for (var id : getSubtasks()) {

            var subtask = subs.get(id);

            if (subtask.getTaskState() == TaskState.NEW)
                isNew += 1;

            if (subtask.getTaskState() == TaskState.DONE)
                isDone += 1;

            if (subtask.getStartTime().isBefore(startTime))
                startTime = subtask.getStartTime();

            if (subtask.getEndTime().isAfter(endTime))
                endTime = subtask.getEndTime();
        }

        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = Duration.between(startTime, endTime).toMinutes();

        if (getSubtasks().size() == isNew) {
            setTaskState(TaskState.NEW);
            return;

        } else if (getSubtasks().size() == isDone) {
            setTaskState(TaskState.DONE);
            return;
        }

        setTaskState(TaskState.IN_PROGRESS);

    }

    public ArrayList<Integer> getSubtasks() {

        return subtasks;

    }

    public void addSubtask(Subtask subtask) {

        subtasks.add(subtask.getId());

    }

    public void setEndTime(Instant endTime) {

        this.endTime = endTime;

    }

    @Override
    public Instant getEndTime() {

        return endTime;

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
                + getEndTime();

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

        return Objects.hash(super.hashCode(), subtasks);

    }

}