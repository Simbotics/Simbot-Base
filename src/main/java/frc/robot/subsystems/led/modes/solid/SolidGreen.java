package frc.robot.subsystems.led.modes.solid;

import frc.robot.subsystems.led.LEDColour;
import frc.robot.subsystems.led.LEDConstants;
import frc.robot.subsystems.led.modes.LEDMode;

public class SolidGreen extends LEDMode {

  @Override
  public void initialize() {
    System.out.println(
        "Starting the SolidGreen LED mode"); // Say we are using the SolidGreen LED mode
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

    LEDColour greenLedColour =
        new LEDColour(0, 255, 0); // Create a new RGB sequence with only green value being added

    // For every LED in the segment window, we are going to set its colour
    for (int i = minSegWindow; i < maxSegWindow; i++) {
      LEDConstants.buffer.setRGB(
          i, greenLedColour.getRedInt(), greenLedColour.getGreenInt(), greenLedColour.getBlueInt());
    }
  }
}
