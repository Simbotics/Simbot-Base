package frc.robot.subsystems.led;

/**
 * This is only used for setting solid colour states. Please see the LEDModes.java class for special
 * LED modes
 */
public class LEDColour {
  private double red, green, blue;

  public LEDColour(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public LEDColour(double red, double green, double blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Copies the RGB values from an LEDState object to the current one
   *
   * @param ledState The LEDState to copy the RGB values from
   * @return The current instane of LEDColour with the new RGB values
   */
  public LEDColour copy(LEDColour ledState) {
    this.red = ledState.red;
    this.green = ledState.green;
    this.blue = ledState.blue;

    return this;
  }

  /**
   * Gets the red value of an LED state object
   *
   * @return The double value of the red value in the RGB sequence
   */
  public double getRed() {
    return this.red;
  }

  /**
   * Gets the red value of an LED state object as an integer
   *
   * @return The integer value of the red value in the RGB sequence
   */
  public int getRedInt() {
    return (int) this.red;
  }

  /**
   * Gets the green value of an LED state object
   *
   * @return The double value of the green value in the RGB sequence
   */
  public double getGreen() {
    return this.green;
  }

  /**
   * Gets the green value of an LED state object as an integer
   *
   * @return The integer value of the green value in the RGB sequence
   */
  public int getGreenInt() {
    return (int) this.green;
  }

  /**
   * Gets the blue value of an LED state object
   *
   * @return The integer value of the blue value in the RGB sequence
   */
  public double getBlue() {
    return this.blue;
  }

  /**
   * Gets the blue value of an LED state object as an integer
   *
   * @return The integer value of the blue value in the RGB sequence
   */
  public int getBlueInt() {
    return (int) this.blue;
  }
}
