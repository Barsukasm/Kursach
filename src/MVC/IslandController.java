package MVC;

import Data.model.Bunny;
import Data.model.FaunaCollection;
import Data.model.LiveBeing;
import Data.model.Wolf;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Vector;

public class IslandController {
        private IslandView view;
        private IslandModel model;


        //сязываем модель и вид
        public IslandController(IslandModel model, IslandView view){
            this.model=model;
            this.view=view;
            init();
        }

        //добавляем слушателей
        private void init(){
            view.startPause.addActionListener(startButtonListener);
            view.stop.addActionListener(stopButtonListener);
            view.window[0].addWindowListener(windowClose);
            view.pauseButton.addActionListener(pauseButton);
            view.saveItem.addActionListener(saveItemListener);
            view.loadItem.addActionListener(loadItemListener);
            view.exitItem.addActionListener(exitItemListener);
        }

        private ActionListener startButtonListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!model.isRunning){
                model.startSimulation();}
                view.pauseButton.setEnabled(true);
            }
        };

        private ActionListener stopButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.isRunning){
                    model.stopSimulation();
                }
            }
        };

        private ActionListener pauseButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.isRunning) {
                    if (!model.isPaused) {
                        model.pause();
                        view.pauseButton.setText("Resume");
                    } else {
                        model.resume();
                        view.pauseButton.setText("Pause");
                    }
                }
            }
        };

        private WindowAdapter windowClose = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(model.wolfsAI!=null) model.wolfsAI.running=false;
                if(model.bunnyAI!=null) model.bunnyAI.running=false;
                e.getWindow().setVisible(false);
                System.exit(0);
            }
        };

        private ActionListener saveItemListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileSave = new JFileChooser();
                int returnVal = fileSave.showSaveDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileSave.getSelectedFile().getPath()+".txt"));
                        oos.writeObject(FaunaCollection.getInstance().fauna); // Сериализация
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };

        private ActionListener loadItemListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.isRunning){
                    model.pause();
                }
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    model.stopSimulation();
                    File file = fileopen.getSelectedFile();
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                        try {
                            FaunaCollection.getInstance().fauna = (Vector<LiveBeing>) ois.readObject();
                        } catch (IOException | ClassNotFoundException ex) {}
                        for (LiveBeing lb:FaunaCollection.getInstance().fauna){
                            if(lb instanceof Bunny){
                                FaunaCollection.getInstance().fc[lb.getX()][lb.getY()].numberOfBunnies++;
                            }else{
                                Wolf wolf = (Wolf)lb;
                                if(wolf.isMale()){
                                    FaunaCollection.getInstance().fc[wolf.getX()][wolf.getY()].numberOfWolfsM++;
                                }else {
                                    FaunaCollection.getInstance().fc[wolf.getX()][wolf.getY()].numberOfWoflsF++;
                                }
                            }
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                model.isRunning=true;
                model.resume();
            }
        };

        private ActionListener exitItemListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.wolfsAI!=null) model.wolfsAI.running=false;
                if(model.bunnyAI!=null) model.bunnyAI.running=false;
                view.window[0].setVisible(false);
                System.exit(0);
            }
        };
}
