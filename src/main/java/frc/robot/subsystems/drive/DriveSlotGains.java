package frc.robot.subsystems.drive;

import com.ctre.phoenix6.configs.Slot0Configs;

public class DriveSlotGains extends Slot0Configs {
  public DriveSlotGains(double kP, double kI, double kD, double kV, double kS) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.kV = kV;
    this.kS = kS;
  }
}
