package org.firstinspires.ftc.teamcode.Other.Edrich;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
@Disabled
public class TeleOp extends OpMode {
// Declare Motors
    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackLeft;
    DcMotor DriveBackRight;
    DcMotor Glyph;
    DcMotor Arm;
    DcMotor Extending;
// Declare Servos
    Servo Jewel;
    Servo Phone;
    Servo RightArm;
    Servo LeftArm;
    Servo RelicClaw;
// Declare Varuables
    double SpeedControl = 1;
    double GlyphSpeed = 1;
    double Power = 0.4;

    public void init() {
// Hardwaremap motors and servos
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
// Set the direction of the motors
        Arm.setDirection(DcMotor.Direction.REVERSE);
        Glyph.setDirection(DcMotorSimple.Direction.REVERSE);
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);
// Set the starting speed/power of the motors to 0
        DriveFrontLeft.setPower(0);
        DriveFrontRight.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
        Glyph.setPower(0);
        Arm.setPower(0);
        Extending.setPower(0);
// Set the starting position(angle) of the servo
        Phone.setPosition(1);
        LeftArm.setPosition(0);
        RightArm.setPosition(1);
        Jewel.setPosition(0);

    }

    @Override
    public void loop() {
// Change the speed controll variable  for the drive motor multiplier.
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
// Move Left
        while (gamepad1.left_bumper) {

            DriveFrontLeft.setPower(1 );
            DriveFrontRight.setPower(-1 );
            DriveBackLeft.setPower(-1 );
            DriveBackRight.setPower(1 );
        }
// Move Right
        while (gamepad1.right_bumper) {

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


// Completely open clyph arms
        if (gamepad2.x){

            RightArm.setPosition(0.9);
            LeftArm.setPosition(0.1);
        }

// Completely close clyph arms
        if (gamepad2.b) {

            RightArm.setPosition(0.5);
            LeftArm.setPosition(0.5);
        }

// 60° angle clyph arms
        if (gamepad2.a) {

            RightArm.setPosition(0.7);
            LeftArm.setPosition(0.3);
        }

// Release Glyph. Arms are bearly open
        if (gamepad2.y) {

            RightArm.setPosition(0.55);
            LeftArm.setPosition(0.45);
        }
// Double lock to ensure that we don't accidentally release the relic
        if ((gamepad2.left_trigger > 0) && (gamepad2.right_trigger > 0)){
            RelicClaw.setPosition(0);
        }
// Close Relic claw
        if (gamepad2.dpad_right){
            RelicClaw.setPosition(0.2);
        }
// Lock front motor when dropping relic
        while (gamepad2.right_bumper){
            Arm.setPower(0);
        }

        Extending.setPower(gamepad2.left_stick_y);

        Arm.setPower(gamepad2.right_stick_y*Power);

        if (gamepad2.back){
            Power = 0.2;
        }
        if (gamepad2.start){
            Power = 0.4;
        }
        /* Changing speed when picking up the glyph.
   Slower when going down.
   Faster when moving up.

        if(gamepad1.left_trigger >0){arm.setpower(gamepad1.right_stick_y);}else{arm.setpower(gamepad1.left_trigger);}
    */


        // else
       // {Arm.setPower((double)gamepad2.right_trigger*0.6);}

    }
}
