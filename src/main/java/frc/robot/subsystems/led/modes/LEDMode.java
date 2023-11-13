package frc.robot.subsystems.led.modes;

public abstract class LEDMode {

  /** Runs when the mode is first called. This can be used to set constants/variabled */
  public abstract void initialize();

  /** Runs constantly every 20ms, this is where you want to calculate what your LEDs do */
  public abstract void periodic(int segmentIndex);
}
