package photonic;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.jtattoo.plaf.smart.SmartLookAndFeel;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Properties props = new Properties();
                props.put("windowTitleForegroundColor", "0 0 0");
                props.put("windowTitleBackgroundColor", "180 240 197");
                props.put("windowTitleColorDark", "180 240 197");
                SmartLookAndFeel.setCurrentTheme(props);
                UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");

                final GraphPlot window = new GraphPlot();
                window.frame.setVisible(true);

                window.frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");
                window.frame.getRootPane().getActionMap().put("clickButton", new AbstractAction() {

                    private static final long serialVersionUID = 1L;

                    public void actionPerformed(ActionEvent a) {
                        window.button.doClick();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}