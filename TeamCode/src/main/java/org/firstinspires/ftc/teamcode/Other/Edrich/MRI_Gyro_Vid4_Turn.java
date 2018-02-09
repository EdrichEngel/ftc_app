package org.firstinspires.ftc.teamcode.Other.Edrich;

/*
Modern Robotics Gyro Turn Example
Updated 11/3/2016 by Colton Mehlhoff of Modern Robotics using FTC SDK 2.35
Reuse permitted with credit where credit is due

Configuration:
Gyro Sensor named "gyro"
Motor named "ml" for left drive train
Motor named "mr" for right drive train

For more information, go to http://modernroboticsedu.com/course/view.php?id=4
Support is available by emailing support@modernroboticsinc.com.
*/

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "4 Turn", group = "Gyro Video")
//@Disabled
public class MRI_Gyro_Vid4_Turn extends LinearOpMode {   //Linear op mode is being used so the program does not get stuck in loop()
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timer = new ElapsedTime();

    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackLeft;
    DcMotor DriveBackRight;

    int zAccumulated;  //Total rotation left/right
    int target = 0;  //Desired angle to turn to

    GyroSensor sensorGyro;  //General Gyro Sensor allows us to point to the sensor in the configuration file.
    ModernRoboticsI2cGyro mrGyro;  //ModernRoboticsI2cGyro allows us to .getIntegratedZValue()

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        DriveFrontRight = hardwareMap.dcMotor.get("DriveFrontRight");
        DriveFrontLeft = hardwareMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hardwareMap.dcMotor.get("DriveBackLeft");
        DriveBackRight = hardwareMap.dcMotor.get("DriveBackRight");

        DriveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        DriveBackLeft.setDirection(DcMotor.Direction.REVERSE);

        DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  //Controls the speed of the motors to be consistent even at different battery levels
        DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sensorGyro = hardwareMap.gyroSensor.get("gyro");  //Point to the gyro in the configuration file
        mrGyro = (ModernRoboticsI2cGyro) sensorGyro;      //ModernRoboticsI2cGyro allows us to .getIntegratedZValue()
        mrGyro.calibrate();  //Calibrate the sensor so it knows where 0 is and what still is. DO NOT MOVE SENSOR WHILE BLUE LIGHT IS SOLID

        while (mrGyro.isCalibrating()) { //Ensure calibration is complete (usually 2 seconds)
        }

        waitForStart();
        runtime.reset();


        while(opModeIsActive()){
            telemetry.addData("Status", "Running: " + runtime.toString());

            if (gamepad1.a)
                target = target + 45;
            if (gamepad1.b)
                target = target - 45;

            turnAbsolute(target);
        }
    }

    //This function turns a number of degrees compared to where the robot is. Positive numbers trn left.
    public void turn(int target) throws InterruptedException {
        turnAbsolute(target + mrGyro.getIntegratedZValue());
    }

    //This function turns a number of degrees compared to where the robot was when the program started. Positive numbers trn left.
    public void turnAbsolute(int target) {
        zAccumulated = mrGyro.getIntegratedZValue();  //Set variables to gyro readings
        double turnSpeed = 0.15;

        while (Math.abs(zAccumulated - target) > 3 && opModeIsActive()) {  //Continue while the robot direction is further than three degrees from the target
            if (zAccumulated > target) {  //if gyro is positive, we will turn right
                DriveBackLeft.setPower(turnSpeed);
                DriveFrontLeft.setPower(turnSpeed);
                DriveFrontRight.setPower(-turnSpeed);
                DriveBackRight.setPower(-turnSpeed);
            }

            if (zAccumulated < target) {  //if gyro is positive, we will turn left
                DriveBackLeft.setPower(-turnSpeed);
                DriveFrontLeft.setPower(-turnSpeed);
                DriveFrontRight.setPower(turnSpeed);
                DriveBackRight.setPower(turnSpeed);
            }

            zAccumulated = mrGyro.getIntegratedZValue();  //Set variables to gyro readings
            telemetry.addData("accu", String.format("%03d", zAccumulated));
            telemetry.update();
        }

        DriveBackLeft.setPower(0);
        DriveFrontLeft.setPower(0);
        DriveFrontRight.setPower(0);
        DriveBackRight.setPower(0);

    }

}
