package kanban.managers.taskManagers.fileBackedTasksManager;

import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.historyManagers.inMemoryHistoryManager.InMemoryHistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.managers.taskManagers.fileBackedTasksManager.exceptions.ManagerSaveException;
import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Oleg Khilko
 */

public class FileBackedTasksManager extends InMemoryTasksManager implements TasksManager {

    File autoSave = new File("results.csv");

    public FileBackedTasksManager() {
    }

    public FileBackedTasksManager(Map<Integer, Task> tasks,
                                  Map<Integer, Epic> epics,
                                  Map<Integer, Subtask> subtasks,
                                  HistoryManager historyManager) {

        this.tasks = tasks;
        this.epics = epics;
        this.subtasks = subtasks;
        this.historyManager = historyManager;

    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(autoSave, true))) {
            String header = "id, type, name, status, description, epic" + "\n";
            String values = header
                    + allTasksEpicsSubtasksToString(this)
                    + "\n"
                    + InMemoryHistoryManager.historyToString(historyManager);

            bw.write(values);

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    @Override
    public Task createTask(Task task) {
        var savedTask = super.createTask(task);
        save();

        return savedTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        var savedEpic = super.createEpic(epic);
        save();

        return savedEpic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        var savedSubtask = super.createSubtask(subtask);
        save();

        return savedSubtask;
    }

    @Override
    public void removeTask(Integer id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(Integer epicID) {
        super.removeEpic(epicID);
        save();
    }

    @Override
    public void removeSubtask(Integer id) {
        super.removeSubtask(id);
        save();
    }

    @Override
    public void removeAllTasksEpicsSubtasks() {
        super.removeAllTasksEpicsSubtasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public Task getTask(Integer id) {
        var savedTask = super.getTask(id);
        save();
        return savedTask;
    }

    @Override
    public Epic getEpic(Integer id) {
        var savedEpic = super.getEpic(id);
        save();
        return savedEpic;
    }

    @Override
    public Subtask getSubtask(Integer id) {
        var savedSubtask = super.getSubtask(id);
        save();
        return savedSubtask;
    }

    // преобразование всех тасков, эпиков и сабтасков в одну строку, каждая с новой строки
    private String allTasksEpicsSubtasksToString(TasksManager tasksManager) {

        List<Task> allTasks = new ArrayList<>();
        var result = "";

        for (Map.Entry<Integer, Task> entry : tasksManager.getTasks().entrySet())
            allTasks.add(entry.getValue());

        for (Map.Entry<Integer, Epic> entry : tasksManager.getEpics().entrySet())
            allTasks.add(entry.getValue());

        for (Map.Entry<Integer, Subtask> entry : tasksManager.getSubtasks().entrySet())
            allTasks.add(entry.getValue());

        for (var task : allTasks)
            result = Arrays.toString(task.toString().split("\n"));

        return result;

    }

}