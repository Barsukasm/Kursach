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
        if(isMale){
            if (FaunaCollection.getInstance().fc[getX()][getY()].numberOfWolfsM>0) FaunaCollection.getInstance().fc[getX()][getY()].numberOfWolfsM--;
        } else {
            if(FaunaCollection.getInstance().fc[getX()][getY()].numberOfWoflsF>0) FaunaCollection.getInstance().fc[getX()][getY()].numberOfWoflsF--;
        }
        hp-=0.1;
        boolean stepIsDone = false;
        Bunny b=null;
        scycle:
        for(int i=getX()-1; i<=getX()+1;i++){
            for(int j=getY()-1;j<=getY()+1;j++){
                if (0<=i&&i<20&&0<=j&&j<20){//если индексы не выходят за пределы массива
                    if(FaunaCollection.getInstance().fc[i][j].numberOfBunnies>0) {//если в данном квадрате есть хотя бы один кролик
                        b = FaunaCollection.getInstance().hasBunny(i, j);//то идем туда и ищем кролика, чтобы съесть
                        if (b != null) {
                            b.setEaten(true);
                            hp++;
                            setX(b.getX());
                            setY(b.getY());
                            stepIsDone = true;
                            break scycle;
                        }
                    }
                }
            }
        }
        if (!stepIsDone&&isMale){//если волк уже сделал свой ход(съел кролика), то пропускаем этот шаг, иначе, ищем самку
            fem:
            for(int i=getX()-1; i<=getX()+1;i++){
                for(int j=getY()-1;j<=getY()+1;j++){
                    if (0<=i&&i<20&&0<=j&&j<20){//если индексы не выходят за пределы массива
                        if(FaunaCollection.getInstance().fc[i][j].numberOfWoflsF>0//если в данном квадрате есть хотя бы одна самка, то переиещаемся в этот квадрат и даем потомство
                                &&FaunaCollection.getInstance().fc[i][j].numberOfWoflsF+FaunaCollection.getInstance().fc[i][j].numberOfWolfsM<3) {//Установим дополнительное ограничение на количество волков в одной клетке
                            setX(i);
                            setY(j);
                            stepIsDone = true;
                            boolean isMale = true;
                            if (Math.random() > 0.5) isMale = false;
                            Wolf wolfy = new Wolf(getX(), getY(), isMale);
                            if (wolfy.isMale()) {
                                FaunaCollection.getInstance().fc[wolfy.getX()][wolfy.getY()].numberOfWolfsM++;
                            } else {
                                FaunaCollection.getInstance().fc[wolfy.getX()][wolfy.getY()].numberOfWoflsF++;
                            }
                            FaunaCollection.getInstance().addNewborns.add(wolfy);
                            break fem;
                        }
                    }
                }
            }
        }
        if (!stepIsDone){//Если самец не нашел самку или эта особь является самкой, то передвигаемся рандомно
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
        if(isMale){
            FaunaCollection.getInstance().fc[getX()][getY()].numberOfWolfsM++;
        } else {
            FaunaCollection.getInstance().fc[getX()][getY()].numberOfWoflsF++;
        }
    }


    @Override
    public void paint(Graphics g) {
        if (img!=null) {
            g.drawImage(img,FaunaCollection.getInstance().map[this.getX()][this.getY()].x,FaunaCollection.getInstance().map[this.getX()][this.getY()].y,img.getWidth(null),img.getHeight(null),null);
        }
    }

    public static Wolf getInstance(int x, int y){
        Wolf wolf=new Wolf(x,y,true);
        return wolf;
    }
}
