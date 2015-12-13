import java.io.IOException;

/**
 * Created by Dima on 04.12.2015.
 */

public class Controller {

    private TasksModel tasksModel;

    public Controller () throws IOException, ClassNotFoundException {

        tasksModel = TasksDataAccessService.LoadTasks();
    }

    public void add (Task task) throws IOException, ClassNotFoundException {

        if (tasksModel.get(task.getDate()).getDate().isEmpty()) {
            tasksModel.set(task);
        }
        else
        {
            remove(task.getDate());
            tasksModel.set(task);
        }

        TasksDataAccessService.SaveTasks(tasksModel);
    }

    public void remove(String date) throws IOException, ClassNotFoundException {

        if (!tasksModel.get(date).getName().isEmpty()) {
            tasksModel.delete(tasksModel.get(date));
            TasksDataAccessService.SaveTasks(tasksModel);
        }
    }

    public Task getTask (String dateOfTask) {

        return tasksModel.get(dateOfTask);

    }

}
