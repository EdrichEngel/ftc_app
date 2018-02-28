package org.firstinspires.ftc.teamcode.Other.Edrich;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Edric on 2018/02/04.
 */
@Autonomous
@Disabled
public class Encoders extends LinearOpMode {
    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackLeft;
    DcMotor DriveBackRight;

    @Override
    public void runOpMode() throws InterruptedException {
        DriveFrontRight = hardwareMap.dcMotor.get("DriveFrontRight");
        DriveFrontLeft = hardwareMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hardwareMap.dcMotor.get("DriveBackLeft");
        DriveBackRight = hardwareMap.dcMotor.get("DriveBackRight");

        DriveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        DriveBackLeft.setDirection(DcMotor.Direction.REVERSE);

        DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  //Controls the speed of the motors to be consistent even at different battery levels
        DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  //Controls the speed of the motors to be consistent even at different battery levels
        DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("LeftFront", DriveFrontLeft.getCurrentPosition());
            telemetry.addData("LeftBack", DriveBackLeft.getCurrentPosition());
            telemetry.addData("RightFront", DriveFrontRight.getCurrentPosition());
            telemetry.addData("RightBack", DriveBackRight.getCurrentPosition());
            telemetry.update();
            Thread.sleep(250);
        }
    }
}
