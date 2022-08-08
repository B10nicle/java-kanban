package kanban.manager.taskmanager;

import kanban.manager.Managers;
import kanban.manager.historymanager.HistoryManager;
import kanban.task.Epic;
import kanban.task.Task;
import kanban.task.Subtask;
import kanban.task.enums.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int idGenerator;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    //получение мапы всех тасков
    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    //получение мапы всех эпиков
    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    //получение мапы всех сабтасков
    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    //запрос таска
    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    //запрос эпика
    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    //запрос сабтаска
    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }


    //создание таска
    @Override
    public Task createTask(Task task) {
        task.setId(++idGenerator);
        tasks.put(task.getId(), task);
        return task;
    }

    //создание эпика
    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(++idGenerator);
        epics.put(epic.getId(), epic);
        return epic;
    }

    //создание сабтаска
    @Override
    public Subtask createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicID());
        subtask.setId(++idGenerator);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
        updateEpicStatus(subtask.getEpicID());
        return subtask;
    }

    //удаление таска
    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        System.out.println("Таск №" + id + " удалён");
    }

    @Override
    public void deleteEpic(int epicID) {
        Epic epic = epics.get(epicID);
        for (Integer subtasks : epic.getSubtasks()) {
            this.subtasks.remove(subtasks);
        }
        epics.remove(epicID);
        System.out.println("Эпик №" + epicID + " удалён");
    }

    //удаление сабтаска
    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = epics.get(subtask.getEpicID());
        epic.removeSubtask(subtask);
        subtasks.remove(id);
        updateEpicStatus(subtask.getEpicID());
        System.out.println("Сабтаск №" + id + " удалён");
    }

    //удаление всех тасков, эпиков и сабтасков
    @Override
    public void deleteAllTasksEpicsSubtasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        System.out.println("Удалены все таски, эпики и сабтаски");
    }

    //печать списка всех тасков
    @Override
    public void printAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список тасков пуст.");
        }
        for (int id : tasks.keySet()) {
            Task value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    //печать списка всех сабтасков
    @Override
    public void printAllSubtasks() {
        if (subtasks.isEmpty()) {
            System.out.println("Список сабтасков пуст.");
        }
        for (int id : subtasks.keySet()) {
            Subtask value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    //печать списка всех эпиков
    @Override
    public void printAllEpics() {
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        }
        for (int id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
    }

    //обновление таска
    @Override
    public void updateTask(Task task) {
        int id = task.getId();
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
        Epic epic = epics.get(subtask.getEpicID());
        int id = subtask.getId();
        Subtask currentSubtask = subtasks.get(id);
        if (currentSubtask == null) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        epics.put(epic.getId(), epic);
        updateEpicStatus(subtask.getEpicID());
        System.out.println("Сабтаск обновлён");
    }

    //обновление эпика
    @Override
    public void updateEpic(Epic epic) {
        Subtask subtask = subtasks.get(epic.getId());
        int id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.put(epic.getId(), epic);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicID());
        System.out.println("Эпик обновлён");
    }

    //обновление статуса эпик
    private void updateEpicStatus(int id) {
        Epic epic = epics.get(id);
        int isNew = 0;
        int isDone = 0;
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (Integer iDsOfSubtask : epic.getSubtasks()) {
            if (subtasks.get(iDsOfSubtask).getStatus() == Status.NEW) {
                isNew += 1;
            } else if (subtasks.get(iDsOfSubtask).getStatus() == Status.DONE) {
                isDone += 1;
            }
        }
        if (epic.getSubtasks().size() == isNew) {
            epic.setStatus(Status.NEW);
            return;
        } else if (epic.getSubtasks().size() == isDone) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);
    }

    //получение истории
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
