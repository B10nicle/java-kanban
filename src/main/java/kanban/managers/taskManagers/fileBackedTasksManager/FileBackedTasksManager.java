package kanban.managers.taskManagers.fileBackedTasksManager;

import kanban.managers.taskManagers.inMemoryTasksManager.InMemoryTasksManager;
import kanban.managers.taskManagers.exceptions.ManagerSaveException;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.enums.TaskType;
import kanban.utils.Formatter;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.Epic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.*;

/**
 * @author Oleg Khilko
 */

public class FileBackedTasksManager extends InMemoryTasksManager implements TasksManager {

    private Path filePath = Path.of("results.csv");

    public FileBackedTasksManager(Path filePath) {
        this.filePath = filePath;
    }

    public FileBackedTasksManager() {
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
    public void removeTask(int id) {

        super.removeTask(id);
        save();

    }

    @Override
    public void removeEpic(int epicID) {

        super.removeEpic(epicID);
        save();

    }

    @Override
    public void removeSubtask(int id) {

        super.removeSubtask(id);
        save();

    }

    @Override
    public void removeAllTasksEpicsSubtasks() {

        super.removeAllTasksEpicsSubtasks();
        save();

    }

    @Override
    public void update(Task task) {

        super.update(task);
        save();

    }

    @Override
    public void update(Epic epic) {

        super.update(epic);
        save();

    }

    @Override
    public void update(Subtask subtask) {

        super.update(subtask);
        save();

    }

    @Override
    public Task getTask(int id) {

        var savedTask = super.getTask(id);
        save();

        return savedTask;

    }

    @Override
    public Epic getEpic(int id) {

        var savedEpic = super.getEpic(id);
        save();

        return savedEpic;

    }

    @Override
    public Subtask getSubtask(int id) {

        var savedSubtask = super.getSubtask(id);
        save();

        return savedSubtask;

    }

    // сохранение в файл
    private void save() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath.toFile()));
             BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {

            if (br.readLine() == null) {

                String header = "id,type,name,status,description,startTime,duration,endTime,epic" + "\n";
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

        var fileBackedTasksManager = new FileBackedTasksManager(path);

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