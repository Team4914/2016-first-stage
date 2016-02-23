/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4914.PantherBot.commands;

import org.usfirst.frc4914.PantherBot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Drive until the robot is the given distance away from the box. Uses a local
 * PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class AUTODrivetoSetpoint extends Command {
	private PIDController pid;

	public AUTODrivetoSetpoint(double distance) {
		requires(Robot.driveTrain);
		pid = new PIDController(-1, 0, 0, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			public double pidGet() {
				return Robot.driveTrain.getUltraVoltage();
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				m_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return m_sourceType;
			}
		}, new PIDOutput() {
			public void pidWrite(double d) {
				Robot.driveTrain.setLeftVictor(-d);
				Robot.driveTrain.setRightVictor(-d);
			}
		});
		pid.setAbsoluteTolerance(0.01);
		pid.setSetpoint(distance);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Get everything in a safe starting state.
		Robot.driveTrain.stop();
		pid.reset();
		pid.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pid.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		// Stop PID and the wheels
		pid.disable();
		Robot.driveTrain.drive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
