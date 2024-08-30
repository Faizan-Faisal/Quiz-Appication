import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Login extends JFrame {

    static JTextField userNameTF;
    static JTextField passwordTF;
    JButton LoginB;

    public Login(ArrayList<Student> data) {
        setBackground(Color.WHITE);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 390);
        // Header
        JLabel headerL = new JLabel("QUIZ APPLICATION");
        headerL.setFont(new Font("Britannic Bold", Font.BOLD, 36));
        headerL.setForeground(Color.WHITE);
        JPanel headerP = new JPanel();
        headerP.add(headerL, BorderLayout.CENTER);
        headerP.setBackground(Color.BLUE);
        add(headerP, BorderLayout.NORTH);
        // Footer
        JLabel footerL = new JLabel("Powered by Faizan");
        footerL.setFont(new Font("Agency FB", Font.BOLD, 26));
        footerL.setForeground(Color.WHITE);
        JPanel footerP = new JPanel();
        footerP.add(footerL, BorderLayout.EAST);
        footerP.setBackground(Color.BLUE);
        add(footerP, BorderLayout.SOUTH);
        // Main
        JPanel main = new JPanel(); // Panels
        JPanel p1 = new JPanel(); // for image
        JPanel p = new JPanel(); // to add all the other p panels
        p.setSize(300, 300);
        JPanel p2 = new JPanel(); // for title
        JPanel p3 = new JPanel(); // for username
        JPanel p4 = new JPanel(); // for password
        JPanel p5 = new JPanel(); // for login button
        JPanel p6 = new JPanel(); // for error label
        JLabel errorL = new JLabel("Invalid UserName or Password!");
        errorL.setForeground(Color.RED);
        p6.add(errorL);
        JLabel titleL = new JLabel("Login to Start"); // Labels, TextFields and Button.
        titleL.setForeground(Color.BLUE);
        titleL.setFont(new Font("Bahnschrift SemiBold Condensed", Font.LAYOUT_LEFT_TO_RIGHT, 30));
        JLabel userNameL = new JLabel("User Name");
        JLabel passwordL = new JLabel("Password ");
        userNameTF = new JTextField(10);
        passwordTF = new JTextField(10);
        LoginB = new JButton("Login");
        LoginB.setBackground(Color.BLUE);
        LoginB.setForeground(Color.WHITE);
        // Image
        ImageIcon i1 = new ImageIcon("icons/logo2.png");

        JLabel image1 = new JLabel(i1);
        image1.setBounds(0, 50, 60, 60);
        p1.add(image1, BorderLayout.WEST);
        p1.setSize(50, 50);
        p2.add(titleL);
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));

        p3.add(userNameL);
        p3.add(userNameTF);
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));

        p4.add(passwordL);
        p4.add(passwordTF);
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));

        p5.add(LoginB);
        p5.setLayout(new FlowLayout(FlowLayout.CENTER));
        p6.setVisible(false);
        p.add(p2);
        p.add(p6);
        p.add(p3);
        p.add(p4);
        p.add(p5);
        p.setLayout(new GridLayout(5, 0, 0, 2));
        main.add(p1, BorderLayout.WEST);
        main.add(p, BorderLayout.EAST);
        add(main);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameTF.getText();
                String password = passwordTF.getText();
                boolean studentFound = false;
                for (Student studentData : data) {
                    if (userName.equals(studentData.getName()) && password.equals(studentData.getPassword())) {
                        // System.out.println("Login Successfully");
                        setVisible(false);
                        new Instruction();
                        studentFound = true;
                    }

                }
                if (!studentFound) {
                    // System.out.println("Invalid Student Information");
                    p6.setVisible(true);
                    p6.revalidate();
                }
            }
        };
        LoginB.addActionListener(actionListener);
        setVisible(true);
    }

    public static void main(String[] args) {

        ArrayList<Student> data = new ArrayList<>();
        // new Login(data);
        data.add(new Student("Hassan", "123"));
        data.add(new Student("Faizan", "456"));
        data.add(new Student("Husnain", "789"));
        new Login(data);

    }

}
