import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dima on 26.11.2015.
 */
public class AddWindow {

    private JButton dontSaveButton;
    protected JFrame frame;
    protected JTextField header;
    public JButton saveButton;
    protected JTextArea textOfTask;

    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";
    private JButton[] button = new JButton[49];

    public AddWindow () {
        frame = new JFrame("Add/Edit");
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel calendar = new JPanel ();

        calendar.setBounds(200,30,430,200);
        frame.add(calendar);

        saveButton = new JButton ("Save");
        dontSaveButton = new JButton ("Don't save");

        saveButton.setBounds(50,130,100,30);
        dontSaveButton.setBounds(50,180,100,30);

        JLabel textHeader = new JLabel("Header of task:");
        textHeader.setBounds(40,230,100,40);
        frame.add(textHeader);

        header = new  JTextField();
        header.setBackground(Color.WHITE);
        header.setBounds(40,270,600,30);
        frame.add(header);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });

        dontSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });

        frame.add(saveButton);
        frame.add(dontSaveButton);

        JLabel textOfLabelTask = new JLabel("Text of task:");
        textOfLabelTask.setBounds(40,310,100,40);
        frame.add(textOfLabelTask);

        textOfTask = new JTextArea();
        textOfTask.setBounds(40,350,600,160);
        textOfTask.setBackground(Color.WHITE);
        frame.add(textOfTask);

        String[] header1 = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        calendar.setBackground(Color.LIGHT_GRAY);
        p1.setPreferredSize(new Dimension(430, 120));

        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6) {
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        //button[selection].setBackground(Color.BLUE);
                    }
                });
            }
            if (x < 7) {
                button[x].setText(header1[x]);
                button[x].setEnabled(false);
            }
            p1.add(button[x]);
        }
        JPanel p2 = new JPanel(new GridLayout(1, 3));

        JButton previous = new JButton("<< Previous");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                for (int i =0; i < 49;i++)
                {
                    button[i].setBackground(Color.white);
                }
                month--;
                displayDate();
            }
        });
        p2.add(previous);

        p2.add(l);

        JButton next = new JButton("Next >>");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                for (int i =0; i < 49;i++)
                {
                    button[i].setBackground(Color.white);
                }
                month++;
                displayDate();
            }
        });
        p2.add(next);

        calendar.add(p1, BorderLayout.CENTER);
        calendar.add(p2, BorderLayout.SOUTH);
        calendar.setBackground(Color.LIGHT_GRAY);
        displayDate();


        frame.setVisible(true);
    }

    public void displayDate() {
        for (int x = 7; x < button.length; x++) {
            button[x].setText("");
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        for (int i =0; i < 49;i++)
        {
            button[i].setEnabled(false);
        }

        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++) {
            button[x].setEnabled(true);
            button[x].setText("" + day);
        }

        l.setText(sdf.format(cal.getTime()));
    }

    public String setPickedDate() {
        if (day.equals("")) {
            return day;
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }

    public String getHeaderOfTask () {

        return header.getText();
    }

    public String getTextOfTask () {

        return textOfTask.getText();
    }

}
