package frc.robot.subsystems.intake.states.scoring.level;

import frc.robot.subsystems.intake.IntakeConstants;
import frc.robot.subsystems.intake.states.ScoringState;

public class LowAuto extends ScoringState {

  LowAuto() {
    super(
        IntakeConstants.OuttakeSpeeds.LOW_CUBE_AUTO,
        IntakeConstants.OuttakeSpeeds.LOW_CONE_AUTO,
        "Low Auto");
  }
}
