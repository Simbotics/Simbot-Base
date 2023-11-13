package frc.robot.subsystems.led.modes;

import frc.robot.subsystems.led.LEDConstants;

public class SolidRed extends LEDMode {

    @Override
    public void initialize() {
        System.out.println("Starting the SolidRed LED mode");
    }

    @Override
    public void periodic(int segmentIndex) {
        int minSegWindow = segmentIndex * LEDConstants.ledsPerSegment;
        int maxSegWindow = minSegWindow + LEDConstants.ledsPerSegment;

        for (int i = minSegWindow; i < maxSegWindow; i++) {
            LEDConstants.buffer.setRGB(i, 255, 0, 0);
        }
        
    }
}
