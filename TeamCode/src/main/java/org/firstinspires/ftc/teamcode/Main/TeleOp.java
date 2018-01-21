package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp

public class TeleOp extends OpMode {

    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackLeft;
    DcMotor DriveBackRight;
    DcMotor Glyph;
    DcMotor Arm;
    DcMotor Extending;
    Servo Jewel;
    Servo Phone;
    Servo RightArm;
    Servo LeftArm;
    Servo RelicClaw;

    double SpeedControl = 1;
    double GlyphSpeed = 1;


    public void init() {

        DriveFrontRight = hardwareMap.dcMotor.get("DriveFrontRight");
        DriveFrontLeft = hardwareMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hardwareMap.dcMotor.get("DriveBackLeft");
        DriveBackRight = hardwareMap.dcMotor.get("DriveBackRight");
        Glyph = hardwareMap.dcMotor.get("Glyph");
        Arm = hardwareMap.dcMotor.get("Arm");
        Extending = hardwareMap.dcMotor.get("Extending");
        Jewel = hardwareMap.servo.get("ServoArm");
        Phone = hardwareMap.servo.get("Phone");
        RightArm = hardwareMap.servo.get("RightArm");
        LeftArm =hardwareMap.servo.get("LeftArm");
        RelicClaw = hardwareMap.servo.get("RelicClaw");
        Arm.setDirection(DcMotor.Direction.REVERSE);
        Glyph.setDirection(DcMotorSimple.Direction.REVERSE);
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);
        DriveFrontLeft.setPower(0);
        DriveFrontRight.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
        Glyph.setPower(0);
        Arm.setPower(0);
        Extending.setPower(0);
        Phone.setPosition(1);
        LeftArm.setPosition(0);
        RightArm.setPosition(1);
        Jewel.setPosition(0);

    }

    @Override
    public void loop() {

        if (gamepad1.back){
            SpeedControl = 0.5;
        }
        if (gamepad1.start){
            SpeedControl = 1;
        }

        if ((gamepad1.left_bumper && gamepad1.right_bumper) == false)
        {

            DriveFrontLeft.setPower(gamepad1.left_stick_y);
            DriveFrontRight.setPower(gamepad1.right_stick_y);
            DriveBackLeft.setPower(gamepad1.left_stick_y);
            DriveBackRight.setPower(gamepad1.right_stick_y);
        }
        while (gamepad1.right_bumper) {
            SpeedControl = gamepad1.right_stick_y;
            DriveFrontLeft.setPower(1 * SpeedControl);
            DriveFrontRight.setPower(-1 * SpeedControl);
            DriveBackLeft.setPower(-1 * SpeedControl);
            DriveBackRight.setPower(1 * SpeedControl);
        }
        while (gamepad1.left_bumper) {
            SpeedControl = gamepad1.right_stick_y;
            DriveFrontLeft.setPower(-1 * SpeedControl);
            DriveFrontRight.setPower(1 * SpeedControl);
            DriveBackLeft.setPower(1 * SpeedControl);
            DriveBackRight.setPower(-1 * SpeedControl);

        }

        while (gamepad2.dpad_up)
        {
            Glyph.setPower(1*GlyphSpeed);
        }
        while (gamepad2.dpad_down)
        {
            Glyph.setPower(-1*GlyphSpeed);
        }

        Glyph.setPower(0);



        if (gamepad2.dpad_left == true){

            RightArm.setPosition(0.9);
            LeftArm.setPosition(0.1);
        }
        if (gamepad2.dpad_right == true) {

            RightArm.setPosition(0.5);
            LeftArm.setPosition(0.5);
        }

        if (gamepad2.x){
            RelicClaw.setPosition(0);
        }
        if (gamepad2.b){
            RelicClaw.setPosition(0.5);
        }

        while (gamepad2.right_bumper == true){
            Arm.setPower(0);
        }




        Extending.setPower(gamepad2.right_stick_y);
        Arm.setPower(gamepad2.left_stick_y*-0.5);

    }
}
