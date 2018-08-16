package MVC;


import Data.model.Bunny;
import Data.model.FaunaCollection;
import Data.model.Wolf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class IslandView extends JFrame {

    ClassLoader classLoader = getClass().getClassLoader();
    JMenuBar jbar=new JMenuBar();
    JMenu menu = new JMenu("File");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem exitItem = new JMenuItem("Exit");
    JButton startPause;
    JButton stop;
    JPanel menuPanel;
    JPanel visualPanel;
    JPanel mainPanel;
    public JTextArea infographics;
    public JButton pauseButton;
    public JTextField initRabbits;
    public JTextField initMales;
    public JTextField initFemales;
    public JTextField initPeriod;
    Window[] window = getWindows();
    Image img;

    public IslandView(String s){
        super(s);
        setBounds(150,150,1000,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initRabbits.setText("10");
        initMales.setText("5");
        initFemales.setText("5");
        initPeriod.setText("3000");

        menu.add(saveItem);
        menu.add(loadItem);
        menu.addSeparator();
        menu.add(exitItem);
        jbar.add(menu);
        setJMenuBar(jbar);

        try{
            img = ImageIO.read(classLoader.getResourceAsStream("images/grassTile1.png"));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        stop.setEnabled(false);

        infographics.setEditable(false);
        add(mainPanel);
        setVisible(true);
    }


    public void startSimulation(String msg){
        startPause.setEnabled(false);
        stop.setEnabled(true);
        pauseButton.setEnabled(true);
        pauseButton.setText("Pause");
        infographics.setText(msg);
        repaint();
    }

    public void stopSimulation(){
        startPause.setEnabled(true);
        pauseButton.setEnabled(false);
        pauseButton.setText("Pause");
        stop.setEnabled(false);
        infographics.setText("");
        repaint();
    }

    private void createUIComponents() {
        visualPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img,0,0,visualPanel.getWidth(),visualPanel.getHeight(),null);
                /*for (LiveBeing lb: FaunaCollection.getInstance().fauna){
                    lb.paint(g);
                }*/
                for (int i=0;i<20;i++){
                    for(int j=0;j<20;j++){
                        if(FaunaCollection.getInstance().fc[i][j].numberOfBunnies>0){
                            Bunny.getInstance(i,j).paint(g, classLoader);
                        }
                        if(FaunaCollection.getInstance().fc[i][j].numberOfWolfsM>0||FaunaCollection.getInstance().fc[i][j].numberOfWoflsF>0){
                            Wolf.getInstance(i,j).paint(g,classLoader);
                        }
                    }
                }
            }
        };
    }
}
