package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Edric on 2018/03/29.
 */
@TeleOp
public class Extend extends LinearOpMode {
    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackLeft;
    DcMotor DriveBackRight;
    DcMotor ExtendLeft;
    DcMotor ExtendRight;
    DcMotor RelicRight;
    DcMotor RelicLeft;
    double SpeedControl = 1;
    double Extend = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        DriveFrontRight = hardwareMap.dcMotor.get("DriveFrontRight");
        DriveFrontLeft = hardwareMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hardwareMap.dcMotor.get("DriveBackLeft");
        DriveBackRight = hardwareMap.dcMotor.get("DriveBackRight");
        ExtendLeft = hardwareMap.dcMotor.get("ExtendLeft");
        ExtendRight = hardwareMap.dcMotor.get("ExtendRight");
        RelicRight = hardwareMap.dcMotor.get("RelicRight");
        RelicLeft = hardwareMap.dcMotor.get("RelicLeft");
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);
        RelicRight.setDirection(DcMotor.Direction.REVERSE);
        ExtendRight.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.back) {
                SpeedControl = 0.3;
            }
            if (gamepad1.start) {
                SpeedControl = 1;
            }
            if ((gamepad1.left_bumper && gamepad1.right_bumper) == false) {
                DriveFrontLeft.setPower(gamepad1.left_stick_y * SpeedControl);
                DriveFrontRight.setPower(gamepad1.right_stick_y * SpeedControl);
                DriveBackLeft.setPower(gamepad1.left_stick_y * SpeedControl);
                DriveBackRight.setPower(gamepad1.right_stick_y * SpeedControl);
            }
            while (gamepad1.left_bumper) {
                DriveFrontLeft.setPower(1);
                DriveFrontRight.setPower(-1);
                DriveBackLeft.setPower(-1);
                DriveBackRight.setPower(1);
            }
            while (gamepad1.right_bumper) {
                DriveFrontLeft.setPower(-1);
                DriveFrontRight.setPower(1);
                DriveBackLeft.setPower(1);
                DriveBackRight.setPower(-1);
            }

            ExtendRight.setPower(gamepad2.left_stick_y);
            ExtendLeft.setPower(-gamepad2.right_stick_y);
            RelicLeft.setPower(0);
            RelicRight.setPower(0);
            if (gamepad2.back) {
                Extend = 0.05;
            }
            if (gamepad2.start) {
                Extend = 0.3;
            }
            while (gamepad2.dpad_up){
                RelicLeft.setPower(Extend);
            }
            while (gamepad2.dpad_down){
                RelicLeft.setPower(-Extend);
            }
            while (gamepad2.y){
                RelicRight.setPower(-Extend);
            }
            while (gamepad2.a){
                RelicRight.setPower(Extend);
            }

            }
        }
    }

