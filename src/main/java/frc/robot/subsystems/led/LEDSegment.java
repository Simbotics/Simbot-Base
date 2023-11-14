package frc.robot.subsystems.led;

import frc.robot.subsystems.led.exceptions.InvalidLEDSegmentException;
import frc.robot.subsystems.led.modes.Breathing;
import frc.robot.subsystems.led.modes.LEDMode;
import frc.robot.subsystems.led.modes.Solid;

/**
 * We are going to attach a name to each index in the LED modes array here.
 * Remember that all
 * indexes start at 0 and NOT 1
 */
public enum LEDSegment {

  // Add all aliases for segments below
  FrontLeft(1, new Solid(new LEDColour(255, 0, 0)).getClass()), // Set the LEDs to solid red
  BackLeft(2, new Breathing(new LEDColour(0, 255, 0)).getClass()), // Create a green breathing effect
  BackRight(3, new Breathing(new LEDColour(0, 255, 0)).getClass()), // Create a green breathing effect 
  FrontRight(4, new Solid(new LEDColour(255, 0, 0)).getClass()); // Set the LEDs to solid red

  public final int segmentNumber; // The segment number of the LED strip (starts at 1 and goes up)
  public LEDMode ledMode; // The mode of the LED strip

  private LEDSegment(int segmentNumber, Class<? extends LEDMode> defaultLedMode) {
    this.segmentNumber = segmentNumber;
    try {
      this.ledMode = defaultLedMode.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Error instantiating LEDMode", e);
    }
  }

  /**
   * Checks if the LED segment is a valid segment based off of the number of
   * segments in
   * LEDConstants.java
   *
   * @return True if it is within the index bounds, false if it isn't
   */
  public boolean isValid() {
    // Return a boolean based on if the segment number is greater than number of
    // segments
    return (this.getSegmentNumber() > LEDConstants.numberOfSegments);
  }

  /**
   * Sets the mode of the current segment
   *
   * @param ledMode The mode to set the LEDs to
   */
  public void setLedMode(Class<? extends LEDMode> ledMode) {
    // Guard clause to check if the segment is within the bounds of the number of
    // available segments
    if (!this.isValid()) {
      throw new InvalidLEDSegmentException(
          String.format(
              "Invalid LED segment: %d. Number of segments: %d",
              this.getSegmentNumber(),
              LEDConstants.numberOfSegments)); // Throw an exception with the segment information
    }

    try {
      this.ledMode = ledMode.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Error instantiating LEDMode", e);
    }

  }

  /**
   * Retrieves the true segment number of an LED segment
   *
   * @return The segment number of the LED segment
   */
  public int getSegmentNumber() {
    return this.segmentNumber;
  }

  /**
   * Gets the index of an LED segment
   *
   * @return The segment number minus one to account for it being an index
   */
  public int getIndex() {
    return (this.segmentNumber - 1);
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
