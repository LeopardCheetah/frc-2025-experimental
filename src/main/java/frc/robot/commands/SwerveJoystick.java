// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.SwerveDrive;

public class SwerveJoystick extends Command {
  /** Creates a new SwerveJoystick. */

  private final Joystick m_joystick;
  private final SlewRateLimiter m_xSlewRateLimiter;
  private final SlewRateLimiter m_ySlewRateLimiter;
  private final SwerveDrive m_swerveDrive;
  public int m_driveMode = 0;

  private double xSpeed;
  private double ySpeed;
  private double turningSpeed;
  private double _newXSpeed;
  private double _newYSpeed;

  public SwerveJoystick(SwerveDrive swerveDrive, Joystick joystick) {
    
    m_xSlewRateLimiter = new SlewRateLimiter(DriveConstants.kMaxTranslationalAccel);
    m_ySlewRateLimiter = new SlewRateLimiter(DriveConstants.kMaxTranslationalAccel);
    m_joystick = joystick;
    m_swerveDrive = swerveDrive;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerveDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("SwerveJoystick Initialized");
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    xSpeed = -m_joystick.getRawAxis(IOConstants.kJoystickYAxis);
    ySpeed = -m_joystick.getRawAxis(IOConstants.kJoystickXAxis);
    turningSpeed = -m_joystick.getRawAxis(IOConstants.kJoystickRotAxis);


    // turn speed from squared inputs or whatever into circular inputs
    // see https://stackoverflow.com/questions/13211595/how-can-i-convert-coordinates-on-a-circle-to-coordinates-on-a-square/32391780#32391780
    // 2.82843 ~ 2sqrt(2)
    _newXSpeed = 0.5*(Math.sqrt(2 + 2.82843*xSpeed + xSpeed*xSpeed - ySpeed*ySpeed) - Math.sqrt(2 - 2.82843*xSpeed + xSpeed*xSpeed - ySpeed*ySpeed));
    _newYSpeed = 0.5*(Math.sqrt(2 + 2.82843*ySpeed - xSpeed*xSpeed + ySpeed*ySpeed) - Math.sqrt(2 - 2.82843*ySpeed - xSpeed*xSpeed + ySpeed*ySpeed));

    _newXSpeed = Math.min(_newXSpeed, 1);
    _newXSpeed = Math.max(_newXSpeed, -1);
    _newYSpeed = Math.min(_newYSpeed, 1);
    _newYSpeed = Math.max(_newYSpeed, -1);

    _newXSpeed = Math.abs(_newXSpeed) > IOConstants.kDeadband ? _newXSpeed : 0.0;
    _newYSpeed = Math.abs(_newYSpeed) > IOConstants.kDeadband ? _newYSpeed : 0.0;
    turningSpeed = Math.abs(turningSpeed) > IOConstants.kDeadband ? turningSpeed : 0.0;

    //use SlewRateLimiter with DriveConstants
    _newXSpeed = m_xSlewRateLimiter.calculate(_newXSpeed) * DriveConstants.kMaxTranslationalSpeed;
    _newYSpeed = m_ySlewRateLimiter.calculate(_newYSpeed) * DriveConstants.kMaxTranslationalSpeed;

    turningSpeed = turningSpeed * DriveConstants.kMaxTurningSpeed;

    SmartDashboard.putNumber("Joystick/xSpeedCommanded", _newXSpeed);
    SmartDashboard.putNumber("Joystick/ySpeedCommanded", _newYSpeed);
    SmartDashboard.putNumber("Joystick/turningSpeedCommanded", turningSpeed);

    m_swerveDrive.driveRobotRelative(ChassisSpeeds.fromFieldRelativeSpeeds(_newXSpeed, _newYSpeed, turningSpeed, m_swerveDrive.getAngle()));
  }




  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}