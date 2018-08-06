package MVC;

import javax.swing.*;
import java.awt.*;

public class IslandView extends JFrame {

    JButton startPause;
    JButton stop;
    JPanel menuPanel;
    JPanel visualPanel;
    JPanel mainPanel;

    public IslandView(){
        super();
        setBounds(0,0,1000,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        setVisible(true);
    }

}
