package assignment4;

/**
 * Created by aliziyaan on 10/18/16.
 */
public class AliGOD extends Critter{
    @Override
    public String toString() {
        return "A";
    }

    public boolean fight(String enemy){
        if(enemy.equals("Algae")){
            return false;
        }
        return true;
    }

    @Override
    public void doTimeStep(){
        walk(0);
        if(getEnergy() > 50){
            AliGOD jesus = new AliGOD();
            reproduce(jesus, 6);
        }
    }

    public static void runStats (java.util.List<Critter> GODS){
        int total_GODS = 0;
        for(Object g: GODS){
            total_GODS ++;
        }
        System.out.println("There are " + total_GODS + " GODS on the map");
    }
}
