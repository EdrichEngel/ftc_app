package org.firstinspires.ftc.teamcode.Main.Worlds;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Edrich on 2018/03/30.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends LinearOpMode {
    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackRight;
    DcMotor DriveBackLeft;
    DcMotor ExtendLeft;
    DcMotor ExtendRight;
    DcMotor RelicLeft;
    DcMotor RelicRight;
    Servo Phone;
    Servo GlyphRight;
    Servo GlyphLeft;
    Servo Jewel;
    Servo RelicLeftServo;
    Servo RelicRightServo;
    CRServo GlyphPickUp;

    double DriveSpeed = 1;
    double RelicSpeed = 0.3;
    @Override
    public void runOpMode() throws InterruptedException {
        DriveBackLeft = hardwareMap.dcMotor.get("DriveBackLeft");
        DriveBackRight = hardwareMap.dcMotor.get("DriveBackRight");
        DriveFrontLeft = hardwareMap.dcMotor.get("DriveFrontLeft");
        DriveFrontRight = hardwareMap.dcMotor.get("DriveFrontRight");
        ExtendLeft = hardwareMap.dcMotor.get("ExtendLeft");
        ExtendRight = hardwareMap.dcMotor.get("ExtendRight");
        RelicRight = hardwareMap.dcMotor.get("RelicRight");
        RelicLeft = hardwareMap.dcMotor.get("RelicLeft");
        Phone = hardwareMap.servo.get("Phone");
        GlyphRight = hardwareMap.servo.get("GlyphRight");
        GlyphLeft = hardwareMap.servo.get("GlyphLeft");
        Jewel = hardwareMap.servo.get("Jewel");
        RelicLeftServo = hardwareMap.servo.get("RelicLeftServo");
        RelicRightServo = hardwareMap.servo.get("RelicRightServo");
        GlyphPickUp = hardwareMap.crservo.get("GlyphPickUp");
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        ExtendLeft.setDirection(DcMotor.Direction.REVERSE);
        ExtendRight.setDirection(DcMotor.Direction.REVERSE);
        GlyphPickUp.setPower(0);
        RelicLeftServo.setPosition(0.95);
        RelicRightServo.setPosition(0.07);
        Phone.setPosition(0.35);

        waitForStart();
        while (opModeIsActive()) {

               while (gamepad2.left_stick_button){
                    Driver1();
                    Driver2();
                    RelicRight.setPower(gamepad2.right_stick_y*RelicSpeed);
                    RelicLeft.setPower(0);
                    ExtendRight.setPower(0);
                    ExtendLeft.setPower(0);

                }
                while (gamepad2.right_stick_button){
                    Driver1();
                    Driver2();
                    RelicLeft.setPower(gamepad2.left_stick_y*RelicSpeed);
                    RelicRight.setPower(0);
                    ExtendRight.setPower(0);
                    ExtendLeft.setPower(0);

                }
                while (!gamepad2.left_stick_button && !gamepad2.right_stick_button){
                    Driver1();
                    Driver2();
                    ExtendLeft.setPower(gamepad2.right_stick_y);
                    ExtendRight.setPower(gamepad2.left_stick_y);
                    RelicRight.setPower(0);
                    RelicLeft.setPower(0);
                }
        }
            }
        public void Driver1() {
            GlyphPickUp.setPower(0);
            if (gamepad1.back) {
                DriveSpeed = 0.1;
            }
            if (gamepad1.start) {
                DriveSpeed = 1;
            }
            if ((gamepad1.left_bumper && gamepad1.right_bumper) == false) {
                DriveFrontLeft.setPower((gamepad1.left_stick_y * DriveSpeed));
                DriveFrontRight.setPower(gamepad1.right_stick_y * DriveSpeed);
                DriveBackLeft.setPower((gamepad1.left_stick_y * DriveSpeed));
                DriveBackRight.setPower(gamepad1.right_stick_y * DriveSpeed);
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
            while (gamepad1.dpad_up) {
                GlyphPickUp.setPower(1);
            }
            while (gamepad1.dpad_down) {
                GlyphPickUp.setPower(-1);
            }
            if (gamepad1.x) {
                GlyphRight.setPosition(0.9);
                GlyphLeft.setPosition(0.1);
            }
            if (gamepad1.b) {
                GlyphRight.setPosition(0.5);
                GlyphLeft.setPosition(0.5);
            }
            if (gamepad1.a) {
                GlyphRight.setPosition(0.7);
                GlyphLeft.setPosition(0.3);
            }
            if (gamepad1.y) {
                GlyphRight.setPosition(0.55);
                GlyphLeft.setPosition(0.45);
            }

        }
        public void Driver2() {

            if (gamepad2.back) {
                RelicSpeed = 0.1;
            }
            if (gamepad2.start) {
                RelicSpeed = 0.3;
            }
            if (gamepad2.dpad_left){
                RelicRightServo.setPosition(0.3);
            }
            if (gamepad2.dpad_right){
                RelicRightServo.setPosition(0);
            }
            if (gamepad2.x){
                RelicLeftServo.setPosition(0.7);
            }
            if (gamepad2.b){
                RelicLeftServo.setPosition(1);
            }
        }
}
