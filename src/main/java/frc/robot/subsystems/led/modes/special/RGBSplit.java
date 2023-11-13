package frc.robot.subsystems.led.modes.special;

import frc.robot.subsystems.led.LEDColour;
import frc.robot.subsystems.led.LEDConstants;
import frc.robot.subsystems.led.modes.LEDMode;

public class RGBSplit extends LEDMode {

  @Override
  public void initialize() {
    System.out.println("Starting the RGBSplit LED mode"); // Say we are using the RGBSplit LED mode
  }

  @Override
  public void periodic(int segmentIndex) {
    int minSegWindow =
        segmentIndex
            * LEDConstants.ledsPerSegment; // Set the start of the segment to display the LEDs from
    int maxSegWindow =
        minSegWindow
            + LEDConstants
                .ledsPerSegment; // Set the end of the segment so we know where to stop displaying
    // LEDs

    // Divide the segment into three equal parts
    int segmentSize = LEDConstants.ledsPerSegment / 3;

    // Set the color for each part
    LEDColour redLedColour = new LEDColour(255, 0, 0);
    LEDColour greenLedColour = new LEDColour(0, 255, 0);
    LEDColour blueLedColour = new LEDColour(0, 0, 255);

    // For every LED in the segment window, set its color based on the part of the segment it
    // belongs to
    for (int i = minSegWindow; i < maxSegWindow; i++) {
      if (i < minSegWindow + segmentSize) {
        LEDConstants.buffer.setRGB(
            i, redLedColour.getRedInt(), redLedColour.getGreenInt(), redLedColour.getBlueInt());
      } else if (i < minSegWindow + 2 * segmentSize) {
        LEDConstants.buffer.setRGB(
            i,
            greenLedColour.getRedInt(),
            greenLedColour.getGreenInt(),
            greenLedColour.getBlueInt());
      } else {
        LEDConstants.buffer.setRGB(
            i, blueLedColour.getRedInt(), blueLedColour.getGreenInt(), blueLedColour.getBlueInt());
      }
    }
  }
}
