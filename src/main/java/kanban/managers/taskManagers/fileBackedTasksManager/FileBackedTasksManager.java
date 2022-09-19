package kanban.managers.taskManagers.fileBackedTasksManager;

import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.historyManagers.inMemoryHistoryManager.InMemoryHistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.managers.taskManagers.fileBackedTasksManager.exceptions.ManagerSaveException;
import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.*;
import java.util.*;

/**
 * @author Oleg Khilko
 */

public class FileBackedTasksManager extends InMemoryTasksManager implements TasksManager {

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

    private void save() {

        File autoSave = new File("results.csv");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(autoSave));
             BufferedReader br = new BufferedReader(new FileReader(autoSave))) {

            if (br.readLine() == null) {
                String header = "id,type,name,status,description,epic" + "\n";
                bw.write(header);
            }

            String values = allTasksEpicsSubtasksToString(this)
                    + "\n"
                    + InMemoryHistoryManager.historyToString(historyManager);

            bw.write(values);

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    // преобразование всех тасков, эпиков и сабтасков в одну строку, каждая с новой строки
    private String allTasksEpicsSubtasksToString(TasksManager tasksManager) {

        List<Task> allTasks = new ArrayList<>();
        var result = new StringBuilder();

        allTasks.addAll(tasksManager.getTasks());
        allTasks.addAll(tasksManager.getEpics());
        allTasks.addAll(tasksManager.getSubtasks());

        for (var task : allTasks)
            result.append(task.toString()).append("\n");

        return result.toString();

    }

}