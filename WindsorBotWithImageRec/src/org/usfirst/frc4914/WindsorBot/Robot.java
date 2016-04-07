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

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc4914.WindsorBot.commands.DriveStraight;
import org.usfirst.frc4914.WindsorBot.subsystems.DriveTrain;
import org.usfirst.frc4914.WindsorBot.subsystems.Forklift;
import org.usfirst.frc4914.WindsorBot.subsystems.Shooter;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//A structure to hold measurements of a particle
	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double PercentAreaToImageArea;
		double rectWidth;
		double rectHeight;
		double centerOfMassX;
		double centerOfMassY;
		
		public int compareTo(ParticleReport r)
		{
			double compareX = Math.abs(r.centerOfMassX/5 - this.centerOfMassX/5);
			double compareY = Math.abs(r.centerOfMassY/5 - this.centerOfMassY/5);
			return (int) (compareX + compareY);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			double compareX = Math.abs(r1.centerOfMassX/5 - r2.centerOfMassX/5);
			double compareY = Math.abs(r1.centerOfMassY/5 - r2.centerOfMassY/5);
			return (int) (compareX + compareY);
		}
	};

	//Structure to represent the Score for the various tests used for target identification
	public class Score {
		double Aspect;
	};

	//Images
	Image frame;
	Image binaryFrame;
	int imaqError;
	
	//Camera
    AxisCamera camera;
    
    /*************************************************/
    // calibrated centre of mass X and Y
    double calibratedCentreX = 0;
    double calibratedCentreY = 0;
    // allowed variance in pixels
    double variance = 0;
    /*************************************************/
    
    Vector<ParticleReport> particles = new Vector<ParticleReport>();
    //Constants
	NIVision.Range GOAL_HUE_RANGE = new NIVision.Range(111, 149);	//Default hue range for yellow tote
	NIVision.Range GOAL_SAT_RANGE = new NIVision.Range(222, 255);	//Default saturation range for yellow tote
	NIVision.Range GOAL_VAL_RANGE = new NIVision.Range(91, 255);	//Default value range for yellow tote
	double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double ASPECT_RATIO_MIN = 90.0;  //Minimum aspect ratio to be considered a target
	double VIEW_ANGLE = 49.4; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	Score score = new Score();

    Command autonomousCommand;
    SendableChooser autoChooser;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static Shooter shooter;
    public static Forklift forklift;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new DriveTrain();
        shooter = new Shooter();
        forklift = new Forklift();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS        
        oi = new OI();
        
        SmartDashboard.putData("Autonomous Sequence", autoChooser);
        autoChooser.addObject("3 second", new DriveStraight(3, 0.6, true));
        autoChooser.addDefault("4 second", new DriveStraight(4, 0.6, true));
        autoChooser.addObject("5 second", new DriveStraight(5, 0.6, true));
        autoChooser.addObject("6 second", new DriveStraight(6, 0.6, true));
        autoChooser.addObject("7 second", new DriveStraight(7, 0.6, true));
        
        // BEGIN IMGREC //
        camera = new AxisCamera("169.254.219.147");
        // create images
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
		// optional smartdashboard display
		SmartDashboard.putNumber("target hue min", GOAL_HUE_RANGE.minValue);
		SmartDashboard.putNumber("target hue max", GOAL_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("target sat min", GOAL_SAT_RANGE.minValue);
		SmartDashboard.putNumber("target sat max", GOAL_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("target val min", GOAL_VAL_RANGE.minValue);
		SmartDashboard.putNumber("target val max", GOAL_VAL_RANGE.maxValue);
		SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        autonomousCommand = (Command) autoChooser.getSelected();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void aimbot() {
    	// BEGIN IMGREC //        
        //Grab image
        camera.getImage(frame);
        //Update threshold values from SmartDashboard
		GOAL_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", GOAL_HUE_RANGE.minValue);
		GOAL_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", GOAL_HUE_RANGE.maxValue);
		GOAL_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", GOAL_SAT_RANGE.minValue);
		GOAL_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", GOAL_SAT_RANGE.maxValue);
		GOAL_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", GOAL_VAL_RANGE.minValue);
		GOAL_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", GOAL_VAL_RANGE.maxValue);
		//Threshold image
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, GOAL_HUE_RANGE, GOAL_SAT_RANGE, GOAL_VAL_RANGE);
		//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);
		//Send masked image to dashboard to assist in tweaking mask.
		CameraServer.getInstance().setImage(binaryFrame);
		//filter out small particles
		float areaMin = (float)SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
		criteria[0].lower = areaMin;
		imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
		//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Filtered particles", numParticles);
		
		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
				ParticleReport par = new ParticleReport();
				par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.rectWidth = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
				par.rectHeight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				par.centerOfMassX = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
				par.centerOfMassY = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);

				// prints values to riolog
				System.out.println("Center X: " + par.centerOfMassX + "\nCenterY: " + par.centerOfMassY + "\n");
				
				particles.add(par);
			}
			particles.sort(null);

			//This example only Score the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
			//for the reader. Note that this Score and reports information about a single particle (single L shaped target). To get accurate information 
			//about the location of the tote (not just the distance) you will need to correlate two adjacent targets in order to find the true center of the tote.
			score.Aspect = AspectScore(particles.elementAt(0));
			SmartDashboard.putNumber("Aspect", score.Aspect);
			boolean isTarget = score.Aspect > ASPECT_RATIO_MIN;

			//Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
			SmartDashboard.putBoolean("IsTarget", isTarget);
		} else {
			SmartDashboard.putBoolean("IsTarget", false);
		}
    }
    
    public void aimTurn() {
    	while (true) {
		// lines bot up
			if (particles.size() > 0) {
				// variance is greater than allowed and requires a CCW turn until centered within a variance
				if (particles.elementAt(0).centerOfMassX - calibratedCentreX < 0 && 
						Math.abs(particles.elementAt(0).centerOfMassX - calibratedCentreX) > variance) {
					Robot.driveTrain.setLeftVictor(-1);
					Robot.driveTrain.setRightVictor(1);
				}
				// variance is greater than allowed and requires a CW turn until centered within a variance
				else if (particles.elementAt(0).centerOfMassX - calibratedCentreX < 0 && 
						Math.abs(particles.elementAt(0).centerOfMassX - calibratedCentreX) > variance) {
					Robot.driveTrain.setLeftVictor(1);
					Robot.driveTrain.setRightVictor(-1);
				}
				// bot is centered
				else {
					Robot.driveTrain.stop();
					break;
				}
				aimbot();
			}
    	}
    }
    
    public void aimDrive() {
    	while (true) {
		// lines bot up
			if (particles.size() > 0) {
				// variance is greater than allowed and requires forward drive until centered within a variance
				if (particles.elementAt(0).centerOfMassY - calibratedCentreY < 0 && 
						Math.abs(particles.elementAt(0).centerOfMassY - calibratedCentreY) > variance) {
					Robot.driveTrain.setLeftVictor(0.4);
					Robot.driveTrain.setRightVictor(0.4);
				}
				// variance is greater than allowed and requires backward drive  until centered within a variance
				else if (particles.elementAt(0).centerOfMassY - calibratedCentreY < 0 && 
						Math.abs(particles.elementAt(0).centerOfMassY - calibratedCentreY) > variance) {
					Robot.driveTrain.setLeftVictor(-0.4);
					Robot.driveTrain.setRightVictor(-0.4);
				}
				// bot is centered
				else {
					Robot.driveTrain.stop();
					break;
				}
				aimbot();
			}
    	}
    }
    
    double AspectScore(ParticleReport report)
	{
		return 100*((report.rectHeight)/(report.rectWidth));
	}
}