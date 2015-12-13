import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 29.11.2015.
 */

//TODO: add entity Task with fields Date, Name, Description

public class TasksModel implements Serializable  {

    private ArrayList<Task> listOfTasks;//TODO: store there Task, not files //TODO: ->tasks

    public TasksModel () {

        listOfTasks = new ArrayList<Task>();
    }

    public Task get (String dateOfTask) {
        for (int i = 0; i < listOfTasks.size(); i++) {
            if ((listOfTasks.get(i).getDate()).compareTo(dateOfTask) == 0)
                return listOfTasks.get(i);
        }
        return new Task();
    }

    public void set (Task newTask) {

        listOfTasks.add(newTask);
    }

    public void delete (Task task) {

        for (int i = 0; i < listOfTasks.size(); i++) {
            if ((listOfTasks.get(i).getName()).compareTo(task.getName()) == 0)
                listOfTasks.remove(i);
        }
    }

}
