package org.firstinspires.ftc.teamcode.Other.Edrich;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Edric on 2018/02/04.
 */
@Disabled
public class MotorTest extends LinearOpMode {
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

        waitForStart();
        while (opModeIsActive()){
            DriveFrontRight.setPower(1);
        }
    }
}
