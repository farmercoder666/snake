package com.example.snake;

import javax.swing.*;

public class Snake {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setBounds(150,0,900,670);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakePanel panel = new SnakePanel();
        frame.add(panel);

        frame.setVisible(true);
    }
}
