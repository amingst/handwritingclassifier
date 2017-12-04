package application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//TODO:
    // Change the directions area location

public class App {
    public static JTextArea title;
    JButton clearButton, runButton;
    DrawingPanel drawingPanel = new DrawingPanel();
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                drawingPanel.clear();
            } else if (e.getSource() == runButton) {
                drawingPanel.run();
            }
        }
    };

    public static void main(String[] args) {
        new App().render();
    }

    public void render() {
        JFrame frame = new JFrame("Handwriting Classifier");
        JPanel buttons = new JPanel();
        Container container = frame.getContentPane();
        //Container container2 = frame.getContentPane();
        title = new JTextArea();
        title.append("Draw a number from 1 to 9 and press run");

        JPanel test = new JPanel();
        test.setLayout(new GridLayout(7, 1));
        test.setBackground(Color.darkGray);

        container.setLayout(new GridLayout(1, 1));
        container.setBackground(Color.DARK_GRAY);
        buttons.setBackground(Color.darkGray);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(actionListener);
        buttons.add(clearButton);

        runButton = new JButton("Run");
        runButton.addActionListener(actionListener);
        buttons.add(runButton);

        container.add(drawingPanel);
        test.add(title);
        test.add(new JPanel());
        test.add(new JPanel());
        test.add(new JPanel());
        test.add(new JPanel());
        test.add(runButton);
        test.add(clearButton);
        container.add(test);

        frame.pack();
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
