/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Quinn Z
 * qaz62
 * <Student1 5-digit Unique No.>
 * Ali Ziyaan  Momin
 * AZM259
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private boolean fightMode = false;

	//Keeps track of how many critters exist on each given space
	private static int[][] positionMap = new int[Params.world_width][Params.world_height];
	private static List<Critter> fightClub = new java.util.ArrayList<Critter>();
	
	
	private boolean hasMoved = false;
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {

		if(direction == 0){
			moveCritter(0,1);
		}
		else if(direction == 1){
			moveCritter(1,1);
		}
		else if(direction == 2){
			moveCritter(1,0);
		}
		else if(direction == 3){
			moveCritter(1,-1);
		}
		else if(direction == 4){
			moveCritter(0, -1);
		}
		else if(direction == 5){
			moveCritter(-1,-1);
		}
		else if(direction == 6){
			moveCritter(-1,0);
		}
		else if(direction == 7){
			moveCritter(-1,1);
		}

		energy -= Params.walk_energy_cost;

	}
	
	protected final void run(int direction) {
		if(direction == 0){
			moveCritter(0,2);
		}
		else if(direction == 1){
			moveCritter(2,2);
		}
		else if(direction == 2){
			moveCritter(2,0);
		}
		else if(direction == 3){
			moveCritter(2,-2);
		}
		else if(direction == 4){
			moveCritter(0, -2);
		}
		else if(direction == 5){
			moveCritter(-2,-2);
		}
		else if(direction == 6){
			moveCritter(-2,0);
		}
		else if(direction == 7){
			moveCritter(-2,2);
		}

		energy -= Params.run_energy_cost;
	}

	
	//should be static I think
	private final void moveCritter(int x_coord, int y_coord){

		if(!this.hasMoved) {
			return;
		}

		this.hasMoved = true;

		if(fightMode && !checkSquareIsEmpty(x_coord, y_coord)){
			return;
		}

	    positionMap [this.x_coord][this.y_coord] -= 1;

		if(x_coord < 0){
			this.x_coord = x_coord + Params.world_width;
		}
		else{
			this.x_coord = (this.x_coord + x_coord) % Params.world_width;
		}

		if(y_coord < 0){
			this.y_coord = y_coord + Params.world_height;
		}
		else{
			this.y_coord = (this.y_coord + y_coord) % Params.world_height;
		}

		positionMap [this.x_coord][this.y_coord] += 1;
	}

	/*
	 * x_coord and y_coord are shifts in current x and y
	 * this method returns if the (x + x_coord, y + y_coord) are taken
	 */
	private final boolean checkSquareIsEmpty(int x_coord, int y_coord){
		int x_temp;
		int y_temp;

		if(x_coord < 0){
			x_temp = x_coord + Params.world_width;
		}
		else{
			x_temp = (this.x_coord + x_coord) % Params.world_width;
		}

		if(y_coord < 0){
			y_temp = y_coord + Params.world_height;
		}
		else{
			y_temp = (this.y_coord + y_coord) % Params.world_height;
		}

		if(positionMap[x_temp][y_temp] > 0){
			return false;
		}

		return true;
	}
	
	private final static int[] getRandomCoord(){
		int rx = getRandomInt(Params.world_width-1);
		int ry = getRandomInt(Params.world_height-1);
		int[] arr = {rx, ry};
		return arr;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	/*	1. Do move
	 *  2. Do make child
	 *  3. encounters
	 *  4. remove dead critters
	 * 
	 */
	
	
	
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Critter temp = (Critter) Class.forName("assignment4." + critter_class_name).newInstance();
			//set variables
			int[] xyPos = getRandomCoord();
			temp.x_coord = xyPos[0];
			temp.y_coord = xyPos[1];
			temp.energy = Params.start_energy;
			population.add(temp);
			
			positionMap[temp.x_coord][temp.y_coord] += 1;
		}
		catch(ClassNotFoundException e1){
			throw new InvalidCritterException(critter_class_name);
		} catch (InstantiationException e2) {
			throw new InvalidCritterException(critter_class_name);
		} catch (IllegalAccessException e3) {
			throw new InvalidCritterException(critter_class_name);
		}
		
	}
	
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Critter temp;
		try{
			temp = (Critter) Class.forName(critter_class_name).newInstance();	
		}
		catch(ClassNotFoundException e1){
			throw new InvalidCritterException(critter_class_name);
		} catch (InstantiationException e2) {
			throw new InvalidCritterException(critter_class_name);
		} catch (IllegalAccessException e3) {
			throw new InvalidCritterException(critter_class_name);
		}

		for(Critter c : population){
			if(temp.getClass().isInstance(c)){
				result.add(c);
			}
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	private static boolean onSquare(Critter c, int x, int y){
		return (c.x_coord == x) && (c.y_coord == y);
	}

	private static void killCritter(Critter c){
		positionMap[c.x_coord][c.y_coord] -= 1;
		population.remove(c);
	}
	
	public static void worldTimeStep() {
		//calls doTimeStep for every critter in collection
		for (Critter c : population) {
			c.hasMoved = false;
			c.energy -= Params.rest_energy_cost; //critter loses rest energy
			c.doTimeStep(); // 
			
			//remove dead creatures
			if(c.energy <= 0){
				killCritter(c);		
			}
		}	
			
		//check for encounters
		for(int i = 0; i < Params.world_height; i++){
			for(int j = 0; j < Params.world_width; j++){
				if(positionMap[i][j] > 1){
					for(Critter c : population){
						if(onSquare(c, i, j)){
							fightClub.add(c);
						}
					}
					//encounters with fightClub
				}
			}
		}
		
		//add babies to population
		for(Critter b : babies){
			population.add(b);
			positionMap[b.x_coord][b.y_coord] += 1;
			babies.remove(b);
		}
		
		
	}

	private static void encounter() {
		//iterate over fightClub
		//
		Critter A;
		Critter B;
		boolean boolA;
		boolean boolB;
		boolean stillFighting;

		while(fightClub.size() > 1){
			stillFighting = true;
			A = fightClub.get(0);
			fightClub.remove(0);

			B = fightClub.get(0);
			fightClub.remove(0);

			boolA = A.fight(B.toString());
			boolB = B.fight(A.toString());

			A.fightMode = true;
			B.fightMode = true;

			if(A.energy <= 0){
				killCritter(A);
				stillFighting = false;
			}
			if(B.energy <= 0){
				killCritter(B);
				stillFighting = false;
			}

			if(stillFighting && A.x_coord == B.x_coord && A.y_coord == B.y_coord){
				int rollA = (boolA?getRandomInt(A.getEnergy()):0);
				//int rollA = getRandomInt((boolA?A.getEnergy():0));

				int rollB = (boolB?getRandomInt(B.getEnergy()):0);

				if(rollA >= rollB){
					B.energy = 0;
					A.energy += B.energy/2;
					fightClub.add(A);
				}
				else{
					A.energy = 0;
					B.energy += A.energy/2;
					fightClub.add(B);
				}
			}
		}
	}
	
	public static void displayWorld() {
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
		System.out.println("+");
		System.out.flush();
		for(int j = 0; j < Params.world_height; j++){
			System.out.print("|");
			System.out.flush();

			for(int i = 0; i < Params.world_width; i++){
				if(positionMap[i][j] >= 1){
					for(Critter c : population){
						if(onSquare(c, i, j)){
							System.out.print(c);
							break;
						}
					}
				}
				else{
					System.out.print(" ");
				}
			}
			System.out.println("|");
		}
		
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
		System.out.println("+");

	}
}
