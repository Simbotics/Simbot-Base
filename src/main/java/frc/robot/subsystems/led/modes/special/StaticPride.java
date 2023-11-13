package frc.robot.subsystems.led.modes.special;

import frc.robot.subsystems.led.LEDConstants;
import frc.robot.subsystems.led.modes.LEDMode;

public class StaticPride extends LEDMode {

  @Override
  public void initialize() {
    System.out.println(
        "Starting the StaticPride LED mode"); // Say we are using the StaticPride LED mode
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

    for (int i = minSegWindow; i < maxSegWindow; i++) {
      LEDConstants.buffer.setHSV(i, (int) ((i * 5) % 180), 255, 255);
    }
  }
}
