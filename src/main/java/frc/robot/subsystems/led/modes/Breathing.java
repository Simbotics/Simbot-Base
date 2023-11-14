package frc.robot.subsystems.led.modes;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.led.LEDColour;
import frc.robot.subsystems.led.LEDConstants;

public class Breathing extends LEDMode {
  private int cycle;
  private LEDColour ledColor;

  public Breathing(LEDColour ledColour) {
    this.ledColor = ledColour;
  }

  @Override
  public void initialize() {
    this.cycle = 1;
    System.out.println("Starting the Breathing mode"); // Say we are using the Breathing mode
  }

  @Override
  public void periodic(int segmentIndex) {
    int minSegWindow = segmentIndex * LEDConstants.ledsPerSegment;
    int maxSegWindow = minSegWindow + LEDConstants.ledsPerSegment;

    for (int i = minSegWindow; i < maxSegWindow; i++) {
      LEDConstants.buffer.setLED(i, calculateBreathingColor());
    }

    this.cycle++;
  }

  private Color calculateBreathingColor() {
    double breathingValue = (Math.sin(Math.PI * this.cycle / 80.0) + 1.0) / 2.0;
    return new Color(
        this.ledColor.getRed() * breathingValue,
        this.ledColor.getGreen() * breathingValue,
        this.ledColor.getBlue() * breathingValue);
  }
}
