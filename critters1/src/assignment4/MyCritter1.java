package assignment4;

import java.util.List;
import java.util.Scanner;

public class MyCritter1 extends Critter.TestCritter {

	@Override
	public void doTimeStep() {
		//System.out.println(this.getEnergy());
		System.out.println("place critter (" + this.getX_coord() + "," + this.getY_coord() + ") at: ");
		Scanner s = new Scanner(System.in);
		int input = s.nextInt();
		int input2 = s.nextInt();
		this.setX_coord(input);
		this.setY_coord(input2);
		//walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}
	
	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
}
