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

    public static int period=3000;

    private Timer timer;
    public boolean isRunning=false;
    private long time;
    public BunnyAI bunnyAI;
    public WolfsAI wolfsAI;
    IslandView view;

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
            int numberOfWolfsM=Integer.parseInt(view.initMales.getText());
            int numberOfWolfsF=Integer.parseInt(view.initFemales.getText());
            int numberOfBunnies=Integer.parseInt(view.initRabbits.getText());
            for (int i=0;i<numberOfWolfsM;i++){
                Wolf wolfy = new Wolf((int)(Math.random()*20),(int)(Math.random()*20),true);
                FaunaCollection.getInstance().fauna.add(wolfy);
                FaunaCollection.getInstance().fc[wolfy.getX()][wolfy.getY()].numberOfWolfsM++;
            }
            for (int i=0;i<numberOfWolfsF;i++){
                Wolf wolfy = new Wolf((int)(Math.random()*20),(int)(Math.random()*20),false);
                FaunaCollection.getInstance().fauna.add(wolfy);
                FaunaCollection.getInstance().fc[wolfy.getX()][wolfy.getY()].numberOfWoflsF++;
            }
            for (int i=0;i<numberOfBunnies;i++){
                Bunny bunny = new Bunny((int)(Math.random()*20),(int)(Math.random()*20));
                FaunaCollection.getInstance().fauna.add(bunny);
                FaunaCollection.getInstance().fc[bunny.getX()][bunny.getY()].numberOfBunnies++;
            }
    }

    public static void setPeriod(int period) {
        IslandModel.period = period;
    }

    public void updateSpeed(){
        if(timer!=null) {
            timer.cancel();
            timer=null;
        }
        wolfsAI.running=false;
        bunnyAI.running=false;

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
        },0,period);
    }

    void startSimulation(){
        isRunning=true;
        isPaused=false;
        FaunaCollection.getInstance().addNewborns.clear();
        FaunaCollection.getInstance().fauna.clear();
        period = Integer.parseInt(view.initPeriod.getText());
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
        },0,period);
    }


    public void stopSimulation(){
        isRunning=false;
        isPaused=false;
        if(timer!=null) {
            timer.cancel();
            timer=null;
        }
        if(wolfsAI!=null) wolfsAI.running=false;
        if(bunnyAI!=null) bunnyAI.running=false;
        time=0;
        FaunaCollection.getInstance().addNewborns.clear();
        FaunaCollection.getInstance().fauna.clear();
        for (int i=0;i<20;i++){
            for (int j=0;j<20;j++){
                FaunaCollection.getInstance().fc[i][j].numberOfBunnies=0;
                FaunaCollection.getInstance().fc[i][j].numberOfWoflsF=0;
                FaunaCollection.getInstance().fc[i][j].numberOfWolfsM=0;
            }
        }
        view.stopSimulation();
    }

    private String formMessage(){
        int bunnies=0, wolfM=0, wolfF=0, wolfsT=0;
        for (FaunaCollection.FaunaCounter[] f: FaunaCollection.getInstance().fc){
            for (FaunaCollection.FaunaCounter faunaCounter: f){
                bunnies+=faunaCounter.numberOfBunnies;
                wolfM+=faunaCounter.numberOfWolfsM;
                wolfF+=faunaCounter.numberOfWoflsF;
                wolfsT+=faunaCounter.numberOfWolfsM+faunaCounter.numberOfWoflsF;
            }
        }
        return "Population of rabbits: "+bunnies+"\n"+
                "Population of wolfs: "+wolfsT+"\n"+
                "Number of males: "+wolfM+"\n"+
                "Number of females: "+wolfF+"\n"+
                "Steps from start of simulation: " + time;
    }


    public boolean isPaused=false;

    public void pause(){
        isPaused=true;
        if(timer!=null) {
            timer.cancel();
            timer=null;
        }
        if(wolfsAI!=null) wolfsAI.running=false;
        if(bunnyAI!=null) bunnyAI.running=false;
    }

    public void resume(){
       isPaused=false;
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
            },0,3000);
    }
}
