package kanban.managers.taskManagers.inMemoryTasksManager;

import kanban.managers.Managers;
import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.tasks.Epic;
import kanban.tasks.Task;
import kanban.tasks.Subtask;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Oleg Khilko
 */

public class InMemoryTasksManager implements TasksManager {

    private Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    protected static HistoryManager historyManager;
    protected Map<Integer, Subtask> subtasks;
    protected Map<Integer, Task> tasks;
    protected Map<Integer, Epic> epics;
    protected int id;

    public InMemoryTasksManager() {

        historyManager = Managers.getDefaultHistory();
        this.subtasks = new HashMap<>();
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();

    }

    public int getNextID() {

        return ++id;

    }

    // получение мапы всех тасков
    @Override
    public Collection<Task> getTasks() {

        return tasks.values();

    }

    // получение мапы всех эпиков
    @Override
    public Collection<Epic> getEpics() {

        return epics.values();

    }

    // получение мапы всех сабтасков
    @Override
    public Collection<Subtask> getSubtasks() {

        return subtasks.values();

    }

    // запрос таска
    @Override
    public Task getTask(int id) {

        var task = tasks.get(id);
        historyManager.add(task);

        return task;

    }

    // запрос эпика
    @Override
    public Epic getEpic(int id) {

        var epic = epics.get(id);
        historyManager.add(epic);

        return epic;
    }

    // запрос сабтаска
    @Override
    public Subtask getSubtask(int id) {

        var subtask = subtasks.get(id);
        historyManager.add(subtask);

        return subtask;
    }

    // создание таска
    @Override
    public Task createTask(Task task) {

        task.setId(getNextID());
        tasks.put(task.getId(), task);
        addToPrioritizedTasks(task);

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
        addToPrioritizedTasks(subtask);
        epic.addSubtask(subtask);
        epic.updateEpicState(subtasks);

        return subtask;
    }

    // удаление таска
    @Override
    public void removeTask(int id) {

        tasks.remove(id);
        historyManager.remove(id);

    }

    // удаление эпика
    @Override
    public void removeEpic(int epicID) {

        var epic = epics.get(epicID);

        for (Integer subtask : epic.getSubtasks()) {
            subtasks.remove(subtask);
            historyManager.remove(subtask);
        }

        epics.remove(epicID);
        historyManager.remove(epicID);

    }

    // удаление сабтаска
    @Override
    public void removeSubtask(int id) {

        var subtask = subtasks.get(id);
        var epic = epics.get(subtask.getEpicID());
        epic.removeSubtask(subtask);
        subtasks.remove(id);
        epic.updateEpicState(subtasks);
        historyManager.remove(id);

    }

    // удаление всех тасков, эпиков и сабтасков
    @Override
    public void removeAllTasksEpicsSubtasks() {

        tasks.clear();
        epics.clear();
        subtasks.clear();
        historyManager.clear();
        prioritizedTasks.clear();

    }

    // печать списка всех тасков
    @Override
    public void printAllTasks() {

        if (tasks.isEmpty())
            System.out.println("Список тасков пуст.");

        for (int id : tasks.keySet()) {
            var value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }

    }

    // печать списка всех сабтасков
    @Override
    public void printAllSubtasks() {

        if (subtasks.isEmpty())
            System.out.println("Список сабтасков пуст.");

        for (int id : subtasks.keySet()) {
            var value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }

    }

    // печать списка всех эпиков
    @Override
    public void printAllEpics() {

        if (epics.isEmpty())
            System.out.println("Список эпиков пуст.");

        for (int id : epics.keySet()) {
            var value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }

    }

    // обновление таска
    @Override
    public void update(Task task) {

        tasks.put(task.getId(), task);
        addToPrioritizedTasks(task);

    }

    // обновление сабтаска
    @Override
    public void update(Subtask subtask) {

        subtasks.put(subtask.getId(), subtask);
        addToPrioritizedTasks(subtask);
        var epic = epics.get(subtask.getEpicID());
        epic.updateEpicState(subtasks);

    }

    // обновление эпика
    @Override
    public void update(Epic epic) {

        epics.put(epic.getId(), epic);

    }

    // получение истории
    @Override
    public List<Task> getHistory() {

        return historyManager.getHistory();

    }

    private List<Task> getPrioritizedTasks() {

        return new ArrayList<>(prioritizedTasks);

    }

    private void addToPrioritizedTasks(Task task) {

        prioritizedTasks.add(task);
        checkIntersections();

    }

    private void checkIntersections() {

        var tasks = getPrioritizedTasks();

        for (int i = 1; i < tasks.size(); i++) {

            var task = tasks.get(i);

            if (task.getStartTime().isBefore(tasks.get(i - 1).getEndTime()))
                throw new RuntimeException("Пересечение между " + task.getId() + " и " + tasks.get(i - 1));

        }

    }

}