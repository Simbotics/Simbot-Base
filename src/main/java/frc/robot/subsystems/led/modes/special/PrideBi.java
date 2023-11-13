package frc.robot.subsystems.led.modes.special;

import frc.robot.subsystems.led.LEDColour;
import frc.robot.subsystems.led.LEDConstants;
import frc.robot.subsystems.led.modes.LEDMode;

public class PrideBi extends LEDMode {

  @Override
  public void initialize() {
    System.out.println("Starting the PrideBi LED mode"); // Say we are using the PrideBi LED mode
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
    LEDColour magentaLedColour = new LEDColour(214, 2, 112);
    LEDColour purpleLedColour = new LEDColour(155, 79, 150);
    LEDColour royalBlueLedColour = new LEDColour(0, 56, 168);

    // For every LED in the segment window, set its color based on the part of the segment it
    // belongs to
    for (int i = minSegWindow; i < maxSegWindow; i++) {
      if (i < minSegWindow + segmentSize) {
        LEDConstants.buffer.setRGB(
            i,
            magentaLedColour.getRedInt(),
            magentaLedColour.getGreenInt(),
            magentaLedColour.getBlueInt());
      } else if (i < minSegWindow + 2 * segmentSize) {
        LEDConstants.buffer.setRGB(
            i,
            purpleLedColour.getRedInt(),
            purpleLedColour.getGreenInt(),
            purpleLedColour.getBlueInt());
      } else {
        LEDConstants.buffer.setRGB(
            i,
            royalBlueLedColour.getRedInt(),
            royalBlueLedColour.getGreenInt(),
            royalBlueLedColour.getBlueInt());
      }
    }
  }
}
