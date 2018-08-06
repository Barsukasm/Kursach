package MVC;

import Data.ai.BunnyAI;
import Data.ai.WolfsAI;
import Data.model.FaunaCollection;

import java.util.Timer;
import java.util.TimerTask;

public class IslandModel {

    private Timer timer;
    private static long time = 0;
    private int island[][];
    public BunnyAI bunnyAI = new BunnyAI();
    public WolfsAI wolfsAI = new WolfsAI();
    IslandView view;


    public IslandModel(IslandView view){
        this.view = view;
        island = new int[20][20];
        for (int i=0; i<island.length;i++){
            for (int j=0; j<island.length;j++){
                island[i][j]=0;
            }
        }
        wolfsAI.start();
        bunnyAI.start();
    }

    public void update(){
        synchronized (FaunaCollection.getInstance().fauna){
            FaunaCollection.getInstance().checkAndClean();
            for (int i=0; i<20;i++){
                for (int j=0; j<20;j++){
                    if (island[i][j]>1){
                        FaunaCollection.getInstance().checkPairs(i,j);
                    }
                }
            }
        }
    }

    void startSimulation(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        },0,10);
    }

    public void stopSimulation(){
        timer.cancel();
        timer.purge();
    }
}
