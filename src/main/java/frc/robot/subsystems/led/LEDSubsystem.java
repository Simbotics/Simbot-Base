package frc.robot.subsystems.led;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.led.modes.LEDMode;

public class LEDSubsystem extends SubsystemBase {

  public static List<LEDSegment> ledSegments = new ArrayList<>();

  public static AddressableLED leds = new AddressableLED(0); // The PWM port the LEDs are plugged into
  public static AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(
      (ledSegments.size() * LEDConstants.ledsPerSegment)); // The buffer that holds the LED data

  @Override
  public void periodic() {
    // For every segment that is registered, run the periodic function
    for (LEDSegment ledSegment : ledSegments) {
      ledSegment.getLedMode().periodic(ledSegment.getIndex());
    }
  }

  /** Does the basic initialization process of setting the length of the LEDs */
  public void initialize() {
    // Register all LED segments into the array
    ledSegments.add(LEDSegment.FrontLeft);
    ledSegments.add(LEDSegment.BackLeft);
    ledSegments.add(LEDSegment.BackRight);
    ledSegments.add(LEDSegment.FrontRight);

    leds.setLength(
        (ledSegments.size()
            * LEDConstants.ledsPerSegment)); // Set the length of the LED strip

    leds.start(); // Start the LED strip
  }

  /**
   * Sets the mode for all segments available
   *
   * @param ledMode The mode to set them all as. Please see the modes directory
   *                for all available
   *                modes
   */
  public void setAllSegmentModesCommand(LEDMode ledMode) {
    // For every segment we can set the mode of, set the mode as the one provided
    for (LEDSegment ledSegment : ledSegments) {
      ledSegment.setLedMode(ledMode);
    }
  }

  /** Initialize the LED subsystem when we create an instance of it */
  public LEDSubsystem() {
    this.initialize();
  }
}
