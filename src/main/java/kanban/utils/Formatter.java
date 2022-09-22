package kanban.utils;

import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.managers.taskManagers.fileBackedTasksManager.exceptions.NotSupportedTypeException;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Khilko
 */

public class Formatter {

    // преобразование истории в строку
    public static String historyToString(HistoryManager manager) {

        StringBuilder sb = new StringBuilder();

        for (Task task : manager.getHistory())
            sb.append(task.getId()).append(",");

        return sb.toString();

    }

    // преобразование истории из строки
    public static List<Integer> historyFromString(String value) {

        List<Integer> history = new ArrayList<>();

        for (var line : value.split(","))
            history.add(Integer.parseInt(line));

        return history;

    }

    // преобразование всех тасков, эпиков и сабтасков в одну строку, каждая с новой строки
    public static String tasksToString(TasksManager tasksManager) {

        List<Task> allTasks = new ArrayList<>();
        var result = new StringBuilder();

        allTasks.addAll(tasksManager.getTasks());
        allTasks.addAll(tasksManager.getEpics());
        allTasks.addAll(tasksManager.getSubtasks());

        for (var task : allTasks)
            result.append(task.toString()).append("\n");

        return result.toString();

    }

    // преобразование из строки обратно в таски, эпики и сабтаски
    public static Task tasksFromString(String value) {

        var values = value.split(",");
        var id = Integer.parseInt(values[0]);
        var type = values[1];
        var name = values[2];
        var status = TaskState.valueOf(values[3]);
        var description = values[4];
        int epicID = 0;

        if (TaskType.valueOf(type).equals(TaskType.SUBTASK))
            epicID = Integer.parseInt(values[5]);

        if (TaskType.valueOf(type).equals(TaskType.TASK))
            return new Task(id, name, status, description);

        if (TaskType.valueOf(type).equals(TaskType.EPIC))
            return new Epic(id, name, status, description);

        if (TaskType.valueOf(type).equals(TaskType.SUBTASK))
            return new Subtask(id, name, status, description, epicID);

        else
            throw new NotSupportedTypeException("Данный формат таска не поддерживается");
    }

}