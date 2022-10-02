package kanban.managers.taskManagers.inMemoryTasksManager;

import kanban.managers.taskManagers.exceptions.IntersectionException;
import kanban.managers.historyManagers.HistoryManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.managers.Managers;
import kanban.tasks.Subtask;
import kanban.tasks.Epic;
import kanban.tasks.Task;

import java.util.*;

/**
 * @author Oleg Khilko
 */

public class InMemoryTasksManager implements TasksManager, Comparator<Task> {

    protected static HistoryManager historyManager;
    protected Map<Integer, Subtask> subtasks;
    private Set<Task> prioritizedTasks;
    protected Map<Integer, Task> tasks;
    protected Map<Integer, Epic> epics;
    protected int id;

    public InMemoryTasksManager() {

        prioritizedTasks = new TreeSet<>(this);
        historyManager = Managers.getDefaultHistory();
        this.subtasks = new HashMap<>();
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();

    }

    public int getNextID() {

        return ++id;

    }

    // получение приоритетного списка + его конвертация из TreeSet в ArrayList
    private List<Task> getPrioritizedTasks() {

        return new ArrayList<>(prioritizedTasks);

    }

    // добавление таска в список + проверка нет ли пересечения
    private void addToPrioritizedTasks(Task task) {

        prioritizedTasks.add(task);
        checkIntersections();

    }

    // проверка нет ли пересечения
    private void checkIntersections() {

        var prioritizedTasks = getPrioritizedTasks();

        for (int i = 1; i < prioritizedTasks.size(); i++) {

            var prioritizedTask = prioritizedTasks.get(i);

            if (prioritizedTask.getStartTime().isBefore(prioritizedTasks.get(i - 1).getEndTime()))

                throw new IntersectionException("Найдено пересечение между "
                        + prioritizedTasks.get(i)
                        + " и "
                        + prioritizedTasks.get(i - 1));
        }

    }

    // печать приоритетного списка
    public void printPrioritizedTasks() {

        System.out.println("СПИСОК ПРИОРИТЕТНЫХ ЗАДАЧ: ");
        prioritizedTasks.forEach(System.out::println);

    }

    @Override // сравнение тасков по getStartTime()
    public int compare(Task o1, Task o2) {

        return o1.getStartTime().compareTo(o2.getStartTime());

    }

    @Override
    public List<Task> getTasks() {

        return new ArrayList<>(tasks.values());

    }

    @Override
    public List<Epic> getEpics() {

        return new ArrayList<>(epics.values());

    }

    @Override
    public List<Subtask> getSubtasks() {

        return new ArrayList<>(subtasks.values());

    }

    @Override
    public Task getTask(int id) {

        var task = tasks.get(id);
        historyManager.add(task);
        return task;

    }

    @Override
    public Epic getEpic(int id) {

        var epic = epics.get(id);
        historyManager.add(epic);

        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {

        var subtask = subtasks.get(id);
        historyManager.add(subtask);

        return subtask;
    }

    @Override
    public Task createTask(Task task) {

        task.setId(getNextID());
        tasks.put(task.getId(), task);
        addToPrioritizedTasks(task);

        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {

        epic.setId(getNextID());
        epics.put(epic.getId(), epic);

        return epic;
    }

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

    @Override
    public void removeTask(int id) {

        tasks.remove(id);
        historyManager.remove(id);
        prioritizedTasks.removeIf(t -> t.getId() == id);

    }

    @Override
    public void removeEpic(int epicID) {

        var epic = epics.get(epicID);

        if (epic == null)
            return;

        for (Integer id : epic.getSubtasks()) {
            prioritizedTasks.removeIf(t -> t.getId() == id);
            subtasks.remove(id);
            historyManager.remove(id);
        }

        epics.remove(epicID);
        historyManager.remove(epicID);

    }

    @Override
    public void removeSubtask(int id) {

        var subtask = subtasks.get(id);

        if (subtask == null)
            return;

        var epic = epics.get(subtask.getEpicID());
        prioritizedTasks.remove(subtask);
        subtasks.remove(id);
        epic.updateEpicState(subtasks);
        historyManager.remove(id);

    }

    @Override
    public void removeAllTasksEpicsSubtasks() {

        prioritizedTasks.clear();
        historyManager.clear();
        subtasks.clear();
        epics.clear();
        tasks.clear();

    }

    @Override
    public void printAllTasks() {

        if (tasks.isEmpty())
            System.out.println("Список тасков пуст.");

        for (int id : tasks.keySet()) {
            var value = tasks.get(id);
            System.out.println("№" + id + " " + value);
        }

    }

    @Override
    public void printAllSubtasks() {

        if (subtasks.isEmpty())
            System.out.println("Список сабтасков пуст.");

        for (int id : subtasks.keySet()) {
            var value = subtasks.get(id);
            System.out.println("№" + id + " " + value);
        }

    }

    @Override
    public void printAllEpics() {

        if (epics.isEmpty())
            System.out.println("Список эпиков пуст.");

        for (int id : epics.keySet()) {
            var value = epics.get(id);
            System.out.println("№" + id + " " + value);
        }

    }

    @Override
    public void update(Task task) {

        tasks.put(task.getId(), task);
        addToPrioritizedTasks(task);

    }

    @Override
    public void update(Subtask subtask) {

        subtasks.put(subtask.getId(), subtask);
        addToPrioritizedTasks(subtask);
        var epic = epics.get(subtask.getEpicID());
        epic.updateEpicState(subtasks);

    }

    @Override
    public void update(Epic epic) {

        epics.put(epic.getId(), epic);

    }

    @Override
    public List<Task> getHistory() {

        return historyManager.getHistory();

    }

}