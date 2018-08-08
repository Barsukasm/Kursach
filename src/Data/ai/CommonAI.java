package Data.ai;

public abstract class CommonAI extends Thread {

    public boolean running;
    public boolean paused;

    public void start(){
        super.start();
        running=true;
        paused = false;
    }

    @Override
    public void run() {
        while (running){
           try {
               Thread.sleep(1000);
           }catch (InterruptedException ex){
               ex.printStackTrace();
           }
           movement();
        }
    }

    public abstract void movement();
}
