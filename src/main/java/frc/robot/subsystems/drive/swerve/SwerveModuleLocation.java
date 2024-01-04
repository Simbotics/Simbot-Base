package frc.robot.subsystems.drive.swerve;

import edu.wpi.first.math.util.Units;

public class SwerveModuleLocation {
    private double xPosition, yPosition;

    /**
     * Stores the x and y position of a swerve module
     * @param xPosition The X position of the module relative to the center of the robot
     * @param yPosition The Y position of the module relative to the center of the robot
     */
    public SwerveModuleLocation(double xPosition, double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * Returns the X position of the module relative to the center of the robot in inches
     * @return The raw X position of the module in inches
     */
    public double getXPositionRaw() {
        return this.xPosition;
    }

    /**
     * Returns the Y position of the module relative to the center of the robot in inches
     * @return The raw Y position of the module in inches
     */
    public double getYPositionRaw() {
        return this.yPosition;
    }

    /**
     * Returns the X position of the module relative to the center of the robot in meters
     * @return The calculated X position of the module in meters
     */
    public double getXPositionCalculated() {
        return Units.inchesToMeters(this.xPosition);
    }

    /**
     * Returns the Y position of the module relative to the center of the robot in meters
     * @return The calculated Y position of the module in meters
     */
    public double getYPositionCalculated() {
        return Units.inchesToMeters(this.yPosition);
    }
}
