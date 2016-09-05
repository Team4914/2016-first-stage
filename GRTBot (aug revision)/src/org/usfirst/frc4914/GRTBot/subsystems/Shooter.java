// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4914.GRTBot.subsystems;

import org.usfirst.frc4914.GRTBot.RobotMap;
import org.usfirst.frc4914.GRTBot.commands.*;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController topFlywheel = RobotMap.shooterTopFlywheel;
    private final SpeedController bottomFlywheel = RobotMap.shooterBottomFlywheel;
    private final SpeedController intakeRoller = RobotMap.shooterIntakeRoller;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /******* CONSTANTS *******/
    
    // high goal values
    public static final double highTopFly = 0.57;
    public static final double highBottomFly = 0.95;
    // low goal values
    public static final double lowTopFly = -0.75;
    public static final double lowBottomFly = 0.5;
    public static final double lowIntake = 1;
    
    /******* METHODS *******/

    public void initDefaultCommand() {
        //setDefaultCommand(new OperateShooter());
    }
    
    public void setTopFly(double speed) {
    	topFlywheel.set(speed);
    }
    
    public void setBottomFly(double speed) {
    	bottomFlywheel.set(speed);
    }
    
    public void setIntake(double speed) {
    	intakeRoller.set(speed);
    }
    
    public void setShooter(double topSpeed, double bottomSpeed) {
    	setTopFly(topSpeed);
    	setBottomFly(bottomSpeed);
    }
    
    public void stopFly() {
    	setTopFly(0);
    	setBottomFly(0);
    }
    
    public void stopIntake() { 
    	setIntake(0);
    }
}