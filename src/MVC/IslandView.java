package MVC;


import Data.model.Bunny;
import Data.model.FaunaCollection;
import Data.model.LiveBeing;
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
    Image img;

    public IslandView(String s){
        super(s);
        setBounds(0,0,1000,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            img = ImageIO.read(new File("images/grassTile1.png"));
        }catch (IOException ex){ex.printStackTrace();}
        stop.setEnabled(false);
        add(mainPanel);
        setVisible(true);
    }


    public void startSimulation(){
        startPause.setEnabled(false);
        stop.setEnabled(true);
        repaint();
    }

    public void stopSimulation(){
        startPause.setEnabled(true);
        stop.setEnabled(false);
        repaint();
    }

    private void createUIComponents() {
        visualPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img,0,0,visualPanel.getWidth(),visualPanel.getHeight(),null);
                for (LiveBeing lb: FaunaCollection.getInstance().fauna){
                    lb.paint(g);
                }
            }
        };
    }
}
