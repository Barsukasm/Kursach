package MVC;

import Data.ai.BunnyAI;
import Data.ai.WolfsAI;
import Data.model.Bunny;
import Data.model.FaunaCollection;
import Data.model.Wolf;

import java.util.Timer;
import java.util.TimerTask;

public class IslandModel {

    private Timer timer;
    public boolean isRunning=false;
    private long time;
    public BunnyAI bunnyAI;
    public WolfsAI wolfsAI;
    IslandView view;
    private static int nWolfs=10;
    private static int nBunnies=20;

    public IslandModel(IslandView view){
        this.view = view;
    }

    public void update(){
       // synchronized (FaunaCollection.getInstance().fauna){
            FaunaCollection.getInstance().checkAndClean();
            /*for (int i=0; i<20;i++){ //Размножение перенесено в класс Wolf
                for (int j=0; j<20;j++){
                    if (FaunaCollection.getInstance().island[i][j]>1){
                        FaunaCollection.getInstance().checkPairs(i,j);
                    }
                }
            }*/
       // }
    }

    private void spawnCreatures(){
       // synchronized (FaunaCollection.getInstance().fauna){
            int numberOfWolfs=(int)(Math.random()*nWolfs);
            int numberOfBunnies=(int)(Math.random()*nBunnies);
            for (int i=0;i<numberOfWolfs;i++){
                boolean isMale=true;
                if(Math.random()>0.5) isMale=false;
                Wolf wolfy = new Wolf((int)(Math.random()*20),(int)(Math.random()*20),isMale);
                FaunaCollection.getInstance().fauna.add(wolfy);
            }
            for (int i=0;i<numberOfBunnies;i++){
                Bunny bunny = new Bunny((int)(Math.random()*20),(int)(Math.random()*20));
                FaunaCollection.getInstance().fauna.add(bunny);
            }
       // }
    }

    void startSimulation(){
        isRunning=true;
        wolfsAI=new WolfsAI();
        wolfsAI.start();
        bunnyAI = new BunnyAI();
        bunnyAI.start();
        spawnCreatures();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
                update();
                view.startSimulation();
            }
        },0,1000);
    }


    public void stopSimulation(){
        isRunning=false;
        timer.cancel();
        timer.purge();
        wolfsAI.running=false;
        bunnyAI.running=false;
        view.stopSimulation();
        FaunaCollection.getInstance().fauna.clear();
    }
}
