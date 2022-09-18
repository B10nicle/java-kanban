package kanban.managers.taskManagers.inMemoryTasksManager;

import kanban.managers.Managers;
import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Task;
import kanban.tasks.Subtask;
import kanban.tasks.enums.TaskState;

import java.util.*;

/**
 * @author Oleg Khilko
 */

public class InMemoryTasksManager implements TasksManager {

    protected Map<Integer, Task> tasks;
    protected Map<Integer, Epic> epics;
    protected Map<Integer, Subtask> subtasks;
    protected HistoryManager historyManager;
    protected int id;

    public InMemoryTasksManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    public int getNextID() {
        return ++id;
    }

    // получение мапы всех тасков
    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    // получение мапы всех эпиков
    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    // получение мапы всех сабтасков
    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    // запрос таска
    @Override
    public Task getTask(Integer id) {
        var task = tasks.get(id);
        historyManager.add(task);

        return task;
    }

    // запрос эпика
    @Override
    public Epic getEpic(Integer id) {
        var epic = epics.get(id);
        historyManager.add(epic);

        return epic;
    }

    // запрос сабтаска
    @Override
    public Subtask getSubtask(Integer id) {
        var subtask = subtasks.get(id);
        historyManager.add(subtask);

        return subtask;
    }

    // создание таска
    @Override
    public Task createTask(Task task) {
        task.setId(getNextID());
        tasks.put(task.getId(), task);

        return task;
    }

    // создание эпика
    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(getNextID());
        epics.put(epic.getId(), epic);

        return epic;
    }

    // создание сабтаска
    @Override
    public Subtask createSubtask(Subtask subtask) {
        var epic = epics.get(subtask.getEpicID());

        subtask.setId(getNextID());
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
        updateEpicStatus(subtask.getEpicID());

        return subtask;
    }

    // удаление таска
    @Override
    public void removeTask(Integer id) {
        tasks.remove(id);
        historyManager.remove(id);
        System.out.println("Таск №" + id + " удалён");
    }

    // удаление эпика
    @Override
    public void removeEpic(Integer epicID) {
        var epic = epics.get(epicID);

        for (Integer subtask : epic.getSubtasks()) {
            subtasks.remove(subtask);
            historyManager.remove(subtask);
        }

        epics.remove(epicID);
        historyManager.remove(epicID);
        System.out.println("Эпик №" + epicID + " удалён");
    }

    // удаление сабтаска
    @Override
    public void removeSubtask(Integer id) {
        var subtask = subtasks.get(id);
        var epic = epics.get(subtask.getEpicID());

        epic.removeSubtask(subtask);
        subtasks.remove(id);
        updateEpicStatus(subtask.getEpicID());
        historyManager.remove(id);
        System.out.println("Сабтаск №" + id + " удалён");
    }

    // удаление всех тасков, эпиков и сабтасков
    @Override
    public void removeAllTasksEpicsSubtasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        historyManager.clear();
        System.out.println("Удалены все таски, эпики и сабтаски");
    }

    // печать списка всех тасков
    @Override
    public void printAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список тасков пуст.");
        }
        for (int id : tasks.keySet()) {
            var value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    // печать списка всех сабтасков
    @Override
    public void printAllSubtasks() {
        if (subtasks.isEmpty()) {
            System.out.println("Список сабтасков пуст.");
        }
        for (int id : subtasks.keySet()) {
            var value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    // печать списка всех эпиков
    @Override
    public void printAllEpics() {
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        }
        for (int id : epics.keySet()) {
            var value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    // обновление таска
    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        var currentTask = tasks.get(id);
        if (currentTask == null) {
            return;
        }
        tasks.put(task.getId(), task);
        System.out.println("Таск обновлён");
    }

    // обновление сабтаска
    @Override
    public void updateSubtask(Subtask subtask) {
        var epic = epics.get(subtask.getEpicID());
        int id = subtask.getId();
        var currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        epics.put(epic.getId(), epic);
        updateEpicStatus(subtask.getEpicID());
        System.out.println("Сабтаск обновлён");
    }

    // обновление эпика
    @Override
    public void updateEpic(Epic epic) {
        var subtask = subtasks.get(epic.getId());
        int id = epic.getId();
        var currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.put(epic.getId(), epic);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicID());
        System.out.println("Эпик обновлён");
    }

    // обновление статуса эпик
    private void updateEpicStatus(Integer id) {
        var epic = epics.get(id);
        int isNew = 0;
        int isDone = 0;
        if (epic.getSubtasks().isEmpty()) {
            epic.setTaskState(TaskState.NEW);
            return;
        }
        for (var iDsOfSubtask : epic.getSubtasks()) {
            if (subtasks.get(iDsOfSubtask).getTaskState() == TaskState.NEW) {
                isNew += 1;
            } else if (subtasks.get(iDsOfSubtask).getTaskState() == TaskState.DONE) {
                isDone += 1;
            }
        }
        if (epic.getSubtasks().size() == isNew) {
            epic.setTaskState(TaskState.NEW);
            return;
        } else if (epic.getSubtasks().size() == isDone) {
            epic.setTaskState(TaskState.DONE);
            return;
        }
        epic.setTaskState(TaskState.IN_PROGRESS);
    }

    // получение истории
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}