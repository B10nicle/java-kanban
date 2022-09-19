package kanban.managers.taskManagers.fileBackedTasksManager;

import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.historyManagers.inMemoryHistoryManager.InMemoryHistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.managers.taskManagers.fileBackedTasksManager.exceptions.ManagerSaveException;
import kanban.managers.taskManagers.fileBackedTasksManager.exceptions.NotSupportedTypeException;
import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.enums.TaskState;
import kanban.tasks.enums.TaskType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author Oleg Khilko
 */

public class FileBackedTasksManager extends InMemoryTasksManager implements TasksManager {

    public FileBackedTasksManager() {

    }

    public FileBackedTasksManager(int id,
                                  HistoryManager historyManager,
                                  Map<Integer, Task> tasks,
                                  Map<Integer, Epic> epics,
                                  Map<Integer, Subtask> subtasks) {

        this.historyManager = historyManager;
        this.subtasks = subtasks;
        this.tasks = tasks;
        this.epics = epics;
        this.id = id;

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

    // сохранение в файл
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

    // загрузка из файла
    public static FileBackedTasksManager load(Path path) {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Map<Integer, Subtask> subtasks = new HashMap<>();
        Map<Integer, Task> allTasks = new HashMap<>();
        Map<Integer, Epic> epics = new HashMap<>();
        Map<Integer, Task> tasks= new HashMap<>();
        int id = 0;

        try {
            var fileName = Files.readString(path);

            var lines = fileName.split("\n");

            for (int i = 1; i < lines.length - 2; i++) {
                var task = fromString(lines[i]);
                var type = lines[i].split(",")[1];

                if (task.getId() > id)
                    id = task.getId();

                if (TaskType.valueOf(type).equals(TaskType.TASK)) {
                    tasks.put(task.getId(), task);
                    allTasks.put(task.getId(), task);
                }

                if (TaskType.valueOf(type).equals(TaskType.EPIC)) {
                    epics.put(task.getId(), (Epic) task);
                    allTasks.put(task.getId(), task);
                }

                if (TaskType.valueOf(type).equals(TaskType.SUBTASK)) {
                    assert task instanceof Subtask;
                    var subtask = (Subtask) task;
                    subtasks.put(task.getId(), subtask);
                    epics.get(subtask.getEpicID()).addSubtask(subtask);
                    allTasks.put(task.getId(), task);
                }
            }

            var history = InMemoryHistoryManager.historyFromString(lines[lines.length - 1]);

            for (Integer thisID : history)
                historyManager.add(allTasks.get(thisID));

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла");
        }

        return new FileBackedTasksManager(id, historyManager, tasks, epics, subtasks);

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

    // преобразование из строки обратно в таски, эпики и сабтаски
    private static Task fromString(String value) {

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