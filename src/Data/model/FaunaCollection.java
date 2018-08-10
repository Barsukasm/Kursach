package Data.model;

import java.awt.*;
import java.util.Vector;

public class FaunaCollection {
    private static FaunaCollection instance;

    public Vector<LiveBeing> fauna;//коллекция обитателей острова
    public Point map[][];//координаты для отрисовки спрайтов
    public FaunaCounter fc[][];
    public Vector<LiveBeing> addNewborns;

    private FaunaCollection(){
        fauna = new Vector<>();
        map = new Point[20][20];
        fc=new FaunaCounter[20][20];

        addNewborns = new Vector<>();
        for (int i=0; i<20;i++){
            for (int j=0; j<20;j++){
                map[i][j]= new Point(30*i,30*j);
                fc[i][j]= new FaunaCounter();
            }
        }
    }

    public static FaunaCollection getInstance(){
        if (instance == null){
            instance = new FaunaCollection();
        }
        return instance;
    }


    public class FaunaCounter{
        public int numberOfBunnies=0;
        public int numberOfWolfsM=0;
        public int numberOfWoflsF=0;
    }


    public void checkAndClean(){//Ищем волков, у которых не осталось очков и кроликов, которые были съедены. Удаляем их из коллекции
       for (int i=0; i<FaunaCollection.getInstance().fauna.size();i++) {
           if (FaunaCollection.getInstance().fauna.get(i) instanceof Wolf){
               Wolf wolf = (Wolf)FaunaCollection.getInstance().fauna.get(i);
               if (wolf.getHp()<=0) {
                   if (wolf.isMale()){
                       if (FaunaCollection.getInstance().fc[wolf.getX()][wolf.getY()].numberOfWolfsM>0) FaunaCollection.getInstance().fc[wolf.getX()][wolf.getY()].numberOfWolfsM--;
                   } else {
                       if(FaunaCollection.getInstance().fc[wolf.getX()][wolf.getY()].numberOfWoflsF>0) FaunaCollection.getInstance().fc[wolf.getX()][wolf.getY()].numberOfWoflsF--;
                   }
                   FaunaCollection.getInstance().fauna.remove(wolf);
               }
           } else if(FaunaCollection.getInstance().fauna.get(i)instanceof Bunny){
               Bunny bunny = (Bunny)FaunaCollection.getInstance().fauna.get(i);
               if(bunny.isEaten()){
                   if(FaunaCollection.getInstance().fc[bunny.getX()][bunny.getY()].numberOfBunnies>0)FaunaCollection.getInstance().fc[bunny.getX()][bunny.getY()].numberOfBunnies--;
                   FaunaCollection.getInstance().fauna.remove(bunny);
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
}
