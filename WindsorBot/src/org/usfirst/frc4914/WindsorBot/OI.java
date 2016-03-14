// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4914.WindsorBot;

import org.usfirst.frc4914.WindsorBot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc4914.WindsorBot.subsystems.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public Joystick driverJoystick;
    public Joystick codriverJoystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        codriverJoystick = new Joystick(1);
        
        driverJoystick = new Joystick(0);
        


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Shoot Low Goal", new ShootLowGoal());
        SmartDashboard.putData("Shoot High Goal", new ShootHighGoal());
        SmartDashboard.putData("Soft EStop", new SoftEStop());
        SmartDashboard.putData("UTL Reset Hook QE", new UTLResetHookQE());
        SmartDashboard.putData("UTL Reset Forklift QE", new UTLResetForkliftQE());
        SmartDashboard.putData("UTL Print QE Values", new UTLPrintQEValues());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    public Joystick getDriverJoystick() {
        return driverJoystick;
    }

    public Joystick getCodriverJoystick() {
        return codriverJoystick;
    }
    
    // driver controller joysticks
    public double driverLJ() { return driverJoystick.getRawAxis(1); }
    public double driverRJ() { return driverJoystick.getRawAxis(5); }
    // codriver controller joysticks
    public double codriverLJ() { return codriverJoystick.getRawAxis(1); }
    public double codriverRJ() { return codriverJoystick.getRawAxis(5); }
    // POVs
    public int driverPOV() { return driverJoystick.getPOV(0); }
    public int codriverPOV() { return driverJoystick.getPOV(0); }
    // driver controller triggers
    public boolean isDriverRT() { return driverJoystick.getRawAxis(3) > 0.25; }
    public boolean isDriverLT() { return driverJoystick.getRawAxis(2) > 0.25; }
    // codriver controller triggers
    public boolean isCodriverRT() { return codriverJoystick.getRawAxis(3) > 0.25; }
    public boolean isCodriverLT() { return codriverJoystick.getRawAxis(2) > 0.25; }
}
