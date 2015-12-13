import java.io.Serializable;

/**
 * Created by Dima on 09.12.2015.
 */
public class Task implements Serializable{

    private String date;//TODO: String->Date
    private String name;
    private String description;

    public Task () {

        date = "";
        name = "";
        description = "";
    }

    public Task (String date, String name, String description) {

        this.date = date;
        this.name = name;
        this.description = description;
    }

    public void setDate (String date) {

        this.date = date;

    }

    public void setName (String name) {

        this.name = name;
    }

    public void setDescription (String description) {

        this.description = description;

    }

    public String getDate (){

        return date;
    }

    public String getName (){

        return name;
    }

    public String getDescription () {

        return description;
    }

}
