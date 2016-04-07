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

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc4914.WindsorBot.Robot;
import org.usfirst.frc4914.WindsorBot.subsystems.*;

/**
 *
 */
public class SallyPort extends CommandGroup {


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public SallyPort() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS


    	requires(Robot.driveTrain);
    	requires(Robot.forklift);
    	
    	/* CONSTANTS */
    	double setpoint1F = 0; // first setpoint for forklift (slightly higher than sally port)
    	double setpoint1H = 0; // first setpoint for hook (slightly higher angle than perfectly horizontal)
    	
    	double drive1 = 0; // amount of time (in seconds) to drive up to the defense
    	double drive1speed = 0; // speed at which to drive up to the defense
    	
    	double setpoint2F = 0; // second setpoint for forklift (brings down to sally port height)
    	double setpoint2H = 0; // second setpoint for hook (horizontal, brought down on sally port)
    	double driveFlush = 0; // amount of time (in seconds) to run motors so that the robot stays flush to the door (in seconds)
    	double driveFlushSpeed = 0; // speed at which to keep the robot flush to door (keep it slow)
    	
    	double drive2 = 0; // amount of time (in seconds) to drive back and pull door open
    	double drive2speed = 0; // amount of time (in seconds) to drive back and pull door open
    	// robot will then rotate 90 degrees CCW and end up facing the open door of sally port
    	
    	/* EXECUTION */
    	
    	// moves forklift and hook to starting positions
    	addSequential(new LiftToSetpoint(setpoint1F));
    	addParallel(new HookToSetpoint(setpoint1H));
    	
    	// drives up to defense
    	addSequential(new DriveStraight(drive1, drive1speed, false));
    	
    	// runs motors slowly to stay flush with door whilst hook and forklift move down into position
    	addSequential(new LiftToSetpoint(setpoint2F));
    	addParallel(new HookToSetpoint(setpoint2H));
    	addParallel(new DriveStraight(driveFlush, driveFlushSpeed, false));
    	
    	// drive backwards to pull door open, and then brake afterwards
    	addSequential(new DriveStraight(drive2, drive2speed, true));
    	
    	// rotate 90 CCW and stop
    	addSequential(new RotateCCW(90));
    	
    	// stops all subsystems, resume normal user-driving at this point
    	addSequential(new SoftEStop());
    } 
}