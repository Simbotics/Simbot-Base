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

    // create the motor
    private VictorSP intakeMotor = new VictorSP(IntakeConstants.INTAKE_MOTOR_CHANNEL);
    // for this use, we need a PDP, which is not defined right now
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
     * Runs an command that stops the intake
     * 
     * @return a command that stops the intake from moving
     */
    public Command stopIntakeCommand() {
        return runOnce(() -> {
            this.intakeMotor.set(0);
        }).ignoringDisable(true);
    }

    /**
     * Runs a command to score a gamepiece.
     * 
     * @param type the type of the score you want to make
     * @return a command that scores a gamepiece (not including arm)
     */
    public Command intakeScoreCommand(IntakeScoreType type, IntakeGamepieces expectedPiece) {
        return run(() -> {

            // change how we are scoring by providing a different type
            switch (type) {
                case MID_CONE -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.MID_CONE), this);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case MID_CONE_TIPPED -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.MID_CONE_TIPPED), this);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case MID_CUBE -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.MID_CUBE), this);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case MID_CUBE_AUTO -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.MID_CUBE_AUTO), this);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case HIGH_CONE -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.HIGH_CONE), this);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case HIGH_CONE_AUTO -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.HIGH_CONE_AUTO), this);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case HIGH_CUBE -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.HIGH_CUBE), this);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case HIGH_CUBE_AUTO -> {
                    Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.HIGH_CUBE_AUTO), this);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case LOW -> {
                    if (expectedPiece.equals(IntakeGamepieces.CUBE)) {
                        Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.LOW_CUBE), this);
                        Commands.waitSeconds(1);
                        Commands.runOnce(() -> stopIntakeCommand(), this);
                    } else {
                        Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.LOW_CONE), this);
                        Commands.waitSeconds(1);
                        Commands.runOnce(() -> stopIntakeCommand(), this);
                    }
                }

                case LOW_AUTO -> {
                    if (expectedPiece.equals(IntakeGamepieces.CUBE)) {
                        Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.LOW_CUBE_AUTO), this);
                        Commands.waitSeconds(1);
                        Commands.runOnce(() -> stopIntakeCommand(), this);
                    } else {
                        Commands.runOnce(() -> outtakeCommand(IntakeConstants.OutakeSpeeds.LOW_CONE_AUTO), this);
                        Commands.waitSeconds(1);
                        Commands.runOnce(() -> stopIntakeCommand(), this);
                    }
                }
            }
        }).andThen(stopIntakeCommand());
    }

    /**
     * Runs a command that intakes and holds a gamepiece
     * 
     * @param expectingCube determines if it'll use cube mode or cone mode
     * @return a command that intakes and holds a gamepiece
     */
    public Command intakeHoldCommand(IntakeGamepieces gamepiece) {
        return run(() -> {
            this.intakeMotor.set(0.95); // set motor speed
            
            Commands.waitUntil(() -> this.pdp.getCurrent(6) < 16);

            // wait a short amount of time so the gamepiece gets pulled in
            Commands.waitSeconds(IntakeConstants.INTAKE_CUBE_DELAY);
            if (gamepiece.equals(IntakeGamepieces.CUBE)) {
                // we have a cube, so run the motor at 15%
                this.intakeMotor.set(IntakeConstants.HOLD_CUBE_SPEED);
            }
            Commands.waitSeconds(IntakeConstants.INTAKE_CONE_DELAY);
            if (gamepiece.equals(IntakeGamepieces.CONE)) {
                // we have a cone, so run the motor at 18%
                this.intakeMotor.set(IntakeConstants.HOLD_CONE_SPEED);
            }
        }).finallyDo(() -> {
            this.intakeMotor.set(0); // stop motor when command is stopped/interrupted
        }).ignoringDisable(false);
    }

    /**
     * Runs a command that spits out its intake
     * 
     * @return a command that forces the intake to spit out its gamepiece
     */
    public Command outtakeCommand(double speed) {
        return runOnce(() -> {
            this.intakeMotor.set(-speed);
        });
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
