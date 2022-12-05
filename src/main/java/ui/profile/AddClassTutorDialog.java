package ui.profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import database.student.Student;
import database.utils.BUGUtils;
import  ui.general.Window;

public class AddClassTutorDialog extends JDialog {

    JLabel codeLabel;

    JLabel sectionLabel;

    JLabel professorLabel;

    JPanel textPanel = new JPanel();

    JPanel buttonPanel = new JPanel();

    DefaultListModel<String> List;

    JTextField classCode = new JTextField(20);

    JTextField section = new JTextField(20);

    JTextField professor = new JTextField(20);


    AddClassTutorDialog(DefaultListModel<String> model) {
        super();
        List = model;

        setSize(300,350);
        createAndDisplay();
    }

    void createAndDisplay() {
        setLayout(new GridLayout(2,2));

        buildTextPanel();
        add(textPanel);

        buildSaveCancel();
        add(buttonPanel);

        setVisible(true);
    }

    void buildSaveCancel(){
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        cancel.addActionListener(new CancelActionListener());
        save.addActionListener(new SaveActionListener());

        buttonPanel.add(save);
        buttonPanel.add(cancel);

        buttonPanel.setVisible(true);
    }

    void buildTextPanel() {
        String s;
        codeLabel = new JLabel("Enter Course Code:");
        sectionLabel = new JLabel("Enter Section:");
        professorLabel = new JLabel("Enter Professor:");
        textPanel.add(codeLabel);
        textPanel.add(classCode);
        textPanel.add(sectionLabel);
        textPanel.add(section);
        textPanel.add(professorLabel);
        textPanel.add(professor);
        textPanel.setVisible(true);
    }

    class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String normalized = classCode.getText().substring(0,4) + " " + classCode.getText().substring(classCode.getText().length() - 4, classCode.getText().length());
            BUGUtils.controller.addCourse(Window.username, classCode.getText(), professor.getText(), Integer.parseInt(section.getText()));
            List.addElement(normalized);
            dispose();
        }
    }

    class CancelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

}
