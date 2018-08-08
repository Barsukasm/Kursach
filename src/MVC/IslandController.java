package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        }

        private ActionListener startButtonListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!model.isRunning){
                model.startSimulation();}

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
}
