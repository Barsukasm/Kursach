package Data.model;

import java.awt.*;
import java.io.Serializable;

public abstract class LiveBeing implements Serializable {
    private int x,y;
    public LiveBeing(int x, int y){
        this.x=x;
        this.y=y;
    }

    public LiveBeing(){
        x=0;
        y=0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void move();

    public abstract void paint(Graphics g, ClassLoader classLoader);
}
