
import javax.swing.*;
import java.awt.*;

public class DecisionForgeApp extends JFrame {

    public DecisionForgeApp() {
        setTitle("DecisionForgeAI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Welcome to DecisionForgeAI");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton courseButton = new JButton("CoursePath");
        JButton laptopButton = new JButton("LaptopWise");

        courseButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "CoursePath not available yet!"));

        laptopButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "LaptopWise not available yet!"));

        panel.add(title);
        panel.add(courseButton);
        panel.add(laptopButton);

        add(panel);
    }
}

