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
            SpeedControl = 0.4;
        }
        if (gamepad1.start){
            SpeedControl = 1;
        }

        if ((gamepad1.left_bumper && gamepad1.right_bumper) == false)
        {

            DriveFrontLeft.setPower(gamepad1.left_stick_y* SpeedControl);
            DriveFrontRight.setPower(gamepad1.right_stick_y*SpeedControl);
            DriveBackLeft.setPower(gamepad1.left_stick_y*SpeedControl);
            DriveBackRight.setPower(gamepad1.right_stick_y*SpeedControl);
        }
        while (gamepad1.right_bumper) {

            DriveFrontLeft.setPower(1 );
            DriveFrontRight.setPower(-1 );
            DriveBackLeft.setPower(-1 );
            DriveBackRight.setPower(1 );
        }
        while (gamepad1.left_bumper) {

            DriveFrontLeft.setPower(-1 );
            DriveFrontRight.setPower(1);
            DriveBackLeft.setPower(1 );
            DriveBackRight.setPower(-1 );

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



        if (gamepad2.x){

            RightArm.setPosition(0.9);
            LeftArm.setPosition(0.1);
        }
        if (gamepad2.b) {

            RightArm.setPosition(0.5);
            LeftArm.setPosition(0.5);
        }

        if (gamepad2.a) {

            RightArm.setPosition(0.7);
            LeftArm.setPosition(0.3);
        }

        if (gamepad2.dpad_left){
            RelicClaw.setPosition(0);
        }
        if (gamepad2.dpad_right){
            RelicClaw.setPosition(0.5);
        }

        while (gamepad2.right_bumper == true){
            Arm.setPower(0);
        }




        Extending.setPower(gamepad2.right_stick_y);
        Arm.setPower(gamepad2.left_stick_y*-0.5);

    }
}
