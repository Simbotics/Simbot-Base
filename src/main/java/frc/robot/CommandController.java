package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * The {@code CommandController} class acts as a wrapper around an existing
 * {@code CommandXboxController}, providing developers with the ability to rename its
 * methods for improved clarity on the functionality of each button.
 *
 * <p>By extending this class, developers can use more intuitive method names
 * that align with the specific actions associated with each button on the
 * controller. This aims to enhance code readability and understanding, making it
 * easier for developers to work with the controller in a more expressive manner.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * CommandController driver = new CommandController(0);
 * driver.greenButton().onTrue(() -> {}); // insert command</pre>
 * 
 * @author Simbotics
 * 
 * @see CommandXboxController
 */
public class CommandController {
    CommandXboxController joystick;

    public CommandController(int port) {
        this.joystick = new CommandXboxController(port);
    }
    
    /**
   * Gets the left joystick X axis.
   *
   * @return The left joystick X axis.
   */
  public double getLeftX() {
    return this.joystick.getLeftX();
  }

  /**
   * Gets the left joystick Y axis.
   *
   * @return The left joystick Y axis.
   */
  public double getLeftY() {
    return -this.joystick.getLeftY();
  }

  /**
   * Gets the pressed state of the left trigger.
   *
   * @return The pressed state of the left trigger as a trigger.
   */
  public Trigger leftTrigger() {
    return this.joystick.rightTrigger();
  }

  /**
   * Gets the pressed state of the right trigger.
   *
   * @return The pressed state of the right trigger as a trigger.
   */
  public Trigger rightTrigger() {
    return this.joystick.rightTrigger();
  }

  /**
   * Gets the right joystick X axis.
   *
   * @return The right joystick X axis.
   */
  public double getRightX() {
    return this.joystick.getRightX();
  }

  /**
   * Gets the right joystick Y axis.
   *
   * @return The right joystick Y axis.
   */
  public double getRightY() {
    return -this.joystick.getRightY();
  }

  /**
   * Gets the green buttons trigger.
   *
   * @return the trigger for the green button
   */
  public Trigger greenButton() {
    return this.joystick.a();
  }

  /**
   * Gets the blue buttons trigger.
   *
   * @return the trigger for the blue button
   */
  public Trigger blueButton() {
    return this.joystick.x();
  }

  /**
   * Gets the red buttons trigger.
   *
   * @return the trigger for the red button
   */
  public Trigger redButton() {
    return this.joystick.b();
  }

  /**
   * Gets the yellow buttons trigger.
   *
   * @return the trigger for the yellow button
   */
  public Trigger yellowButton() {
    return this.joystick.y();
  }

  /**
   * Gets the back button trigger
   * 
   * @return the trigger for the back button
   */
  public Trigger backButton() {
    return this.joystick.back();
  }

/**
   * Gets the back button trigger
   * 
   * @return the trigger for the back button
   */
  public Trigger startButton() {
    return this.joystick.start();
  }

  /**
   * Gets the pressed state of the top left back button.
   *
   * @return The value of the pressed state as a trigger.
   */
  public Trigger leftBumper() {
    return this.joystick.leftBumper();
  }

  /**
   * Gets the pressed state of the top right back button.
   *
   * @return The value of the pressed state as a trigger.
   */
  public Trigger rightBumper() {
    return this.joystick.rightBumper();
  }

  /**
   * Gets the pressed state of the left joystick button.
   *
   * @return The state of the left joystick button as a trigger.
   */
  public Trigger leftStickClick() {
    return this.joystick.button(9);
  }

  /**
   * Gets the pressed state of the right joystick button.
   *
   * @return The state of the of the right joystick button as a boolean.
   */
  public Trigger rightStickClick() {
    return this.joystick.button(10);
  }

  /** POV methods. */
  public int getPOVVal() {
    return this.joystick.getHID().getPOV(0);
  }

  /**
   * Get trigger for when POV angle is 180
   * 
   * @return trigger for when POV is 180
   */
  public Trigger POVDown() {
    return this.joystick.povDown();
  }

  /**
   * Get trigger for when POV angle is 90
   * 
   * @return trigger for when POV is 90
   */
  public Trigger POVRight() {
    return this.joystick.povRight();
  }

  
  /**
   * Get trigger for when POV angle is 0
   * 
   * @return trigger for when POV is 0
   */
  public Trigger POVUp() {
    return this.joystick.povUp();
  }

  
  /**
   * Get trigger for when POV angle is 270
   * 
   * @return trigger for when POV is 270
   */
  public Trigger POVLeft() {
    return this.joystick.povLeft();
  }

  /**
   * Sets the amount of rumble for supported controllers.
   *
   * @param rumble Amount of rumble; Either 0 or 1.
   */
  public void setRumble(double rumble) {
    this.joystick.getHID().setRumble(RumbleType.kLeftRumble, rumble);
    this.joystick.getHID().setRumble(RumbleType.kRightRumble, rumble);
  }
}
