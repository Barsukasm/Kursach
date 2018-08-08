package Data.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Wolf extends LiveBeing {

    private static Image img;

    static {
        try{
        img = ImageIO.read(new File("images/Wolf2.png"));
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private double hp;
    private boolean isMale;

    public Wolf(int x, int y, boolean isMale){
        super(x,y);
        hp = 1;
        this.isMale=isMale;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    @Override
    public void move() {
        hp-=0.1;
        boolean stepIsDone = false;
        Bunny b=null;
        scycle:
        for(int i=getX()-1; i<=getX()+1;i++){
            for(int j=getY()-1;j<=getY()+1;j++){
               b=FaunaCollection.getInstance().hasBunny(i,j);
               if(b!=null){
                   b.setEaten(true);
                   hp++;
                   setX(b.getX());
                   setY(b.getY());
                   stepIsDone=true;
                   break scycle;
               }
            }
        }
        if (!stepIsDone&&isMale){
            fem:
            for(int i=getX()-1; i<=getX()+1;i++){
                for(int j=getY()-1;j<=getY()+1;j++){
                    Wolf femW=FaunaCollection.getInstance().findFem(i,j);
                    if(femW!=null){
                        setX(femW.getX());
                        setY(femW.getY());
                        stepIsDone=true;
                        boolean isMale = true;
                        if (Math.random()>0.5) isMale = false;
                        Wolf wolfy = new Wolf(getX(),getY(), isMale);
                        FaunaCollection.getInstance().addNewborns.add(wolfy);
                        break fem;
                    }
                }
            }
        }
        if (!stepIsDone){
            int r = (int)(Math.random()*9);
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
        }
    }


    @Override
    public void paint(Graphics g) {
        if (img!=null) {
            g.drawImage(img,FaunaCollection.getInstance().map[this.getX()][this.getY()].x,FaunaCollection.getInstance().map[this.getX()][this.getY()].y,img.getWidth(null),img.getHeight(null),null);
        }
    }
}
