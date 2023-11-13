package frc.robot.subsystems.led;

/**
 * This is only used for setting solid colour states. Please see the LEDModes.java class for special
 * LED modes
 */
public class LEDColour {
  int red, green, blue;

  /**
   * The
   *
   * @param red
   * @param green
   * @param blue
   */
  public LEDColour(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Copies the RGB values from an LEDState object to the current one
   *
   * @param ledState The LEDState to copy the RGB values from
   * @return A new LEDState with the RGB values mirrores from the supplied LEDState
   */
  public LEDColour copy(LEDColour ledState) {
    this.red = ledState.red;
    this.green = ledState.green;
    this.blue = ledState.blue;

    return new LEDColour(this.red, this.green, this.blue);
  }

  /**
   * Resets the RBG values of the LEDState back to 0, 0, 0. (white)
   *
   * @return A new LEDState with the RGB values all being 0
   */
  public LEDColour reset() {
    this.red = 0;
    this.green = 0;
    this.blue = 0;

    return new LEDColour(this.red, this.green, this.blue);
  }

  /**
   * Gets the red value of an LED state object
   *
   * @return The integer value of the red value in the RGB sequence
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets the green value of an LED state object
   *
   * @return The integer value of the green value in the RGB sequence
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the blue value of an LED state object
   *
   * @return The integer value of the blue value in the RGB sequence
   */
  public int getBlue() {
    return this.blue;
  }
}