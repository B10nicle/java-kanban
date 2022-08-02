package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Task;
import kanban.tasks.state.Status;
import kanban.tasks.Subtask;

import java.util.HashMap;

public class TaskManager {
    protected final HashMap<Long, Task> tasks = new HashMap<>();
    protected final HashMap<Long, Epic> epics = new HashMap<>();
    protected final HashMap<Long, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Long, Task> archive = new HashMap<>();

    private long idGenerator;

    public HashMap<Long, Task> getTasks() {
        return tasks;
    }

    public HashMap<Long, Epic> getEpics() {
        return epics;
    }

    public HashMap<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Long, Task> getArchive() {
        return archive;
    }

    //создание таска
    public Task createTask(Task task) {
        task.setStatus(Status.NEW);
        task.setId(++idGenerator);
        tasks.put(task.getId(), task);
        return task;
    }

    //создание сабтаска
    public Subtask createSubTask(Subtask subtask) {
        subtask.setStatus(Status.NEW);
        subtask.setId(++idGenerator);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    //создание эпика
    public Epic createEpic(Epic epic) {
        epic.setStatus(Status.NEW);
        epic.setId(++idGenerator);
        epics.put(epic.getId(), epic);
        return epic;
    }

    //добавление сабтаска в эпик
    public Epic addSubtaskToEpic(Epic epic, Subtask subtask) {
        subtask.setStatus(Status.NEW);
        epic.getIDsOfSubtasks().add(subtask.getId());
        subtask.setEpicID(epic.getId());
        return epic;
    }

    //удаление сабтаска из эпика и добавление эпика в архив
    public Epic deleteSubtaskFromEpic(Epic epic, Subtask subtask) {
        deleteSubtask(subtask);
        epic.getIDsOfSubtasks().remove(subtask.getId());

        //переключение статуса эпика в DONE если список сабтасков пуст
        if (epic.getIDsOfSubtasks().isEmpty()) {
            epic.setStatus(Status.DONE);
        }

        //удаление эпика из маппы эпиков если все сабтаски выполнены
        if (epic.getStatus() == Status.DONE) {
            archive.put(epic.getId(), epic);
            epics.remove(epic.getId(), epic);
        }
        return epic;
    }

    //удаление сабтаска и добавление в архив
    private Subtask deleteSubtask(Subtask subtask) {
        subtask.setStatus(Status.DONE);
        archive.put(subtask.getId(), subtask);
        subtasks.remove(subtask.getId(), subtask);
        return subtask;
    }

    //переключение таска в DONE, удаление из маппы и добавление в архив
    public Task deleteTask(Task task) {
        task.setStatus(Status.DONE);
        archive.put(task.getId(), task);
        tasks.remove(task.getId(), task);
        return task;
    }

    //печать списка всех тасков
    public void printAllTasks(HashMap<Long, Task> tasks) {
        for (Long id : tasks.keySet()) {
            Task value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (tasks.isEmpty()) {
            System.out.println("Список тасков пуст.");
        }
    }

    //печать списка всех сабтасков
    public void printAllSubtasks(HashMap<Long, Subtask> subtasks) {
        for (Long id : subtasks.keySet()) {
            Subtask value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (subtasks.isEmpty()) {
            System.out.println("Список сабтасков пуст.");
        }
    }

    //печать списка всех эпиков
    public void printAllEpics(HashMap<Long, Epic> epics) {
        for (Long id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        }
    }

    //печать архива
    public void printArchive(HashMap<Long, Task> archive) {
        System.out.println("Архив:");
        for (Long id : archive.keySet()) {
            Task value = archive.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (archive.isEmpty()) {
            System.out.println("Архив пуст.");
        }
    }

    //обновление таска
    public void updateTask(Task task) {
        task.setStatus(Status.IN_PROGRESS);
        long id = task.getId();
        Task currentTask = tasks.get(id);
        if (currentTask == null) {
            return;
        }
        tasks.put(task.getId(), task);
        System.out.println("Таск обновлён");
    }

    //обновление сабтаска
    public void updateSubtask(Subtask subtask) {
        subtask.setStatus(Status.IN_PROGRESS);
        long id = subtask.getId();
        Subtask currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        System.out.println("Сабтаск обновлён");
    }

    //обновление эпика
    public void updateEpic(Epic epic) {
        epic.setStatus(Status.IN_PROGRESS);
        long id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.put(epic.getId(), epic);
        System.out.println("Эпик обновлён");
    }
}
