package Data.model;

public class Bunny extends LiveBeing {

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
        int r = (int)(Math.random()*9);
        if(Math.random()<=0.2){
            Bunny bunny = new Bunny(getX(),getY());
            FaunaCollection.getInstance().fauna.add(bunny);
        }
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
