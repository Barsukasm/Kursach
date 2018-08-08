package MVC;

import Data.ai.BunnyAI;
import Data.ai.WolfsAI;
import Data.model.Bunny;
import Data.model.FaunaCollection;
import Data.model.LiveBeing;
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
    private static int nWolfs=15;
    private static int nBunnies=15;

    public IslandModel(IslandView view){
        this.view = view;
    }

    public void update(){
        synchronized (FaunaCollection.getInstance().fauna){
            FaunaCollection.getInstance().fauna.addAll(FaunaCollection.getInstance().addNewborns);
            FaunaCollection.getInstance().addNewborns.clear();
            FaunaCollection.getInstance().checkAndClean();
        }
    }

    private void spawnCreatures(){
       // synchronized (FaunaCollection.getInstance().fauna){
            int numberOfWolfs=(int)(Math.random()*nWolfs+1);
            int numberOfBunnies=(int)(Math.random()*nBunnies+1);
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
        spawnCreatures();
        wolfsAI=new WolfsAI();
        wolfsAI.start();
        bunnyAI = new BunnyAI();
        bunnyAI.start();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
                update();
                view.startSimulation(formMessage());
            }
        },0,1000);
    }


    public void stopSimulation(){
        isRunning=false;
        timer.cancel();
        timer.purge();
        wolfsAI.running=false;
        bunnyAI.running=false;
        FaunaCollection.getInstance().addNewborns.clear();
        FaunaCollection.getInstance().fauna.clear();
        view.stopSimulation();
    }

    private String formMessage(){
        int bunnies=0, wolfM=0, wolfF=0, wolfsT=0;
        for (LiveBeing lb: FaunaCollection.getInstance().fauna){
            if(lb instanceof Bunny) bunnies++;
            if(lb instanceof Wolf){
                wolfsT++;
                Wolf wolf = (Wolf)lb;
                if(wolf.isMale()){wolfM++;}else {wolfF++;}
            }
        }
        return "Population of rabbits: "+bunnies+"\n"+
                "Population of wolfs: "+wolfsT+"\n"+
                "Number of males: "+wolfM+"\n"+
                "Number of females: "+wolfF;
    }
}
