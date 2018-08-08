package Data.model;

import java.awt.*;
import java.util.Vector;

public class FaunaCollection {
    private static FaunaCollection instance;

    public Vector<LiveBeing> fauna;//коллекция обитателей острова
    public Point map[][];//координаты для отрисовки спрайтов
    public Vector<LiveBeing> addNewborns;

    private FaunaCollection(){
        fauna = new Vector<>();
        map = new Point[20][20];
        addNewborns = new Vector<>();
        for (int i=0; i<20;i++){
            for (int j=0; j<20;j++){
                map[i][j]= new Point(30*i,30*j);
            }
        }
    }

    public static FaunaCollection getInstance(){
        if (instance == null){
            instance = new FaunaCollection();
        }
        return instance;
    }




    public void checkAndClean(){//Ищем волков, у которых не осталось очков и кроликов, которые были съедены. Удаляем их из коллекции
       for (int i=0; i<FaunaCollection.getInstance().fauna.size();i++) {
           if (FaunaCollection.getInstance().fauna.get(i) instanceof Wolf){
               Wolf wolf = (Wolf)FaunaCollection.getInstance().fauna.get(i);
               if (wolf.getHp()<=0) {
                   FaunaCollection.getInstance().fauna.remove(wolf);
               }
           } else if(FaunaCollection.getInstance().fauna.get(i)instanceof Bunny){
               Bunny bunny = (Bunny)FaunaCollection.getInstance().fauna.get(i);
               if(bunny.isEaten()){
                   FaunaCollection.getInstance().fauna.remove(bunny);
               }
           }
       }
    }

    public void reproduction(){

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
