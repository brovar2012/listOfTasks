/**
 * Created by Dima on 30.11.2015.
 */
public class EditWindow extends AddWindow {

    public EditWindow( Task task ) {
        if (!task.getName().isEmpty() ) {
            this.header.setText(task.getName());
            this.textOfTask.setText(task.getDescription());
        } else {
            frame.setVisible(false);
            new Message("Создайте дело прежде чем редактировать!");
        }
    }

}
