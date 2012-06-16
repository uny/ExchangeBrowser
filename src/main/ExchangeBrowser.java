package main;

import javax.swing.JFrame;


public class ExchangeBrowser {
    public static void main(String[] args) {
        ExchangeFrame frame = new ExchangeFrame("resources/data.csv");
        frame.setTitle("Sample");
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
