package assignment4;

//Created by Quinn Z.
public class Aggie extends Critter {
	int cultPower = 0;
	
	boolean move = false; //it won't move until it's cultPower hits 50
	
    @Override
    public String toString() {
        return "A";
    }

    public boolean fight(String enemy){
    	if(cultPower > 100 || enemy.equals("Longhorn")){ //always fights Longhorn
    		if(cultPower > 100){
    			cultPower -= 50;
    		}
    		return true;
    	}
    	return false;
    }

    @Override
    public void doTimeStep() {
    	if(cultPower < 200){
        	cultPower += 25;
    	}
    	if(cultPower >= 50){
    		move = true;
    	}

    	if(move){
    		walk(getRandomInt(8));
    	}
        //This critter runs to it's death
        if(getEnergy() > 150){
            Aggie a = new Aggie();
            reproduce(a, 7);
        }
    }

    public static void runStats (java.util.List<Critter> ag){
        int count = ag.size();
        if(count > 0) {
            System.out.println("There are " + count + " Aggies on the map.");
        }
        else{
            System.out.println("There must not be enough UT applicants!");
        }
    }

}
