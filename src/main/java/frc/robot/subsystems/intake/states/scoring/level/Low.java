package frc.robot.subsystems.intake.states.scoring.level;

import frc.robot.subsystems.intake.IntakeConstants;
import frc.robot.subsystems.intake.states.ScoringState;

public class Low extends ScoringState {

    Low() {
        super(IntakeConstants.OuttakeSpeeds.LOW_CUBE, IntakeConstants.OuttakeSpeeds.LOW_CONE, "Low");
    }
}
