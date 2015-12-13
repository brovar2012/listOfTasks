import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dima on 30.11.2015.
 */

public class Message {
    JFrame frame;
    public Message (String message)
    {
        frame = new JFrame("Message");
        frame.setSize(300, 120);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);

        JLabel label = new JLabel(message);
        label.setBounds(20,10,280,30);
        JButton button = new JButton ("OK");
        button.setBounds(100,50,100,20);
        frame.add(label);
        frame.add(button);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });

    }
}
