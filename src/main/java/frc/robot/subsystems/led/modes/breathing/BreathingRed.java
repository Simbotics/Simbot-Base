package frc.robot.subsystems.led.modes.breathing;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.led.LEDConstants;
import frc.robot.subsystems.led.modes.LEDMode;

public class BreathingRed extends LEDMode {
  private int cycle;

  @Override
  public void initialize() {
    this.cycle = 1;
    System.out.println("Starting the BreathingRed LED mode"); // Say we are using the BreathingRed LED mode
  }

  @Override
  public void periodic(int segmentIndex) {
    int minSegWindow = segmentIndex * LEDConstants.ledsPerSegment; // Set the start of the segment to display the LEDs
                                                                   // from
    int maxSegWindow = minSegWindow + LEDConstants.ledsPerSegment; // Set the end of the segment so we know where to
                                                                   // stop displaying LEDs

    Color breathingRedColour = new Color((Math.sin(Math.PI * this.cycle / 80.0) + 1.0) / 2.0, 0, 0);

    for (int i = minSegWindow; i < maxSegWindow; i++) {
      LEDConstants.buffer.setLED(i, breathingRedColour);
    }

    this.cycle++; // Increment the cycle by one;
  }
}
