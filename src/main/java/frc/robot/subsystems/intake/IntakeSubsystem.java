package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.intake.enums.IntakeGamepieces;
import frc.robot.subsystems.intake.enums.IntakeScoreType;

/**
 * This an example subsystem of our
 * intake from 2023
 * 
 * Each subsystem represents a different purpose of the robot
 * such as arms, LEDs, drivetrains, shooter, etc.
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
     * This is useful for updating subsystem-specific
     * state that you don't want to offload to a Command.
     * 
     * Teams should try to be consistent within their
     * own codebases about which responsibilities will
     * be handled by Commands, and which will be handled here.
     */
    @Override
    public void periodic() {
        SmartDashboard.putNumber("INTAKE CURRENT", this.pdp.getCurrent(IntakeConstants.INTAKE_MOTOR_CHANNEL));
        SmartDashboard.putNumber("INTAKE SPEED", this.intakeMotor.get());
    }

    /**
     * Runs a command that stops the intake
     * 
     * @return a command that stops the intake from moving
     */
    public Command stopIntakeCommand() {
        return runOnce(() -> this.intakeMotor.set(0))
                .ignoringDisable(true);
    }

    /**
     * Runs a command to score a gamepiece.
     * 
     * @param type          the type of the score you want to make
     * @param expectedPiece the type of gamepiece to expect when scoring
     * @return a command that scores a gamepiece (not including arm)
     */
    public Command intakeScoreCommand(IntakeScoreType type, IntakeGamepieces expectedPiece) {
        return run(() -> {

            switch (type) {
                case MID_CONE -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.MID_CONE), this)
                            .andThen(Commands.waitSeconds(0.5))
                            .andThen(stopIntakeCommand());
                }

                case MID_CONE_TIPPED -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.MID_CONE_TIPPED), this)
                            .andThen(Commands.waitSeconds(0.5))
                            .andThen(stopIntakeCommand());
                }

                case MID_CUBE -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.MID_CUBE), this)
                            .andThen(Commands.waitSeconds(0.5))
                            .andThen(stopIntakeCommand());
                }

                case MID_CUBE_AUTO -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.MID_CUBE_AUTO), this)
                            .andThen(Commands.waitSeconds(0.5))
                            .andThen(stopIntakeCommand());
                }

                case HIGH_CONE -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.HIGH_CONE), this)
                            .andThen(Commands.waitSeconds(0.5))
                            .andThen(stopIntakeCommand());
                }

                case HIGH_CONE_AUTO -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.HIGH_CONE_AUTO), this)
                            .andThen(Commands.waitSeconds(0.5))
                            .andThen(stopIntakeCommand());
                }

                case HIGH_CUBE -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.HIGH_CUBE), this)
                            .andThen(Commands.waitSeconds(0.5))
                            .andThen(stopIntakeCommand());
                }

                case HIGH_CUBE_AUTO -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.HIGH_CUBE_AUTO), this)
                            .andThen(Commands.waitSeconds(0.5))
                            .andThen(stopIntakeCommand());
                }

                case LOW -> {
                    if (expectedPiece.equals(IntakeGamepieces.CUBE)) {
                        Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.LOW_CUBE), this)
                                .andThen(Commands.waitSeconds(1))
                                .andThen(stopIntakeCommand());
                    } else {
                        Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.LOW_CONE), this)
                                .andThen(Commands.waitSeconds(1))
                                .andThen(stopIntakeCommand());
                    }
                }

                case LOW_AUTO -> {
                    if (expectedPiece.equals(IntakeGamepieces.CUBE)) {
                        Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.LOW_CUBE_AUTO), this)
                                .andThen(Commands.waitSeconds(1))
                                .andThen(stopIntakeCommand());
                    } else {
                        Commands.runOnce(() -> outtakeCommand(IntakeConstants.OuttakeSpeeds.LOW_CONE_AUTO), this)
                                .andThen(Commands.waitSeconds(1))
                                .andThen(stopIntakeCommand());
                    }
                }
            }
        }).andThen(stopIntakeCommand());
    }

    /**
     * Runs a command that intakes and holds a gamepiece
     * 
     * @param gamepiece the type of gamepiece to expect
     * @return a command
     */
    public Command intakeHoldCommand(IntakeGamepieces gamepiece) {
        return run(() -> {
            this.intakeMotor.set(IntakeConstants.INTAKING_SPEED);

            // this will keep the motor running as long as the current is low enough
            // this resulting in something similar to an if statement
            Commands.waitUntil(() -> this.pdp
                    .getCurrent(IntakeConstants.INTAKE_MOTOR_CHANNEL) < IntakeConstants.INTAKE_AMP_THRESHOLD);

            if (gamepiece.equals(IntakeGamepieces.CUBE)) {
                // wait a short amount of time so the gamepiece gets pulled in
                Commands.waitSeconds(IntakeConstants.INTAKE_CUBE_DELAY);
                this.intakeMotor.set(IntakeConstants.HOLD_CUBE_SPEED);
            }
            if (gamepiece.equals(IntakeGamepieces.CONE)) {
                // we have a cone, so run the motor at a higher speed
                Commands.waitSeconds(IntakeConstants.INTAKE_CONE_DELAY);
                this.intakeMotor.set(IntakeConstants.HOLD_CONE_SPEED);
            }
        }).finallyDo(() -> this.intakeMotor.set(0))
                .ignoringDisable(false);
    }

    /**
     * Runs a command that spits out its intake
     * 
     * @param speed speed to run the motor at, this is pre-inverted
     * @return a command that forces the intake to spit out its gamepiece
     */
    public Command outtakeCommand(double speed) {
        return runOnce(() -> this.intakeMotor.set(-speed));
    }

    /**
     * This method is similar to periodic,
     * but only runs in simulation mode.
     * 
     * Useful for updating subsystem-specific state
     * that needs to be maintained for simulations, such as
     * for updating edu.wpi.first.wpilibj.simulation classes
     * and setting simulated sensor readings.
     */
    @Override
    public void simulationPeriodic() {
        SmartDashboard.putNumber("INTAKE CURRENT", this.pdp.getCurrent(IntakeConstants.INTAKE_MOTOR_CHANNEL));
        SmartDashboard.putNumber("INTAKE SPEED", this.intakeMotor.get());
    }
}
