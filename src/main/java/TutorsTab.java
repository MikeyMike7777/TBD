import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class TutorsTab extends JPanel{
    static final String[] names = {"Select Subject", "Biology and " +
            "Health Sciences", "Business", "Chemistry and Biochemistry", "Education " +
            "and Social Work", "Engineering and Computer Science", "English and " +
            "Journalism", "Fine Arts", "Foreign Languages", "History and Political " +
            "Science", "Math and Physics", "Philosophy and BIC", "Psychology and " +
            "Sociology"};

    HashMap<String, Integer> boardKeys = new HashMap<>();
    HashMap<String, JPanel> subjects = new HashMap<>();
    JPanel currSubject = new JPanel();

    TutorsTab() {
        super();
        for (int i = 1; i < names.length; ++i) {
            boardKeys.put(names[i], i);
            subjects.put(names[i], new JPanel());
            subjects.get(names[i]).add(new JLabel(names[i]));
        }
        createAndDisplay();
    }

    void createAndDisplay() {
        setSize(new Dimension(600, 400));
        setAlignmentX(LEFT_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
        setVisible(true);
    }

    void addComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(addBoardMenu());
        add(panel);
    }

    Component addBoardMenu() {
        JMenuBar bar = new JMenuBar();
        bar.setPreferredSize(new Dimension(300, 30));
        JMenu select = new JMenu();
        select.setText("Select Subject");
        select.setAlignmentX(CENTER_ALIGNMENT);
        select.setPreferredSize(new Dimension(300, 30));
        JMenuItem[] boards = createBoardOptions();
        for (int i = 0; i < names.length; ++i)
            select.add(boards[i]);
        bar.add(select);
        return bar;
    }

    JMenuItem[] createBoardOptions() {
        JMenuItem[] items = new JMenuItem[names.length];
        for (int i = 0; i < names.length; ++i) {
            items[i] = new JMenuItem(names[i]);
            if (i > 0)
                items[i].addActionListener(new TutorsTab.MenuActionListener());
        }
        return items;
    }

    class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = ((JMenuItem)e.getSource()).getText();
            ((JMenu)((JPopupMenu)((JMenuItem)e.getSource()).getParent()).
                    getInvoker()).setText(s);
            currSubject = subjects.get(s);

            // adds panels-- theoretically i want it to replace them
            // (hide old one, show new one) but idk how to do that
            add(currSubject);
        }
    }
}


