package frc.robot.subsystems.led;

import java.util.ArrayList;
import java.util.List;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;


public class LEDConstants {
    /**
     * Related to the number of LEDs and number of segments
     */
    public static final int numberOfSegments = 4; // The number of segments, or LED strips there are attached to each other.
    public static final int ledsPerSegment = 16; // The number LEDs (actual diodes) there are on each segment

    public static List<LEDSegment> ledSegments = new ArrayList<>();

    public static AddressableLED leds = new AddressableLED(0); // The PWM port the LEDs are plugged into
    public static AddressableLEDBuffer buffer = new AddressableLEDBuffer((numberOfSegments * ledsPerSegment)); // The buffer that holds the LED data
}
