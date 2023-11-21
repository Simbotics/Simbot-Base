package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This an example subsystem of our
 * intake from 2023
 * 
 * Each subsystem represents a different purpose of the robot
 * such as arms, LEDs, drivetrains, shooter, etc.
 */
public class Intake extends SubsystemBase {

    // create the motor
    VictorSP intakeMotor = new VictorSP(6);
    // for this use, we need a PDP, which is not defined right now
    PowerDistribution pdp;

    int intakeCycles = 0; // could be replace with waitSeconds()?

    enum ScoreType {
        HIGH_CONE,
        HIGH_CONE_AUTO,
        HIGH_CUBE,
        HIGH_CUBE_AUTO,
        MID_CONE,
        MID_CONE_TIPPED,
        MID_CUBE,
        MID_CUBE_AUTO,
        LOW,
        LOW_AUTO
    }

    public Intake(PowerDistribution pdp) {
        this.pdp = pdp;
    }

    /**
     * This method runs once every 20ms.
     * 
     * This is useful for updating subsystem-specific
     * state that you don't want to offload to a Command.
     * Teams should try to be consistent within their
     * own codebases about which responsibilities will
     * be handled by Commands, and which will be handled here.
     */
    @Override
    public void periodic() {
        // TODO: find things to put in here, if there is anything
    }

    /**
     * Runs an command that stops the intake
     * 
     * @return a command that stops the intake from moving
     */
    public Command stopIntakeCommand() {
        return runOnce(() -> {
            this.intakeCycles = 0;
            // this.scoringCycles = 0;
            this.intakeMotor.set(0);
        }).ignoringDisable(true);
    }

    /**
     * Runs a command to score a gamepiece.
     * 
     * @param type the type of the score you want to make
     * @return a command that scores a gamepiece (not including arm)
     */
    public Command intakeScoreCommand(ScoreType type, boolean expectingCube) {
        return run(() -> {

            // TODO: dedisgustify this code?
            // change how we are scoring by providing a different type
            switch (type) {
                case MID_CONE -> {
                    this.intakeMotor.set(-0.1);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case MID_CONE_TIPPED -> {
                    this.intakeMotor.set(-0.8);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case MID_CUBE -> {
                    this.intakeMotor.set(-0.25);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case MID_CUBE_AUTO -> {
                    this.intakeMotor.set(-0.2);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case HIGH_CONE -> {
                    this.intakeMotor.set(-0.15);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case HIGH_CONE_AUTO -> {
                    this.intakeMotor.set(-0.15);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case HIGH_CUBE -> {
                    this.intakeMotor.set(-0.55);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case HIGH_CUBE_AUTO -> {
                    this.intakeMotor.set(-0.6);
                    Commands.waitSeconds(0.5);
                    Commands.runOnce(() -> stopIntakeCommand(), this);
                }

                case LOW -> {
                    if (expectingCube) {
                        this.intakeMotor.set(-0.3);
                        Commands.waitSeconds(1);
                        Commands.runOnce(() -> stopIntakeCommand(), this);
                    } else {
                        this.intakeMotor.set(-0.6);
                        Commands.waitSeconds(1);
                        Commands.runOnce(() -> stopIntakeCommand(), this);
                    }
                }

                case LOW_AUTO -> {
                    if (expectingCube) {
                        this.intakeMotor.set(-0.3);
                        Commands.waitSeconds(1);
                        Commands.runOnce(() -> stopIntakeCommand(), this);
                    } else {
                        this.intakeMotor.set(-1.0);
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
    public Command intakeHoldCommand(boolean expectingCube) {
        return run(() -> {
            // TODO: Move constants to constants file, use guard clauses?
            intakeMotor.set(0.95); // set motor speed

            // if current is high enough, it means we have a gamepiece
            if (pdp.getCurrent(6) > 16) {
                intakeCycles++;

                if (expectingCube && this.intakeCycles > 12) {
                    // we have a cube, so run the motor at 15%
                    intakeMotor.set(0.15);
                } else if (this.intakeCycles > 15) {
                    // we have a cone, so run the motor at 18%
                    intakeMotor.set(0.18);
                }
            } else {
                intakeCycles = 0; // reset cycles when theres nothing in the intake
            }
        }).finallyDo(() -> {
            intakeMotor.set(0); // set speed to 0 after stop
        }).ignoringDisable(false);
    }

    /**
     * Runs a command that spits out its intake
     * 
     * @return a command that forces the intake to spit out its gamepiece
     */
    public Command forceOuttakeCommand() {
        return runOnce(() -> {
            intakeMotor.set(-0.6);
            Commands.waitSeconds(2);
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
        // TODO: fill
    }
}