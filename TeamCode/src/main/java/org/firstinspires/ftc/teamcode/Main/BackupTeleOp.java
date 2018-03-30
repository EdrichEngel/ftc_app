package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Edric on 2018/03/03.
 */
@TeleOp
@Disabled
public class BackupTeleOp extends LinearOpMode{
DcMotor Left;
DcMotor Right;
DcMotor Front;
Servo Glyph;
    @Override
    public void runOpMode() throws InterruptedException {
        Left = hardwareMap.dcMotor.get("Left");
        Right = hardwareMap.dcMotor.get("Right");
        Right.setDirection(DcMotorSimple.Direction.REVERSE);
        Front = hardwareMap.dcMotor.get("Front");
 //       Glyph = hardwareMap.servo.get("Glyph");
        waitForStart();
        while (opModeIsActive()){
            Left.setPower(gamepad1.left_stick_y);
            Right.setPower(gamepad1.right_stick_y);
            if (gamepad1.dpad_left){
                Front.setPower(0);
            }if (gamepad1.dpad_up){
                Front.setPower(1);
            }if (gamepad1.dpad_down){
                Front.setPower(-1);
            }
  /*          if (gamepad1.left_bumper){
                Glyph.setPosition(1);
            }
            if (gamepad1.right_bumper){
                Glyph.setPosition(0);
            }
    */    }
    }
}
