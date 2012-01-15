package elevatorsimulation.master;

import java.util.*;

/**
 * This class starts the simulation by creating an instance of
 * mastermind.
 * 
 * @author vijay
 *
 */
public class Simulator {
	
	/**
	 * Create an instance of MasterMind and run the simulation
	 * @param args
	 */
	public static void main ( String args[] ) {
		Scanner sc = new Scanner( System.in );
		// Prompt user for input
		System.out.println( "Enter the number of floors, the number of \n" +
				"elevators, and the number of iterations to simulate." );
		// Get input from user
		Integer numFloors = sc.nextInt();
		Integer numElevators = sc.nextInt();
//		Double probCreatingPerson = sc.nextDouble();
		Integer numberOfIterations = sc.nextInt();
		
		// Create a MasterMind class and start the simulation
		MasterMind master = new MasterMind( numFloors, numElevators, 
				numberOfIterations );
		master.startSimulation();
	}
}
