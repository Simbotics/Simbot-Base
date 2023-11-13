package frc.robot.subsystems.led;

import frc.robot.subsystems.led.exceptions.InvalidLEDSegmentException;
import frc.robot.subsystems.led.modes.LEDMode;
import frc.robot.subsystems.led.modes.solid.SolidRed;

/**
 * We are going to attach a name to each index in the LED modes array here. Remember that all
 * indexes start at 0 and NOT 1
 */
public enum LEDSegment {

  // Add all aliases for segments below
  FrontLeft(1, SolidRed.class),
  BackLeft(2, SolidRed.class),
  BackRight(3, SolidRed.class),
  FrontRight(4, SolidRed.class);

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
   * Checks if the LED segment is a valid segment based off of the number of segments in
   * LEDConstants.java
   *
   * @return True if it is within the index bounds, false if it isn't
   */
  public boolean isValid() {
    // Checks if the segment number is greater than the number of segments
    if (this.getSegmentNumber() > LEDConstants.numberOfSegments) {
      return false; // If it isn't within bounds, return false
    } else {
      return true; // If it IS within bounds, return true because it's valid
    }
  }

  /**
   * Sets the mode of the current segment
   *
   * @param ledMode The mode to set the LEDs to
   */
  public void setLedMode(Class<? extends LEDMode> ledMode) {
    // Checks if the segment is within the bounds of the number of available
    // segments
    if (this.isValid()) {
      try {
        this.ledMode = ledMode.getDeclaredConstructor().newInstance();
      } catch (Exception e) {
        throw new RuntimeException("Error instantiating LEDMode", e);
      }
    } else {
      throw new InvalidLEDSegmentException(
          String.format(
              "Invalid LED segment: %d. Number of segments: %d",
              this.getSegmentNumber(),
              LEDConstants.numberOfSegments)); // Throw an exception with the segment information
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
