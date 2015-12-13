import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Dima on 26.11.2015.
 */
public class MainWindow {

    private int month;
    private int year;
    private String day;
    private JButton[] button = new JButton[49];

    private JTextArea list;

    private  AddWindow addWindow;
    private  EditWindow editWindow;

    Controller controller;

    public MainWindow() throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame("List of tasks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(780, 550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        ImageIcon imageIcon = new ImageIcon("src//1.jpg");
        JLabel label = new JLabel(imageIcon);
        label.setBackground(Color.LIGHT_GRAY);
        label.setBounds(20, 20, 200, 200);
        frame.add(label);

        JButton addButton = new JButton("Add");
        final JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addTask ();
            }
        }));

        editButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  editTask();
            }
        }));

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTask ();
            }
        });

        addButton.setBounds(360,80,100,40);
        editButton.setBounds(470,80,100,40);
        deleteButton.setBounds(580,80,100,40);

        frame.add(addButton);
        frame.add(editButton);
        frame.add(deleteButton);

        list = new JTextArea();
        list.setEnabled(false);
        list.setBounds(20, 250, 250, 240);
        list.setBackground(Color.BLACK);

        frame.add(list);

        JPanel calendar = new JPanel ();

        String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setBackground(Color.LIGHT_GRAY);
        calendar.setBackground(Color.LIGHT_GRAY);
        p1.setPreferredSize(new Dimension(430, 120));


        month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        JLabel l = new JLabel("", JLabel.CENTER);
        day = "";

        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6) {
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                       // button[selection].setBackground(Color.RED);
                    }
                });

            }
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setEnabled(false);
            }
            p1.add(button[x]);
        }

        JPanel p2 = new JPanel(new GridLayout(1, 3));
        p2.setBackground(Color.LIGHT_GRAY);

        JButton previous = new JButton("<< Previous");

        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                for (int i =0; i < 49;i++)
                    button[i].setBackground(Color.white);
                month--;
                displayDate();
            }
        });

        for ( int i =0; i<button.length; i++) {
                button[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Thread thread = new Thread();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                    showListOfTask(setPickedDate());
                            }
                        }).start();
                    }
                });
            }

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
        displayDate();

        calendar.setBounds(300,250,430,200);
        frame.add(calendar);

        frame.setVisible(true);
        controller = new Controller();
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

        JLabel l = new JLabel("", JLabel.CENTER);

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

    public void showListOfTask ( final String date ) {

        Thread thread = new Thread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if ((controller.getTask(date).getDate().isEmpty()))
                    list.setText(date + "\n" + "Нет задач на сегодня");

                else
                    list.setText(date + "\n"
                                    + controller.getTask(date).getName() + "\n"
                                    + controller.getTask(date).getDescription()
                    );
            }
        }).start();
    }

    public void addTask () {
        if (setPickedDate().isEmpty()) {
            new Message("Выберите дату для добавления задачи!");
            return;
        }
        addWindow = new AddWindow();
        addWindow.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addWindow.setPickedDate().isEmpty()) {
                    try {
                        controller.add( new Task(setPickedDate(),addWindow.getHeaderOfTask(),addWindow.getTextOfTask()) );
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    showListOfTask(setPickedDate());
                }
                else
                {
                    try {
                        controller.add(new Task(addWindow.setPickedDate(),addWindow.getHeaderOfTask(),addWindow.getTextOfTask()) );
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    showListOfTask(addWindow.setPickedDate());
                }
            }
        });
    }

    public void editTask () {
        if ((controller.getTask(setPickedDate()).getName().isEmpty())) {

            new Message("Нет дел для редактирования в этот день!");
            return;
        }
        editWindow = new EditWindow(controller.getTask(setPickedDate()));
        editWindow.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (editWindow.setPickedDate().isEmpty()) {
                    try {
                        controller.add(new Task(setPickedDate(), editWindow.getHeaderOfTask(), editWindow.getTextOfTask()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    showListOfTask(setPickedDate());
                } else {
                    try {
                        controller.add(new Task(editWindow.setPickedDate(), editWindow.getHeaderOfTask(), editWindow.getTextOfTask()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    showListOfTask(setPickedDate());
                }
            }
        });
    }

    public void deleteTask () {
        if (setPickedDate().isEmpty() ) {
            new Message("Выберите день для удаления!");
            return;
        }
        Thread thread = new Thread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    controller.remove(setPickedDate());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException a) {
            return;
        }
        showListOfTask(setPickedDate());


    }

}
