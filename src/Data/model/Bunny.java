package Data.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bunny extends LiveBeing {

    /*private static Image img;

    static {
        try{
            img = ImageIO.read(new File("images/rabbit1.png"));
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }*/

    private boolean isEaten;

    public Bunny(int x, int y){
        super(x,y);
        isEaten = false;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public boolean isEaten() {
        return isEaten;
    }

    @Override
    public void move() {
        if(!isEaten){//Если кролика еще не съели, то он может двигаться
            int r = (int)(Math.random()*9);
            if(Math.random()<0.2&&FaunaCollection.getInstance().fc[getX()][getY()].numberOfBunnies<3){//Установим дополнительное ограничение по количеству кроликов в одной клетке
                Bunny bunny = new Bunny(getX(),getY());
                FaunaCollection.getInstance().addNewborns.add(bunny);
                FaunaCollection.getInstance().fc[bunny.getX()][bunny.getY()].numberOfBunnies++;
            }

            FaunaCollection.getInstance().fc[getX()][getY()].numberOfBunnies--;//Убираем кролика из квадрата перед тем, как сделать шаг
            switch (r){
                case 0:
                    if(getX()>0){
                        setX(getX()-1);
                    }
                    if(getY()>0){
                        setY(getY()-1);
                    }
                    break;
                case 1:
                    if(getX()>0){
                        setX(getX()-1);
                    }
                    break;
                case 2:
                    if(getX()>0){
                        setX(getX()-1);
                    }
                    if(getY()<19){
                        setY(getY()+1);
                    }
                    break;
                case 3:
                    if(getY()>0) {
                        setY(getY() - 1);
                    }
                    break;
                case 4:
                    break;
                case 5:
                    if(getY()<19){
                        setY(getY()+1);
                    }
                    break;
                case 6:
                    if(getX()<19){
                        setX(getX()+1);
                    }
                    if(getY()>0) {
                        setY(getY() - 1);
                    }
                    break;
                case 7:
                    if(getX()<19){
                        setX(getX()+1);
                    }
                    break;
                case 8:
                    if(getX()<19){
                        setX(getX()+1);
                    }
                    if(getY()<19){
                        setY(getY()+1);
                    }
                    break;
            }
            FaunaCollection.getInstance().fc[getX()][getY()].numberOfBunnies++;//Добавляем кролика на новые координаты
        }
    }

    @Override
    public void paint(Graphics g,ClassLoader classLoader) {
        Image img=null;
        try{
            img = ImageIO.read(classLoader.getResourceAsStream("images/rabbit1.png"));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        if (img!=null) {
            g.drawImage(img,FaunaCollection.getInstance().map[this.getX()][this.getY()].x,FaunaCollection.getInstance().map[this.getX()][this.getY()].y,img.getWidth(null),img.getHeight(null),null);
        }
    }

    public static Bunny getInstance(int x, int y){
        Bunny inst=new Bunny(x,y);
        return inst;
    }
}
