package Data.model;

import java.awt.*;
import java.util.Vector;

public class FaunaCollection {
    private static FaunaCollection instance;

    public Vector<LiveBeing> fauna;//коллекция обитателей острова
    public Point map[][];//координаты для отрисовки спрайтов
    //public int island[][];//число обитателей в каждой клетке

    private FaunaCollection(){
        fauna = new Vector<>();
        map = new Point[20][20];
       // island = new int[20][20];
        for (int i=0; i<20;i++){
            for (int j=0; j<20;j++){
                map[i][j]= new Point(30*i,30*j);
                //island[i][j]=0;
            }
        }
    }

    public static FaunaCollection getInstance(){
        if (instance == null){
            instance = new FaunaCollection();
        }
        return instance;
    }


    public void checkPairs(int x, int y){//Похоже, не нужен
        boolean male = false;
        boolean female = false;
        for (int i=0; i<FaunaCollection.getInstance().fauna.size();i++){ //Проходимся по коллекции, ищем есть ли в квадрате с координатами (x,y) хотя бы по одной особи разных полов
            if (FaunaCollection.getInstance().fauna.get(i).getX()==x //Находится ли особь в указанном секторое x?
                    &&FaunaCollection.getInstance().fauna.get(i).getY()==y //Находится ли особь в указанном секторе y?
                    &&FaunaCollection.getInstance().fauna.get(i)instanceof Wolf){ //Является ли особь волком?
                Wolf wolf = (Wolf)FaunaCollection.getInstance().fauna.get(i);
                if(wolf.isMale()){
                    male = true;
                } else {
                    female = true;
                }
            }
        }
        if (male&&female){
            boolean isMale = true;
            if (Math.random()>0.5) isMale = false;
            Wolf wolfy = new Wolf(x,y, isMale);
            //FaunaCollection.getInstance().island[x][y]+=1;
            FaunaCollection.getInstance().fauna.add(wolfy);
        }
    }


    public void checkAndClean(){//Ищем волков, у которых не осталось очков и кроликов, которые были съедены. Удаляем их из коллекции
       for (int i=0; i<FaunaCollection.getInstance().fauna.size();i++) {
           if (FaunaCollection.getInstance().fauna.get(i) instanceof Wolf){
               Wolf wolf = (Wolf)FaunaCollection.getInstance().fauna.get(i);
               if (wolf.getHp()<=0) {
                   FaunaCollection.getInstance().fauna.remove(wolf);
                   //FaunaCollection.getInstance().island[wolf.getX()][wolf.getY()]-=1;
               }
           } else if(FaunaCollection.getInstance().fauna.get(i)instanceof Bunny){
               Bunny bunny = (Bunny)FaunaCollection.getInstance().fauna.get(i);
               if(bunny.isEaten()){
                   FaunaCollection.getInstance().fauna.remove(bunny);
                   //FaunaCollection.getInstance().island[bunny.getX()][bunny.getY()]-=1;
               }
           }
       }
    }

    public Bunny hasBunny(int x, int y){//поиск кролика в указанных координатах
        Bunny bunny = null;
        for (int i=0; i<FaunaCollection.getInstance().fauna.size();i++){
            if (FaunaCollection.getInstance().fauna.get(i).getX()==x //Находится ли предполагаемый кролик в указанном секторое x?
                    &&FaunaCollection.getInstance().fauna.get(i).getY()==y //Находится ли предполагаемый кролик в указанном секторе y?
                    &&FaunaCollection.getInstance().fauna.get(i)instanceof Bunny){
                bunny=(Bunny)FaunaCollection.getInstance().fauna.get(i);
            }
        }
        return bunny;
    }

    public Wolf findFem(int x, int y){//поиск самки в указанных координатах
        Wolf female = null;
        finded:
        for (int i=0; i<FaunaCollection.getInstance().fauna.size();i++){
            if (FaunaCollection.getInstance().fauna.get(i).getX()==x //Находится ли предполагаемый кролик в указанном секторое x?
                    &&FaunaCollection.getInstance().fauna.get(i).getY()==y //Находится ли предполагаемый кролик в указанном секторе y?
                    &&FaunaCollection.getInstance().fauna.get(i)instanceof Wolf){
                female =(Wolf) FaunaCollection.getInstance().fauna.get(i);
                if(!female.isMale()) {break finded;}
                else female=null;
            }
        }
        return female;
    }
}
