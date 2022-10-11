/*
package kanban.managers.taskManagers.httpTasksManager;

import kanban.managers.taskManagers.fileBackedTasksManager.FileBackedTasksManager;
import kanban.servers.KVTaskClient;
import kanban.utils.Formatter;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;

*/
/**
 * @author Oleg Khilko
 *//*


public class HttpTasksManager extends FileBackedTasksManager {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    protected KVTaskClient serverClient;
    private final Gson gson;


    public HttpTasksManager() {

        gson = Formatter.createGson();
        serverClient = new KVTaskClient();

    }

    public KVTaskClient getServerClient() {

        return serverClient;

    }

    @Override
    public void save() {

        String allTasks = gson.toJson(getTasksPerId());
        serverClient.put("tasks/task", allTasks);
        String allEpics = gson.toJson(getEpicsPerId());
        serverClient.put("tasks/epic", allEpics);
        String allSubTasks = gson.toJson(getSubTasksPerId());
        serverClient.put("tasks/subtask", allSubTasks);
        String history = gson.toJson(getHistory());
        serverClient.put("tasks/history", history);
        String prioritizedTask = gson.toJson(getPrioritizedTasks());
        serverClient.put("tasks", prioritizedTask);

    }

    public void read() throws IOException, InterruptedException {
        String gsonTasks = getServerClient().load("tasks/task");
        Type typeTask = new TypeToken<Map<Integer, Task>>() {
        }.getType();
        Map<Integer, Task> tasks = gson.fromJson(gsonTasks, typeTask);
        getTasksPerId().putAll(tasks);

        String gsonEpics = getServerClient().load("tasks/epic");
        Type typeEpic = new TypeToken<Map<Integer, Epic>>() {
        }.getType();
        Map<Integer, Epic> epics = gson.fromJson(gsonEpics, typeEpic);
        getEpicsPerId().putAll(epics);

        String gsonSubTasks = getServerClient().load("tasks/subtask");
        Type typeSubTask = new TypeToken<Map<Integer, SubTask>>() {
        }.getType();
        Map<Integer, SubTask> subtasks = gson.fromJson(gsonSubTasks, typeSubTask);
        getSubTasksPerId().putAll(subtasks);

        String gsonHistory = getServerClient().load("tasks/history");
        Type typeHistory = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> historyOfTasks = gson.fromJson(gsonHistory, typeHistory);
        getHistory().addAll(historyOfTasks);

        String prioritizedTasks = getServerClient().load("tasks");
        Type typePriorityTask = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> priorityTask = gson.fromJson(prioritizedTasks, typePriorityTask);
        getPrioritizedTasks().addAll(priorityTask);

    }


}*/
