package org.usfirst.frc4914.WindsorBot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4914.WindsorBot.Robot;
public class UTLPrintQEValues extends Command {
    public UTLPrintQEValues() { }
    protected void initialize() {
    	Robot.forklift.printEncoderValues();
    	SmartDashboard.putString("Quadrature Encoder Values",
    			"Forklift QE: " + Robot.forklift.getFLiftQEValue() +
    			"\nHook QE: " + Robot.forklift.getHookQEValue());
    }
    protected void execute() { }
    protected boolean isFinished() { return true; }
    protected void end() { }
    protected void interrupted() { }
}
