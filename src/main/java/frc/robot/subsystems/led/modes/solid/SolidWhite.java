package frc.robot.subsystems.led.modes.solid;

import frc.robot.subsystems.led.LEDColour;
import frc.robot.subsystems.led.LEDConstants;
import frc.robot.subsystems.led.modes.LEDMode;

public class SolidWhite extends LEDMode {

  @Override
  public void initialize() {
    System.out.println("Starting the SolidWhite LED mode"); // Say we are using the WolidWhite LED mode
  }

  @Override
  public void periodic(int segmentIndex) {
    int minSegWindow = segmentIndex * LEDConstants.ledsPerSegment; // Set the start of the segment to display the LEDs from
    int maxSegWindow = minSegWindow + LEDConstants.ledsPerSegment; // Set the end of the segment so we know where to stop displaying LEDs

    LEDColour whiteLedColour = new LEDColour(255, 255, 255); // Create a new RGB sequence with all values 255

    // For every LED in the segment window, we are going to set its colour
    for (int i = minSegWindow; i < maxSegWindow; i++) {
      LEDConstants.buffer.setRGB(i, whiteLedColour.getRedInt(), whiteLedColour.getGreenInt(), whiteLedColour.getBlueInt());
    }
  }
}
