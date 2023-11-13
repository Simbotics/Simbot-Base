package frc.robot.subsystems.led.modes.special;

import frc.robot.subsystems.led.LEDConstants;
import frc.robot.subsystems.led.modes.LEDMode;

public class Rainbow extends LEDMode {

  @Override
  public void initialize() {
    System.out.println("Starting the Rainbow LED mode"); // Say we are using the Rainbow LED mode
  }

  @Override
  public void periodic(int segmentIndex) {
    int minSegWindow = segmentIndex * LEDConstants.ledsPerSegment; // Set the start of the segment to display the LEDs from
    int maxSegWindow = minSegWindow + LEDConstants.ledsPerSegment; // Set the end of the segment so we know where to stop displaying LEDs

    double rainbowSpeed = 15.0; // Set the speed of the rainbow, the higher the number, the faster it is
    double hue = System.currentTimeMillis() / rainbowSpeed; // Calculate the hue based off the speed and the current system time

    for (int i = minSegWindow; i < maxSegWindow; i++) {
      LEDConstants.buffer.setHSV(i, (int) ((hue + i * 5) % 180), 255, 255);
    }
  }
}
