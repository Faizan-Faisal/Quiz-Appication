import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Instruction extends JFrame {
    public Instruction() {
        // Set custom colors
        Color backgroundColor = new Color(240, 240, 240);
        Color headerFooterColor = new Color(65, 105, 225); // Royal Blue
        Color textColor = Color.WHITE;

        // Header
        JLabel headerL = new JLabel("QUIZ APPLICATION");
        headerL.setFont(new Font("Britannic Bold", Font.BOLD, 36));
        headerL.setForeground(textColor);
        JPanel headerP = new JPanel();
        headerP.add(headerL);
        headerP.setBackground(headerFooterColor);

        // Footer
        JLabel footerL = new JLabel("Powered by Faizan");
        footerL.setFont(new Font("Agency FB", Font.BOLD, 26));
        footerL.setForeground(textColor);
        JPanel footerP = new JPanel();
        footerP.add(footerL);
        footerP.setBackground(headerFooterColor);

        JLabel title = new JLabel("Instructions");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(headerFooterColor);

        JLabel instr = new JLabel(
                "<html>1. Read all MCQs Carefully.<br><br>2. Each MCQ will be displayed for 30 seconds. <br><br>3. You can skip MCQs to solve them later. <br><br>4. Each MCQ is worth 1 mark. <br><br>5. At the end, press the submit button to submit your quiz.<br><br>6. By clicking Start, Quiz will be Start.</html>");
        instr.setFont(new Font("Arial", Font.PLAIN, 18));

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(backgroundColor);
        main.add(title, BorderLayout.NORTH);
        main.add(instr, BorderLayout.CENTER);

        JButton button = new JButton("Start");
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLUE);
        JPanel eastP = new JPanel();
        eastP.setLayout(new FlowLayout(FlowLayout.RIGHT));
        eastP.add(button, BorderLayout.EAST);
        // Set margins for main panel
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add components to JFrame
        main.add(eastP, BorderLayout.SOUTH);
        getContentPane().setBackground(backgroundColor);
        add(headerP, BorderLayout.NORTH);
        add(main, BorderLayout.CENTER);
        add(footerP, BorderLayout.SOUTH);

        setTitle("Instructions");
        setSize(540, 530);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setVisible(true);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                new Question();
            }
        };
        button.addActionListener(actionListener);
    }
}