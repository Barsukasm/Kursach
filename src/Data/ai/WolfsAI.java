package Data.ai;

import Data.model.FaunaCollection;
import Data.model.LiveBeing;
import Data.model.Wolf;

public class WolfsAI extends CommonAI {

    public WolfsAI(){
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
                if (lb instanceof Wolf){
                    lb.move();
                }
            }
        }
    }
}
