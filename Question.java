
import javax.swing.Timer;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Question extends JFrame {
    int score, unsolvedCount, solvedCount = 0;
    boolean inUnsolvedMode = false;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup grpbutton;
    JButton saveButton, skipButton, unsolvedButton, submitButton, jumpToCurrentButton;
    int currentQuestionIndex = 0;
    JLabel questionLabel, qno, timerLabel;
    ArrayList<String[]> questions = new ArrayList();
    ArrayList<String[]> answers = new ArrayList();
    ArrayList<Integer> unsolvedIndexes = new ArrayList();
    ArrayList<String[]> unsolvedQuestions = new ArrayList<>();
    ArrayList<String[]> unsolvedAnswers = new ArrayList();
    boolean lastQuestionSolved, isunsubmitButton = false;
    private Timer timer;
    private int remainingTime = 40; // Timer starts at 40 seconds

    public Question() {
        setTitle("Qusetions");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setMaximumSize(getMaximumSize());
        JLabel headerL = new JLabel("QUIZ APPLICATION");
        headerL.setFont(new Font("Britannic Bold", Font.BOLD, 36));
        headerL.setForeground(Color.WHITE);
        JPanel headerP = new JPanel();
        headerP.add(headerL, BorderLayout.CENTER);
        headerP.setBackground(Color.BLUE);
        add(headerP, BorderLayout.NORTH);
        JLabel footerL = new JLabel("Powered by Faizan");
        footerL.setFont(new Font("Agency FB", Font.BOLD, 26));
        footerL.setForeground(Color.WHITE);
        JPanel footerP = new JPanel();
        footerP.add(footerL, BorderLayout.EAST);
        footerP.setBackground(Color.BLUE);
        add(footerP, BorderLayout.SOUTH);
        questions.add(new String[] { "Your name starts with which letter?", "N", "F", "M", "B" });
        questions.add(new String[] { "OOP is known for what features?", "Polymorphism", "Inheritance", "Both A and B",
                "None of them" });
        questions.add(new String[] { "Who invented Java?", "James Gosling", "Mark", "Bill Gates", "Elon Musk" });
        questions.add(new String[] { "What is the old name of java?", "oak", "php", "csharp", "None of them" });
        questions.add(new String[] { "Which programming language currently you are learning?", "C++", "JavaScript",
                "Java", "All of them" });
        answers.add(new String[] { "F", "", "", "", "" });
        answers.add(new String[] { "Both A and B", "", "", "", "" });
        answers.add(new String[] { "James Gosling", "", "", "", "" });
        answers.add(new String[] { "oak", "", "", "", "" });
        answers.add(new String[] { "Java", "", "", "", "" });

        timerLabel = new JLabel("Timer: 30 seconds");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.RED);
        JPanel timerPanel = new JPanel();
        timerPanel.add(timerLabel, BorderLayout.CENTER);
        add(timerPanel, BorderLayout.EAST);

        saveButton = new JButton("Save/Next");
        skipButton = new JButton("Skip");
        unsolvedButton = new JButton("Unsolved");
        submitButton = new JButton("Submit");
        jumpToCurrentButton = new JButton(">");
        jumpToCurrentButton.setBackground(Color.CYAN);
        jumpToCurrentButton.setVisible(false);
        saveButton.setBackground(Color.CYAN);
        skipButton.setBackground(Color.CYAN);
        unsolvedButton.setBackground(Color.CYAN);
        submitButton.setBackground(Color.CYAN);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 0));
        buttonPanel.add(jumpToCurrentButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(skipButton);
        buttonPanel.add(unsolvedButton);
        buttonPanel.add(submitButton);

        JPanel radioPanel = new JPanel();
        grpbutton = new ButtonGroup();
        opt1 = new JRadioButton();
        opt2 = new JRadioButton();
        opt3 = new JRadioButton();
        opt4 = new JRadioButton();
        grpbutton.add(opt1);
        grpbutton.add(opt2);
        grpbutton.add(opt3);
        grpbutton.add(opt4);
        radioPanel.add(opt1);
        radioPanel.add(opt2);
        radioPanel.add(opt3);
        radioPanel.add(opt4);
        radioPanel.setLayout(new GridLayout(4, 0));

        qno = new JLabel();
        questionLabel = new JLabel("");
        JPanel questionPanel = new JPanel();

        questionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        questionPanel.add(qno, BorderLayout.EAST);
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        JPanel main = new JPanel(new BorderLayout());
        main.add(buttonPanel, BorderLayout.SOUTH);
        main.add(questionPanel, BorderLayout.NORTH);
        main.add(radioPanel, BorderLayout.WEST);

        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(main, BorderLayout.CENTER);

        start(0);
        setVisible(true);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == submitButton) {
                    timer.stop(); // Stop the timer
                    score();
                    setVisible(false);
                } else if (e.getSource() == saveButton) {
                    try {
                        timer.stop(); // Stop the timer
                        checkAnswer(currentQuestionIndex); // Check answer for the current question
                        check_if_in_unsolvedSection(); // Check and move to the next question
                        if (currentQuestionIndex < questions.size()) {
                            start(currentQuestionIndex); // Start the next question
                        } else {
                            JOptionPane.showMessageDialog(Question.this, "No more questions available.");
                        }
                    } catch (Exception b) {
                        System.out.println("Error in the save section: " + b.getMessage());
                        b.printStackTrace();
                    }
                } else if (e.getSource() == skipButton) {
                    try {
                        skippedQuestion(currentQuestionIndex);
                        check_if_in_unsolvedSection();
                        if (currentQuestionIndex < questions.size()) {
                            start(currentQuestionIndex);
                        } else {
                            JOptionPane.showMessageDialog(Question.this, "No More question Available");
                        }
                    } catch (Exception c) {
                        System.out.println("Error in the skipped section");
                    }
                } else if (e.getSource() == unsolvedButton) {
                    try {
                        if (solvedCount == questions.size()) {
                            jumpToCurrentButton.setEnabled(false);
                        }

                        if (!unsolvedIndexes.isEmpty()) {
                            isunsubmitButton = true;
                            currentQuestionIndex = unsolvedIndexes.get(unsolvedCount);
                            jumpToCurrentButton.setVisible(true);
                            start(currentQuestionIndex);
                        } else {
                            JOptionPane.showMessageDialog(Question.this, "No Skipped Question");
                        }

                    } catch (Exception d) {
                        System.out.println("Error in the unsolved section");
                    }
                } else if (e.getSource() == jumpToCurrentButton) {

                    isunsubmitButton = false;
                    currentQuestionIndex = solvedCount;
                    jumpToCurrentButton.setVisible(false);
                    // currentQuestionIndex = solvedCount;
                    start(currentQuestionIndex);

                }
            }
        };
        submitButton.addActionListener(actionListener);
        skipButton.addActionListener(actionListener);
        unsolvedButton.addActionListener(actionListener);
        saveButton.addActionListener(actionListener);
        jumpToCurrentButton.addActionListener(actionListener);
    }

    public void start(int count) {
        String[] currentQuestion = questions.get(count);
        if (currentQuestion != null) {
            grpbutton.clearSelection();
            qno.setText("Q" + (count + 1) + ": ");
            questionLabel.setText(currentQuestion[0]); // Display the question text

            // Display the options in radio buttons
            opt1.setText(currentQuestion[1]);
            opt2.setText(currentQuestion[2]);
            opt3.setText(currentQuestion[3]);
            opt4.setText(currentQuestion[4]);

            // Reset and start the timer
            resetTimer();
            startTimer();
        } else {
            System.out.println("All questions have been attempted");
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        remainingTime = 30;
        timerLabel.setText("Timer: " + remainingTime + " seconds");
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                // updateTimerLabel();
                timerLabel.setText("Timer: " + remainingTime + " seconds");
                if (remainingTime <= 0) {
                    timer.stop();
                    check_if_in_unsolvedSection();
                    if (currentQuestionIndex < questions.size()) {
                        start(currentQuestionIndex);
                    } else {
                        JOptionPane.showMessageDialog(Question.this, "No More questions Available");
                    }
                }
            }
        });
        timer.start();
    }

    public void check_if_in_unsolvedSection() {
        if (isunsubmitButton) {
            unsolvedCount++;

            if (unsolvedCount < unsolvedIndexes.size()) {
                currentQuestionIndex = unsolvedIndexes.get(unsolvedCount);
            } else {
                JOptionPane.showMessageDialog(Question.this, "All unsolved questions have been answered.");
                isunsubmitButton = false;
                jumpToCurrentButton.setVisible(false);

                // Ensure we skip any solved questions after switching from unsolved mode
                while (currentQuestionIndex < questions.size() && solvedCount > currentQuestionIndex) {
                    currentQuestionIndex++;
                }
                // Set currentQuestionIndex to the first unsolved question
                currentQuestionIndex = solvedCount;

                if (currentQuestionIndex < questions.size()) {
                    start(currentQuestionIndex);
                } else {
                    JOptionPane.showMessageDialog(Question.this, "No More Questions");
                }
            }
        } else {
            solvedCount++;
            currentQuestionIndex++;
        }
    }

    public void checkAnswer(int index) {
        String[] currentAnswer = answers.get(index);
        String correctAnswer = currentAnswer[0];
        String selectedAnswer = null;
        if (opt1.isSelected()) {
            selectedAnswer = opt1.getText();
        } else if (opt2.isSelected()) {
            selectedAnswer = opt2.getText();
        } else if (opt3.isSelected()) {
            selectedAnswer = opt3.getText();
        } else if (opt4.isSelected()) {
            selectedAnswer = opt4.getText();
        }
        if (selectedAnswer != null && correctAnswer.equals(selectedAnswer)) {
            score++;
        }
    }

    public void skippedQuestion(int index) {
        // adding skipped mcq into new array list
        if (index >= 0 && index < questions.size()) {
            unsolvedIndexes.add(index);
            unsolvedQuestions.add(questions.get(index));
            unsolvedAnswers.add(answers.get(index));

        }
    }

    public void score() {

        try {
            String username = Login.userNameTF.getText();

            FileWriter writer = new FileWriter("scores.txt", true);
            writer.write(username + " --- " + score + "\n");
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }
    }

}
