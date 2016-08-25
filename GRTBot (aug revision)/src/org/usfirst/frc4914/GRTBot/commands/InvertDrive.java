package org.usfirst.frc4914.GRTBot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc4914.GRTBot.Robot;
public class InvertDrive extends Command {
    public InvertDrive() { }
    protected void initialize() {
    	Robot.drivetrain.isInvertedDrive = !Robot.drivetrain.isInvertedDrive;
    }
    protected void execute() { }
    protected boolean isFinished() { return true; }
    protected void end() { }
    protected void interrupted() { }
}
