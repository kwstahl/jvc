package com.mycompany.app.fileprocessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui {
    public static void gui() {
        JFrame f = new JFrame("Label Example");
        JLabel l1, l2;
        JButton b = new JButton("Add Files Here");
        JButton b2 = new JButton("Combine Samples and Scans");

        l1 = new JLabel("First Label.");
        l1.setBounds(50, 50, 100, 30);

        l2 = new JLabel("Second Label.");
        l2.setBounds(50, 100, 100, 30);

        b.setBounds(50, 200, 200, 30);
        b.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser j = new JFileChooser("//app//");
                        j.showSaveDialog(null);
                    }
                });

        b2.setBounds(50, 250, 250, 30);

        f.add(l1);
        f.add(l2);
        f.add(b);
        f.add(b2);
        f.setSize(600, 500);
        f.setLayout(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser j = new JFileChooser();
        j.showSaveDialog(null);
    }

}
