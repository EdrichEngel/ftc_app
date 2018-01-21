package org.firstinspires.ftc.teamcode.Other.Edrich;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Edrich on 11/24/2016.
 */
@Disabled
@Autonomous(name = "Template", group = "AutoOp")
class AutoOpTemplate extends LinearOpMode {







    private DcMotor DriveLeft;
    private DcMotor DriveRight;
    private DcMotor Sweeper;
    private DcMotor ShooterRight;
    private DcMotor ShooterLeft;

    private Servo Lever;
    private Servo redBeacon;
    //Servo blueBeacon;

    private ColorSensor RedBeacon;
    private I2cDevice colorA;
    private I2cDevice colorC;
    private I2cDeviceSynch colorCreader;
    private I2cDeviceSynch colorAreader;
    private byte[] colorCcache;
    private byte[] colorAcache;
    private boolean bLeftOnLine;
    private boolean bRightOnLine;

    private final static double Red_Max_Servo = 0;
    private final static double Red_Min_Servo = 1;
    private double BeaconActivated = 0;

    //............................................... Main program
    public void runOpMode() {
        //........................................... Initialization
        initSystem();
        //........................................... Waits for user to push the play button
        waitForStart();
        //........................................... Main Loop
        while (opModeIsActive()) {
            turnLeft(0.5,3600);
            stop();
        }
    }



    private void initSystem() {
        DriveLeft = hardwareMap.dcMotor.get("driveLeft");
        DriveRight = hardwareMap.dcMotor.get("driveRight");

        ShooterRight = hardwareMap.dcMotor.get("shooterright");
        ShooterLeft = hardwareMap.dcMotor.get("shooterleft");
        Sweeper = hardwareMap.dcMotor.get("sweeper");
        ShooterRight.setDirection(DcMotorSimple.Direction.REVERSE);
        DriveRight.setDirection(DcMotor.Direction.REVERSE);
        DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);




        Lever = hardwareMap.servo.get("lever");
        redBeacon = hardwareMap.servo.get("redbeacon");


        colorA = hardwareMap.i2cDevice.get("rightLine");
        colorC = hardwareMap.i2cDevice.get("leftLine");

        RedBeacon = hardwareMap.colorSensor.get("RedColorSensor");
        colorCreader.write8(3, 0);
        colorAreader.write8(3, 0);
    }


    private void driveForwardDistance(double power, int distance) {
        // Reset encoders
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Change to run to position prior to setting target
        // Run to position
        DriveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DriveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target position
        DriveLeft.setTargetPosition(distance);
        DriveRight.setTargetPosition(distance);

        // Set drive power
        driveForward(power);

        // wait until position is reached
        while (opModeIsActive() && DriveLeft.isBusy() && DriveRight.isBusy()) idle();

        //stopRobot and change modes back to normal
        stopRobot();
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    private void driveForward(double power) {
        DriveLeft.setPower(power);
        DriveRight.setPower(power);
    }



    private void stopRobot() {
        DriveLeft.setPower(0);
        DriveRight.setPower(0);
    }


    private void turnLeft(double power, int distance) {
        // Reset encoders
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Change to run to position prior to setting target
        // Run to position
        DriveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DriveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target position
        DriveLeft.setTargetPosition(-distance);
        DriveRight.setTargetPosition(distance);

        // Set drive power
        DriveLeft.setPower(-power);
        DriveRight.setPower(power);

        while (opModeIsActive() && DriveLeft.isBusy() && DriveRight.isBusy()) idle();

        //stopRobot and change modes back to normal
        stopRobot();
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    private void turnRight(double power, int distance) {
        // Reset encoders
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Change to run to position prior to setting target
        // Run to position
        DriveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DriveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target position
        DriveLeft.setTargetPosition(distance);
        DriveRight.setTargetPosition(-distance);

        // Set drive power
        DriveLeft.setPower(power);
        DriveRight.setPower(-power);

        while (opModeIsActive() && DriveLeft.isBusy() && DriveRight.isBusy()) idle();

        //stopRobot and change modes back to normal
        stopRobot();
        DriveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    private void LineStop(double power) {
        colorCreader = new I2cDeviceSynchImpl(colorC, I2cAddr.create8bit(0x3a), false);
        colorAreader = new I2cDeviceSynchImpl(colorA, I2cAddr.create8bit(0x3a), false);
        colorCreader.engage();
        colorAreader.engage();

        colorCcache = colorCreader.read(0x04, 1);

        if (colorCcache[0] > 12) {

            DriveLeft.setPower(0);
            DriveRight.setPower(0);
            stop();
        } else {
            DriveLeft.setPower(power);
            DriveRight.setPower(power);
        }
    }


    private void DualLine() {
        colorCreader.engage();
        colorAreader.engage();

        colorCcache = colorCreader.read(0x04, 1);
        colorAcache = colorAreader.read(0x04, 1);

        if (!bLeftOnLine) {
            if (colorCcache[0] > 14) {
                DriveLeft.setPower(0);
                bLeftOnLine = true;
            }
        }
        if (!bRightOnLine) {
            if (colorAcache[0] > 14) {
                DriveRight.setPower(0);
                bRightOnLine = true;
            }
        }
        if (bRightOnLine && bLeftOnLine) {
            stop();
        }

    }


    private void BeaconActivate() {

        float hsvValues[] = {0, 0, 0}; //used to get Hue
        Color.RGBToHSV(RedBeacon.red() * 8, RedBeacon.green() * 8, RedBeacon.blue() * 8, hsvValues);

        if (BeaconActivated < 5) {
            if (RedBeacon.red() > RedBeacon.blue() && RedBeacon.red() > RedBeacon.green()) {
                redBeacon.setPosition(Red_Max_Servo);
                sleep(500);
                redBeacon.setPosition(Red_Min_Servo);
                BeaconActivated = 10;

            } else {
                driveForwardDistance(0.1, 50);

            }

        }


    }
}

