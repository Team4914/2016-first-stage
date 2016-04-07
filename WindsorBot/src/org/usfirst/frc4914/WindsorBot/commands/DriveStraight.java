// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4914.WindsorBot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4914.WindsorBot.Robot;

/**
 *
 */
public class DriveStraight extends Command {
	double seconds;
	double speed;
	boolean brake;

	/**
	 * 
	 * @param seconds amount of time to drive straight (in seconds)
	 * @param speed speed at which to drive straight
	 * @param brake true if a brake sequence at the end of this command is desired, otherwise false
	 */
    public DriveStraight(double seconds, double speed, boolean brake) {
    	this.seconds = seconds;
    	this.speed = speed;
    	this.brake = brake;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// resets gyros before auto
    	Robot.driveTrain.resetGyro();
    	// drives forward
    	Robot.driveTrain.setLeftVictor(0.6);
    	Robot.driveTrain.setRightVictor(0.6);
    	Timer.delay(seconds);
    	// initiates brakes
	    if (brake && speed > 0) {
	    	Robot.driveTrain.setLeftVictor(-0.1);
	    	Robot.driveTrain.setRightVictor(-0.1);
	    	Timer.delay(0.5);
	    }
	    else if (brake && speed < 0) {
	    	Robot.driveTrain.setLeftVictor(0.1);
	    	Robot.driveTrain.setRightVictor(0.1);
	    	Timer.delay(0.5);
	    }
    	Robot.driveTrain.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}