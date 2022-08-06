package kanban.manager.taskmanager;

import kanban.manager.Managers;
import kanban.manager.historymanager.HistoryManager;
import kanban.task.Epic;
import kanban.task.Task;
import kanban.task.enums.Status;
import kanban.task.Subtask;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private long idGenerator;
    private final Map<Long, Task> tasks;
    private final Map<Long, Epic> epics;
    private final Map<Long, Subtask> subtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
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

    //создание таска
    @Override
    public Task createTask(Task task) {
        task.setId(++idGenerator);
        tasks.put(task.getId(), task);
        historyManager.getHistory().add(task);
        if (historyManager.getHistory().size() > 10) {
            historyManager.getHistory().remove(0);
        }
        return task;
    }

    //создание сабтаска
    @Override
    public Subtask createSubTask(Subtask subtask) {
        subtask.setId(++idGenerator);
        subtasks.put(subtask.getId(), subtask);
        historyManager.getHistory().add(subtask);
        if (historyManager.getHistory().size() > 10) {
            historyManager.getHistory().remove(0);
        }
        return subtask;
    }

    //создание эпика
    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(++idGenerator);
        epics.put(epic.getId(), epic);
        historyManager.getHistory().add(epic);
        if (historyManager.getHistory().size() > 10) {
            historyManager.getHistory().remove(0);
        }
        return epic;
    }

    //добавление сабтаска в эпик
    @Override
    public Epic addSubtaskToEpic(Epic epic, Subtask subtask) {
        epic.getIDsOfSubtasks().add(subtask.getId());
        subtask.setEpicID(epic.getId());
        return epic;
    }

    //удаление сабтаска из эпика
    @Override
    public Epic deleteSubtaskFromEpic(Epic epic, Subtask subtask) {
        deleteSubtask(subtask);
        epic.getIDsOfSubtasks().remove(subtask.getId());
        if (epic.getStatus() == Status.DONE) {
            epics.remove(epic.getId(), epic);
        }
        return epic;
    }

    //удаление эпика из маппы эпиков если список сабтасков пуст
    @Override
    public Epic deleteSubtaskFromEpic(Epic epic) {
        if (epic.getStatus() == Status.DONE) {
            epics.remove(epic.getId(), epic);
        }
        return epic;
    }

    //удаление сабтаска
    @Override
    public Subtask deleteSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId(), subtask);
        return subtask;
    }

    //удаление таска
    @Override
    public Task deleteTask(Task task) {
        tasks.remove(task.getId(), task);
        return task;
    }

    //печать списка всех тасков
    @Override
    public void printAllTasks() {
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
    public void printAllSubtasks() {
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
    public void printAllEpics() {
        for (Long id : epics.keySet()) {
            Epic value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        }
    }

    //обновление таска
    //логика метода: пришел таск, мы через геттер взяли у него id и сохранили в long id, дёрнули этот таск из маппы.
    //если ссылка на currentTask == null, то есть объекта нет, то return, а если он есть - то перезапись в его ячейку
    @Override
    public void updateTask(Task task) {
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
        long id = epic.getId();
        Epic currentEpic = epics.get(id);
        if (currentEpic == null) {
            return;
        }
        epics.put(epic.getId(), epic);
        System.out.println("Эпик обновлён");
    }
}
