package kanban.servers;

import kanban.managers.taskManagers.FileBackedTasksManager;
import kanban.managers.taskManagers.TasksManager;
import kanban.servers.handlers.HomepageHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import kanban.utils.Formatter;
import com.google.gson.Gson;
import kanban.tasks.Subtask;
import kanban.tasks.Epic;
import kanban.tasks.Task;

import java.nio.charset.StandardCharsets;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Oleg Khilko
 */

public class HttpTaskServer {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final int PORT = 8080;
    private final TasksManager manager;
    private final Gson gson;

    public HttpTaskServer(TasksManager manager) throws IOException {

        this.manager = manager;

        var httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);

        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/", new HomepageHandler());

        gson = Formatter.createGson();

        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }

    class TasksHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {

            System.out.println("Началась обработка /tasks запроса от клиента.\n");

            var path = exchange.getRequestURI().getPath();

            var method = exchange.getRequestMethod();

            byte[] response = new byte[0];

            int responseCode = 404;

            try {

                switch (method) {

                    case "GET":

                        if (Pattern.matches("^/tasks$", path)) {

                            var prioritizedTasksToJson = gson.toJson(manager.getPrioritizedTasks());

                            if (!prioritizedTasksToJson.isEmpty()) {

                                response = prioritizedTasksToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/task$", path)) {

                            var tasksToJson = gson.toJson(manager.getTasks());

                            if (!tasksToJson.isEmpty()) {

                                response = tasksToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/epic$", path)) {

                            var epicsToJson = gson.toJson(manager.getEpics());

                            if (!epicsToJson.isEmpty()) {

                                response = epicsToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/subtask$", path)) {

                            var subtasksToJson = gson.toJson(manager.getSubtasks());

                            if (!subtasksToJson.isEmpty()) {

                                response = subtasksToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/task/\\d$", path)) {

                            int id = Integer.parseInt(path.replaceFirst("/tasks/task/", ""));

                            var taskByIDToJson = gson.toJson(manager.getTasks().get(id));

                            if (!taskByIDToJson.isEmpty()) {

                                response = taskByIDToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/history$", path)) {

                            var historyToJson = gson.toJson(manager.getHistory());

                            if (!historyToJson.isEmpty()) {

                                response = historyToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }
                        }

                        if (Pattern.matches("^/tasks/subtask/epic/\\d+$", path)) {

                            int id = Integer.parseInt(path.replaceFirst("/tasks/subtask/epic/", ""));

                            var subtaskEpicToJson = gson.toJson(manager.getSubtasks().get(id));

                            if (!subtaskEpicToJson.isEmpty()) {

                                response = subtaskEpicToJson.getBytes(DEFAULT_CHARSET);

                                exchange.getResponseHeaders().add("Content-Type", "application/json");

                                responseCode = 200;

                            } else {

                                responseCode = 400;

                            }

                        }

                        break;

                    case "POST":

                        if (Pattern.matches("^/tasks/task$", path)) {

                            var inputStream = exchange.getRequestBody();
                            var body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            var task = gson.fromJson(body, Task.class);

                            responseCode = 200;

                            if (!manager.getTasks().containsKey(task.getId())) {

                                manager.createTask(task);
                                System.out.println("Задача #" + task.getId() + " создана.\n" + body);

                            } else {

                                manager.update(task);
                                System.out.println("Задача #" + task.getId() + " обновлена.\n" + body);

                            }

                        }

                        if (Pattern.matches("^/tasks/epic$", path)) {

                            var inputStream = exchange.getRequestBody();
                            var body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            var epic = gson.fromJson(body, Epic.class);

                            responseCode = 200;

                            if (!manager.getEpics().containsKey(epic.getId())) {

                                manager.createEpic(epic);
                                System.out.println("Задача #" + epic.getId() + " создана.\n" + body);

                            } else {

                                manager.update(epic);
                                System.out.println("Задача #" + epic.getId() + " обновлена.\n" + body);

                            }

                        }

                        if (Pattern.matches("^/tasks/subtask$", path)) {

                            var inputStream = exchange.getRequestBody();
                            var body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            var subtask = gson.fromJson(body, Subtask.class);

                            responseCode = 200;

                            if (!manager.getSubtasks().containsKey(subtask.getId())) {

                                manager.createSubtask(subtask);
                                System.out.println("Задача #" + subtask.getId() + " создана.\n" + body);

                            } else {

                                manager.update(subtask);
                                System.out.println("Задача #" + subtask.getId() + " создана.\n" + body);

                            }

                        }

                        break;

                    case "DELETE":

                        if (Pattern.matches("^/tasks/task$", path)) {

                            manager.removeAllTasksEpicsSubtasks();

                            System.out.println("Все таски, эпики и сабтаски удалены.");

                            responseCode = 200;

                        }

                        if (Pattern.matches("^/tasks/task/\\d+$", path)) {

                            int id = Integer.parseInt(path.replaceFirst("/tasks/task/", ""));

                            if (manager.getTasks().containsKey(id)) {

                                manager.removeTask(id);
                                System.out.println("Задача #" + id + " удалена.\n");

                            }

                            if (manager.getEpics().containsKey(id)) {

                                manager.removeEpic(id);
                                System.out.println("Задача #" + id + " удалена.\n");

                            }

                            if (manager.getSubtasks().containsKey(id)) {

                                manager.removeSubtask(id);
                                System.out.println("Задача #" + id + " удалена.\n");

                            }

                            responseCode = 200;

                        }

                        break;

                    default:

                        System.out.println("Метод " + method + " не поддерживается.");

                }

                exchange.sendResponseHeaders(responseCode, 0);

                try (OutputStream os = exchange.getResponseBody()) {

                    os.write(response);

                }

            } catch (IOException e) {

                System.out.println("Ошибка выполнения запроса: " + e.getMessage());

            } finally {

                exchange.close();

            }

        }

    }

    public static void main(String[] args) throws IOException{

        new HttpTaskServer(FileBackedTasksManager.load(Path.of("src/main/resources/results.csv")));

    }

}