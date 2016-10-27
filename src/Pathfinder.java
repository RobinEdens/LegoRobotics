/**
 * @author Harper & Robin
 *
 */
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		// Length of brick is 7.5 inch; width is 3.5
		DifferentialPilot pilot = new DifferentialPilot(1.75f, 4.65f, Motor.A, Motor.C);
		TouchSensor touch = new TouchSensor(SensorPort.S4);
		ColorSensor color = new ColorSensor(SensorPort.S1);
		System.out.println("Hello World!");
		
		color.setFloodlight(true);
		System.out.println(color.getFloodlight());
		System.out.println(color.getColorID());
		while (!Button.ENTER.isDown()) {	
			pilot.travel(10, true);

			while(pilot.isMoving()) {
				if (touch.isPressed()) {
					pilot.stop();
					System.out.println("Something is in my way!");
					pilot.travel(-5);
					pilot.rotate(-90);
					pilot.arcForward(8.4);
					}
				}	
			}
		}
}