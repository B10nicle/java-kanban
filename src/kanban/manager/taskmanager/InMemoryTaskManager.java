package kanban.manager.taskmanager;

import kanban.task.Epic;
import kanban.task.Task;
import kanban.task.state.Status;
import kanban.task.Subtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private long idGenerator;
    private final Map<Long, Task> tasks;
    private final Map<Long, Epic> epics;
    private final Map<Long, Subtask> subtasks;
    private final List<Task> historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.historyManager = new ArrayList<>();
    }

    public Map<Long, Task> getTasks() {
        return tasks;
    }

    public Map<Long, Epic> getEpics() {
        return epics;
    }

    public Map<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    public List<Task> getHistoryManager() {
        return historyManager;
    }

    //создание таска
    @Override
    public Task createTask(Task task) {
        task.setStatus(Status.NEW);
        task.setId(++idGenerator);
        tasks.put(task.getId(), task);
        historyManager.add(task);
        if (historyManager.size() > 10) {
            historyManager.remove(0);
        }
        return task;
    }

    //создание сабтаска
    @Override
    public Subtask createSubTask(Subtask subtask) {
        subtask.setStatus(Status.NEW);
        subtask.setId(++idGenerator);
        subtasks.put(subtask.getId(), subtask);
        historyManager.add(subtask);
        if (historyManager.size() > 10) {
            historyManager.remove(0);
        }
        return subtask;
    }

    //создание эпика
    @Override
    public Epic createEpic(Epic epic) {
        epic.setStatus(Status.NEW);
        epic.setId(++idGenerator);
        epics.put(epic.getId(), epic);
        historyManager.add(epic);
        if (historyManager.size() > 10) {
            historyManager.remove(0);
        }
        return epic;
    }

    //добавление сабтаска в эпик
    @Override
    public Epic addSubtaskToEpic(Epic epic, Subtask subtask) {
        subtask.setStatus(Status.NEW);
        epic.getIDsOfSubtasks().add(subtask.getId());
        subtask.setEpicID(epic.getId());
        return epic;
    }

    //удаление сабтаска из эпика
    @Override
    public Epic deleteSubtaskFromEpic(Epic epic, Subtask subtask) {
        deleteSubtask(subtask);
        epic.getIDsOfSubtasks().remove(subtask.getId());

        //переключение статуса эпика в DONE если список сабтасков пуст
        if (epic.getIDsOfSubtasks().isEmpty()) {
            epic.setStatus(Status.DONE);
        }

        //удаление эпика из маппы эпиков если все сабтаски выполнены
        if (epic.getStatus() == Status.DONE) {
            epics.remove(epic.getId(), epic);
        }
        return epic;
    }

    //удаление эпика из маппы эпиков если список сабтасков пуст
    @Override
    public Epic deleteSubtaskFromEpic(Epic epic) {
        //переключение статуса эпика в DONE если список сабтасков пуст
        if (epic.getIDsOfSubtasks().isEmpty()) {
            epic.setStatus(Status.DONE);
        }

        //удаление эпика из маппы эпиков если все сабтаски выполнены
        if (epic.getStatus() == Status.DONE) {
            epics.remove(epic.getId(), epic);
        }
        return epic;
    }

    //удаление сабтаска
    @Override
    public Subtask deleteSubtask(Subtask subtask) {
        subtask.setStatus(Status.DONE);
        subtasks.remove(subtask.getId(), subtask);
        return subtask;
    }

    //переключение таска в DONE, удаление из маппы
    @Override
    public Task deleteTask(Task task) {
        task.setStatus(Status.DONE);
        tasks.remove(task.getId(), task);
        return task;
    }

    //печать списка всех тасков
    @Override
    public void printAllTasks(Map<Long, Task> tasks) {
        for (Long id : tasks.keySet()) {
            Task value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (tasks.isEmpty()) {
            System.out.println("Список тасков пуст.");
        }
    }

    //печать списка всех сабтасков
    @Override
    public void printAllSubtasks(Map<Long, Subtask> subtasks) {
        for (Long id : subtasks.keySet()) {
            Subtask value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (subtasks.isEmpty()) {
            System.out.println("Список сабтасков пуст.");
        }
    }

    //печать списка всех эпиков
    @Override
    public void printAllEpics(Map<Long, Epic> epics) {
        for (Long id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        }
    }

    //обновление таска
    @Override
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
    @Override
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
    @Override
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
