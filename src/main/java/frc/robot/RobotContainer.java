// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import java.util.jar.Attributes.Name;

import com.fasterxml.jackson.databind.util.Named;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.SwerveJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;


import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.IOConstants;

import frc.robot.subsystems.SwerveDrive;

// remove all subsystems

@SuppressWarnings("unused") // thanks 254

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than commandsthe scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
 
  // The robot's subsystems and  are defined here...
  // see OldRobotContainer for a working, non pruned robo container

  private final Joystick m_driverJoystick = new Joystick(0);
  private final Joystick m_secondJoystick = new Joystick(1);

  private final JoystickButton m_resetHeadingButton = new JoystickButton(m_driverJoystick, IOConstants.kResetHeadingButtonID);



  public final SwerveDrive m_swerve = new SwerveDrive();
  public final SwerveJoystick m_swerveJoystick = new SwerveJoystick(m_swerve, m_driverJoystick);
  private final InstantCommand m_resetHeadingCommand = m_swerve.resetHeadingCommand();

  // private MoveForTime m_leaveAuto = new MoveForTime(m_swerve, 4, -0.6, 0, 0);
  // private DriveForwardL4 m_driveForwardL4 = new DriveForwardL4(m_swerve, m_arm, m_elevator, m_endEffector, m_secondJoystick);

  
  private SendableChooser<Command> m_autoChooser;
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_autoChooser = AutoBuilder.buildAutoChooser();
    System.out.println("build auto chooser");
    // m_autoChooser.setDefaultOption("Drive, L4", m_driveForwardL4);
    // m_autoChooser.addOption("Drive, L4", m_driveForwardL4);
    // m_autoChooser.addOption("Move Auto", m_leaveAuto);
    
    SmartDashboard.putData("Auto Chooser", m_autoChooser);
    // SmartDashboard.putData("Reset_Heading", m_swerve.resetHeadingCommand());
    // Configure the trigger bindings
    bindSubsystemCommands();
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

   private void configureBindings() {
    // ....
    // there are on subsystems to bind
    // ....    
  }




  public Command getAutonomousCommand() {
    // return m_autoChooser.getSelected();
    // return m_driveForwardAndPlace;
    return null;
  }



  private void bindSubsystemCommands() {
    ////// 
    m_swerve.setDefaultCommand(m_swerveJoystick);
    // m_lockServo.schedule(); // sorry lys
  }
}
  
