package frc.robot.subsystems.intake;

public class IntakeConstants {

    /** Used when scoring/outtaking */
    public class OuttakeSpeeds {
        public static final double LOW_CUBE = 0.3;
        public static final double LOW_CONE = 0.6;
        public static final double LOW_CUBE_AUTO = 0.3;
        public static final double LOW_CONE_AUTO = 1.0;
    
        public static final double MID_CONE = 0.1;
        public static final double MID_CONE_TIPPED = 0.8;
        public static final double MID_CUBE = 0.25;
        public static final double MID_CUBE_AUTO = 0.2;
    
        public static final double HIGH_CONE = 0.15;
        public static final double HIGH_CONE_AUTO = 0.15;
        public static final double HIGH_CUBE = 0.55;
        public static final double HIGH_CUBE_AUTO = 0.6;
    }

    public static final double INTAKING_SPEED = 0.95;

    public static final int INTAKE_MOTOR_CHANNEL = 6;
    public static final double INTAKE_AMP_THRESHOLD = 16;

    // 20ms per periodic cycle * number of periodic cycles / 1000 to get as millis
    public static final double INTAKE_CUBE_DELAY = (20 * 15) / 1000;
    public static final double INTAKE_CONE_DELAY = (20 * 3) / 1000;

    public static final double HOLD_CONE_SPEED = 0.18;
    public static final double HOLD_CUBE_SPEED = 0.15;


}
