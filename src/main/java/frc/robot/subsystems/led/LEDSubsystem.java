package frc.robot.subsystems.led;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.led.modes.LEDMode;

public class LEDSubsystem extends SubsystemBase {

  @Override
  public void periodic() {
    // For every segment that is registered, run the periodic function
    for (LEDSegment ledSegment : LEDConstants.ledSegments) {
      ledSegment.getLedMode().periodic(ledSegment.getIndex());
    }
  }

  /** Does the basic initialization process of setting the length of the LEDs */
  public void initialize() {
    // Register all LED segments into the array
    LEDConstants.ledSegments.add(LEDSegment.FrontLeft);
    LEDConstants.ledSegments.add(LEDSegment.BackLeft);
    LEDConstants.ledSegments.add(LEDSegment.BackRight);
    LEDConstants.ledSegments.add(LEDSegment.FrontRight);

    LEDConstants.leds.setLength(
        (LEDConstants.numberOfSegments
            * LEDConstants.ledsPerSegment)); // Set the length of the LED strip
    LEDConstants.leds.start(); // Start the LED strip
  }

  /**
   * Sets the mode for all segments available
   *
   * @param ledMode The mode to set them all as. Please see the modes directory for all available
   *     modes
   */
  private void setAllSegmentModesCommand(LEDMode ledMode) {
    // For every segment we can set the mode of, set the mode as the one provided
    for (LEDSegment ledSegment : LEDConstants.ledSegments) {
      ledSegment.setLedMode(ledMode.getClass());
    }
  }

  /** Initialize the LED subsystem when we create an instance of it */
  public LEDSubsystem() {
    this.initialize();
  }
}
