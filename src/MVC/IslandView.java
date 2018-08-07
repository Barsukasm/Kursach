package MVC;


import Data.model.Bunny;
import Data.model.Wolf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class IslandView extends JFrame {

    JButton startPause;
    JButton stop;
    JPanel menuPanel;
    JPanel visualPanel;
    JPanel mainPanel;
    Wolf wolf;
    Bunny bunny;
    Image img;

    public IslandView(String s){
        super(s);
        setBounds(0,0,1000,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wolf = new Wolf(2,3,true);
        bunny = new Bunny(10,19);
        try{
            img = ImageIO.read(new File("images/grassTile1.png"));
        }catch (IOException ex){ex.printStackTrace();}

        add(mainPanel);
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        visualPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img,0,0,visualPanel.getWidth(),visualPanel.getHeight(),null);
                wolf.paint(g);
                bunny.paint(g);
            }
        };
    }
}
