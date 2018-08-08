package Data.ai;

import Data.model.Bunny;
import Data.model.FaunaCollection;
import Data.model.LiveBeing;

public class BunnyAI extends CommonAI {
    public BunnyAI(){
        super();
    }

    @Override
    public void movement() {
        synchronized (FaunaCollection.getInstance().fauna){
            while (paused){
                try {
                    FaunaCollection.getInstance().fauna.wait();
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
            for(LiveBeing lb: FaunaCollection.getInstance().fauna){
                if (lb instanceof Bunny){
                    lb.move();
                }
            }
        }
    }
}
