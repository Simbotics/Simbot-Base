package frc.robot.subsystems.led.modes;

import frc.robot.subsystems.led.LEDColour;
import frc.robot.subsystems.led.LEDConstants;

public class Solid extends LEDMode {
    private LEDColour ledColour;

    public Solid(LEDColour ledColour) {
        this.ledColour = ledColour;
    }

  @Override
  public void initialize() {
    System.out.println(
        "Starting the Solid LED mode"); // Say we are using the Solid LED mode
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
    // For every LED in the segment window, we are going to set its colour
    for (int i = minSegWindow; i < maxSegWindow; i++) {
      LEDConstants.buffer.setRGB(
          i, ledColour.getRedInt(), ledColour.getGreenInt(), ledColour.getBlueInt());
    }
  }
}
