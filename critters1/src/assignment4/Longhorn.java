package assignment4;

//Created by Quinn Z.
public class Longhorn extends Critter {
	int gpa = 400; //start with that sweet sweet 4.00
	
    @Override
    public String toString() {
        return "U";
    }

    //fights aggies no matter what. Fights others only if gpa is 3 or 4
    public boolean fight(String enemy){
    	if(gpa > 200 || enemy.equals("Aggie")){ //always fights Aggies
    		if(gpa >= 50){
    			gpa -= 50; //lose .5 gpa
    		}
    		return true;
    	}
    	return false;
    }

    @Override
    public void doTimeStep() {
    	if(gpa < 390){
    		gpa += 10;
    	}
        walk(getRandomInt(8));
        //This critter runs to it's death
        if(getEnergy() > 60 && gpa < 300){ //anybody over a 3.00 gpa won't have kids
            Longhorn l = new Longhorn();
            reproduce(l, 2);
        }
    }

    public static void runStats (java.util.List<Critter> lh){
        int count = lh.size();
        if(count > 0) {
            System.out.println("There are " + count + " Longhorns on the map.");
            System.out.println("Smash those Aggies");
        }
        else{
            System.out.println("Oh no!");
        }
    }

}
