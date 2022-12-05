package ui.profile;

import database.utils.BUGUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import ui.general.Window;

public class ProfileClassList extends JPanel {

    String[] classesDataDummy = {
            "CSI 3471",
            "WGS 2300"
    };

    Vector<String> classes = new Vector<>();

    JLabel header;

    JList<String> classList;

    JPanel buttons = new JPanel();

    DefaultListModel<String> model = new DefaultListModel<>();

    public ProfileClassList(){
        super();
        createAndDisplay();
    }

    void createAndDisplay() {
        setMinimumSize(new Dimension(100,100));
        addComponents();
        setVisible(true);
    }

    void addComponents() {
        // header label
        header = new JLabel("Current enrolled courses:");

        add(header);

        buildClassList();
        add(classList);

        buildAddRemoveButtons();
        add(buttons);

    }

    void buildAddRemoveButtons(){
        JButton add = new JButton("Add Class");
        JButton remove = new JButton("Remove Class");

        add.addActionListener(new AddActionListener());
        remove.addActionListener(new RemoveActionListener());

        buttons.add(add);
        buttons.add(remove);

        buttons.setSize(new Dimension(10, 20));
        buttons.setVisible(true);
    }

    void buildClassList(){
        Vector<Object> s = BUGUtils.controller.getStudentClasses(Window.username);
        for(int i = 0; i < s.size(); i++){
            classes.add(s.elementAt(i).toString().substring(0,3) + " " + s.elementAt(i).toString().substring(3,7));
        }

        if(!classes.isEmpty()) {
            model.addAll(classes);
        } else {
            model.addElement("No Current Classes!");
        }
        classList = new JList<>(model);
        classList.setSize(50,50);
        add(new JScrollPane(classList));
    }

    class AddActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddClassTutorDialog(model, "course");
        }
    }

    class RemoveActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(classList.getSelectedValue() != null) {
                int answer = JOptionPane
                        .showConfirmDialog(null,
                                "Do you want to remove " + classList.getSelectedValue() + "?",
                                "Warning", JOptionPane.YES_NO_OPTION);
                if (answer == 0) {
                    model.remove(classList.getSelectedIndex());
                }
            }
        }
    }

    public Vector<String> getNames(){
        return classes;
    }

}
