package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
}
