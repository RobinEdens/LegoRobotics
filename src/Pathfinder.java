/**
 *
 * @author Harper & Robin
 */
import java.lang.Math;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;

public class Pathfinder {


	public static void main(String[] args) {
		
		
		// Length of brick is 7.5 inch; width is 3.5
		DifferentialPilot pilot = new DifferentialPilot(1.75f, 4.65f, Motor.A, Motor.C);
		TouchSensor touch = new TouchSensor(SensorPort.S4);
		ColorSensor colorSense = new ColorSensor(SensorPort.S1);
		boolean rightTurn = true;
		int turnA = -30;
		int turnB = -60;
		
		pilot.setTravelSpeed(15);
		pilot.setRotateSpeed(90);
		System.out.println("Hello World!");
		
		while (!Button.ENTER.isDown()) {	
			System.out.println("Vroom vroom");
			pilot.travel(40, true);

			while(pilot.isMoving()) {
				if (touch.isPressed()) {
					pilot.stop();
					System.out.println("Something is in my way!");
					pilot.travel(-4);
					pilot.rotate(-90);
					pilot.travelArc(9.5, 29.83, true);
				//	#FIXME add extra movement to align self on top of line proper
					}
				
				if (colorSense.getColorID() == Color.WHITE) {
					pilot.stop();
					System.out.println("Line has been lost. Searching...");
					if(rightTurn) {
						while (colorSense.getColorID() != Color.BLACK) {
							pilot.rotate(turnA, true);
							rightTurn = false;
							pilot.rotate(Math.abs(turnB), true);
							rightTurn = true;
							turnA -= 60;
							turnB -= 60;
						}
					}
					else {
						while (colorSense.getColorID() != Color.BLACK) {
							pilot.rotate(Math.abs(turnA), true);
							rightTurn = true;
							pilot.rotate(turnB, true);
							rightTurn = false;
							turnA -= 60;
							turnB -= 60;
						}
					}
						
					turnA = -30;
					turnB = -60;
					System.out.println("Line has been found. Continuing quest!");
				}	
				
				if (colorSense.getColorID() == Color.GREEN) {
					pilot.stop();
					System.out.println("Victory!");
					pilot.rotate(360);
					pilot.rotate(-360);
					Sound.beep();
					return;
				}
			}
		}
	}
}
