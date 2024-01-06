package frc.robot.subsystems.intake.states;


import frc.robot.subsystems.intake.enums.IntakeGamepiece;


public abstract class ScoringState {
  double outtakeSpeed;
  double cubeOuttakeSpeed;
  double coneOuttakeSpeed;

  String stateName;

  /**
   * Creates a scoring state with the same outtake speed for cubes and cones
   *
   * @param outtakeSpeed The speed that should be used to outtake the gamepiece
   * @param stateName The name of the scoring state that should be used for logging
   */
  protected ScoringState(double outtakeSpeed, String stateName) {
    this.outtakeSpeed = outtakeSpeed;
    this.stateName = stateName;
  }

  /**
   * Creates a scoring state with different outtake speeds for cubes and cones
   *
   * @param cubeOuttakeSpeed The speed that should be used to outtake the cube gamepiece
   * @param coneOuttakeSpeed The speed that should be used to outtake the cone gamepiece
   * @param stateName The name of the scoring state that should be used for logging
   */
  protected ScoringState(double cubeOuttakeSpeed, double coneOuttakeSpeed, String stateName) {
    this.cubeOuttakeSpeed = cubeOuttakeSpeed;
    this.coneOuttakeSpeed = coneOuttakeSpeed;
    this.stateName = stateName;
  }

  /**
   * Gets the name of the scoring state
   *
   * @return The name of the scoring state
   */
  public String getStateName() {
    return this.stateName;
  }

  /**
   * Gets the outtake speed for the scoring state depending on the gamepiece that is expected to be
   * scored
   *

   * @param expectedGamepiece The gamepiece that is expected to be scored
   * @return The outtake speed for the scoring state
   */
  public double getOuttakeSpeed(IntakeGamepiece expectedGamepiece) {

    if (expectedGamepiece == null) {
      return this.outtakeSpeed;
    }

    // Based on the gamepiece that is expected to be scored, return the appropriate outtake speed
    switch (expectedGamepiece) {
      case CUBE:
        return this.cubeOuttakeSpeed;
      case CONE:
        return this.coneOuttakeSpeed;
      default:
        return 0.0;
    }
  }
}
