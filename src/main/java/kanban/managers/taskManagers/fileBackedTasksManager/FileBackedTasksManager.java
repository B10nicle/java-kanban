package kanban.managers.taskManagers.fileBackedTasksManager;

import kanban.utils.Formatter;
import kanban.managers.taskManagers.TasksManager;
import kanban.managers.taskManagers.fileBackedTasksManager.exceptions.ManagerSaveException;
import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.enums.TaskType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Oleg Khilko
 */

public class FileBackedTasksManager extends InMemoryTasksManager implements TasksManager {

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

            String values = Formatter.tasksToString(this)
                    + "\n"
                    + Formatter.historyToString(historyManager);

            bw.write(values);

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    // загрузка из файла
    public static FileBackedTasksManager load(Path path) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();

        try {
            var fileName = Files.readString(path);

            var lines = fileName.split("\n");

            for (int i = 1; i < lines.length - 2; i++) {
                var task = Formatter.tasksFromString(lines[i]);
                var type = lines[i].split(",")[1];

                if (TaskType.valueOf(type).equals(TaskType.TASK)) {
                    fileBackedTasksManager.createTask(task);
                    historyManager.add(fileBackedTasksManager.getTask(task.getId()));
                }

                if (TaskType.valueOf(type).equals(TaskType.EPIC)) {
                    var epic = (Epic) task;
                    fileBackedTasksManager.createEpic(epic);
                    historyManager.add(fileBackedTasksManager.getEpic(epic.getId()));
                }

                if (TaskType.valueOf(type).equals(TaskType.SUBTASK)) {
                    var subtask = (Subtask) task;
                    fileBackedTasksManager.createSubtask(subtask);
                    historyManager.add(fileBackedTasksManager.getSubtask(subtask.getId()));
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла");
        }

        return fileBackedTasksManager;

    }

}