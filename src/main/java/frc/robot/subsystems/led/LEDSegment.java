package frc.robot.subsystems.led;

import frc.robot.subsystems.led.exceptions.InvalidLEDSegmentException;
import frc.robot.subsystems.led.modes.Breathing;
import frc.robot.subsystems.led.modes.LEDMode;
import frc.robot.subsystems.led.modes.Solid;

/**
 * We are going to attach a name to each index in the LED modes array here. Remember that all
 * indexes start at 0 and NOT 1
 */
public enum LEDSegment {

  // Add all aliases for segments below
  FrontLeft(0, new Solid(new LEDColour(255, 0, 0))), // Set the LEDs to solid red
  BackLeft(1, new Breathing(new LEDColour(0, 255, 0))), // Create a green breathing effect
  BackRight(2, new Breathing(new LEDColour(0, 255, 0))), // Create a green breathing effect
  FrontRight(3, new Solid(new LEDColour(255, 0, 0))); // Set the LEDs to solid red

  public final int segmentNumber; // The segment number of the LED strip (starts at 1 and goes up)
  public LEDMode ledMode; // The mode of the LED strip

  private LEDSegment(int segmentNumber, LEDMode defaultLedMode) {
    this.segmentNumber = segmentNumber;
    this.ledMode = defaultLedMode;
  }

  /**
   * Checks if the LED segment is a valid segment based off of the number of segments in
   * LEDConstants.java
   *
   * @return True if it is within the index bounds, false if it isn't
   */
  private boolean isValid() {
    // Return a boolean based on if the segment number is greater than number of
    // segments
    return (this.getSegmentIdentifier() < LEDSubsystem.ledSegments.size());
  }

  /**
   * Sets the mode of the current segment
   *
   * @param ledMode The mode to set the LEDs to
   */
  public void setLedMode(LEDMode ledMode) {
    // Guard clause to check if the segment is within the bounds of the number of
    // available segments
    if (!this.isValid()) {
      throw new InvalidLEDSegmentException(
          String.format(
              "Invalid LED segment: %d. Number of segments: %d",
              this.getSegmentIdentifier(),
              LEDSubsystem.ledSegments.size())); // Throw an exception with the segment information
    }

    this.ledMode = ledMode;
  }

  /**
   * Retrieves the true segment number of an LED segment
   *
   * @return The segment number of the LED segment
   */
  public int getSegmentIdentifier() {
    return this.segmentNumber;
  }

  /**
   * Gets the LED mode for this segment
   *
   * @return The LED mode of the current segment as an LEDMode object
   */
  public LEDMode getLedMode() {
    return this.ledMode;
  }
}
