package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.subsystems.intake.enums.IntakeGamepiece;
import frc.robot.subsystems.intake.states.ScoringState;

/**
 * This an example implementation of our intake subsystem from 2023.
 *
 * <p>Each subsystem represents a different purpose of the robot such as arms, LEDs, drivetrains,
 * shooter, etc.
 */
public class IntakeSubsystem extends SubsystemBase {

  private VictorSP intakeMotor = new VictorSP(IntakeConstants.INTAKE_MOTOR_CHANNEL);
  private PowerDistribution pdp;

  public IntakeSubsystem(PowerDistribution pdp) {
    this.pdp = pdp;
  }

  /**
   * This method runs once every 20ms.
   *
   * <p>This is useful for updating subsystem-specific state that you don't want to offload to a
   * Command.
   *
   * <p>Teams should try to be consistent within their own codebases about which responsibilities
   * will be handled by Commands, and which will be handled here.
   */
  @Override
  public void periodic() {
    SmartDashboard.putNumber(
        "INTAKE CURRENT", this.pdp.getCurrent(IntakeConstants.INTAKE_MOTOR_CHANNEL));
    SmartDashboard.putNumber("INTAKE SPEED", this.intakeMotor.get());
  }

  /**
   * Runs a command that stops the intake
   *
   * @return a command that stops the intake
   */
  public Command stopIntakeCommand() {
    return runOnce(() -> this.intakeMotor.set(0)).ignoringDisable(true);
  }

  /**
   * Runs a command to score a gamepiece.
   *
   * @param scoringState the state to run the intake in
   * @param expectedPiece the type of gamepiece to expect when scoring
   * @return a command that scores a gamepiece
   */
  public Command intakeScoreCommand(ScoringState scoringState, IntakeGamepiece expectedGamepiece) {

    SmartDashboard.putString("INTAKE STATE", scoringState.getStateName());

    return run(() -> {
          Commands.runOnce(
                  () -> outtakeCommand(scoringState.getOuttakeSpeed(expectedGamepiece)), this)
              .andThen(Commands.waitSeconds(0.5))
              .andThen(stopIntakeCommand());
        })
        .andThen(stopIntakeCommand());
  }

  /**
   * Runs a command that intakes and holds a gamepiece
   *
   * @param gamepiece the type of gamepiece to expect
   * @return a command that forces the intake to hold the specified gamepiece
   */
  public Command intakeHoldCommand(IntakeGamepiece gamepiece) {
    return run(() -> {
          this.intakeMotor.set(IntakeConstants.INTAKING_SPEED);

          // this will keep the motor running as long as the current is low enough
          // this results in something similar to an if statement
          Commands.waitUntil(
              () ->
                  this.pdp.getCurrent(IntakeConstants.INTAKE_MOTOR_CHANNEL)
                      < IntakeConstants.INTAKE_AMP_THRESHOLD);

          if (gamepiece.equals(IntakeGamepiece.CUBE)) {
            // wait a short amount of time so the gamepiece gets pulled in
            Commands.waitSeconds(IntakeConstants.INTAKE_CUBE_DELAY);
            this.intakeMotor.set(IntakeConstants.HOLD_CUBE_SPEED);
          }
          if (gamepiece.equals(IntakeGamepiece.CONE)) {
            // we have a cone, so run the motor at a higher speed
            Commands.waitSeconds(IntakeConstants.INTAKE_CONE_DELAY);
            this.intakeMotor.set(IntakeConstants.HOLD_CONE_SPEED);
          }
        })
        .finallyDo(() -> this.intakeMotor.set(0))
        .ignoringDisable(false);
  }

  /**
   * Runs a command that spits out its gamepiece
   *
   * @param speed speed to run the motor at, this is pre-inverted
   * @return a command that forces the intake to spit out its gamepiece
   */
  public Command outtakeCommand(double speed) {
    return runOnce(() -> this.intakeMotor.set(-speed));
  }

  /**
   * This method is similar to periodic, but only runs in simulation mode.
   *
   * <p>Useful for updating subsystem-specific state that needs to be maintained for simulations,
   * such as for updating edu.wpi.first.wpilibj.simulation classes and setting simulated sensor
   * readings.
   */
  @Override
  public void simulationPeriodic() {
    SmartDashboard.putNumber(
        "INTAKE CURRENT", this.pdp.getCurrent(IntakeConstants.INTAKE_MOTOR_CHANNEL));
    SmartDashboard.putNumber("INTAKE SPEED", this.intakeMotor.get());
  }
}
