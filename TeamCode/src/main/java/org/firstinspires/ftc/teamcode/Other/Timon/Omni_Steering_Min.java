package org.firstinspires.ftc.teamcode.Other.Timon;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
//@Disabled
public class Omni_Steering_Min extends OpMode {

    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackLeft;
    DcMotor DriveBackRight;


    double in_rad = 0;
    double vect_rad = 0;
    double vect_x = 0;
    double vect_y = 0;
    double M = 1;

    boolean bumper = false;
    boolean stick = false;




    public void init() {
        DriveFrontRight = hardwareMap.dcMotor.get("DriveFrontRight");
        DriveFrontLeft = hardwareMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hardwareMap.dcMotor.get("DriveBackLeft");
        DriveBackRight = hardwareMap.dcMotor.get("DriveBackRight");
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);
        DriveFrontLeft.setPower(0);
        DriveFrontRight.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
    }

    public void update_stick_status() {
        if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
            stick = true;
        } else {
            stick = false;
        }
    }

    public void update_motor_vect() {
//incase of x = 1
            if(gamepad1.left_stick_x == 0){
                if (gamepad1.left_stick_y > 0)
                {in_rad = Math.PI / 2;} else {in_rad = -Math.PI / 2;}
            }
//incase of x = -1
            else if(gamepad1.left_stick_y == 0){
                if(gamepad1.left_stick_x > 0)
                {in_rad = 0;} else {in_rad = Math.PI;}
            }

            else{

/*
        if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0){Q = 1;}
        if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0){Q = 2;}
        if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0){Q = 3;}
        if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0){Q = 4;}
*/
        if ((gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0) || (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0) )
        {in_rad = Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);}
        else{in_rad = Math.PI + Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);}
            }


        vect_rad = in_rad - Math.PI / 4;
        vect_x = Math.cos(vect_rad);
        vect_y = Math.sin(vect_rad);
    }//<3


//
//    public void Update_Multiplier(){
//
//    }

    public void MM_Null() {
        DriveFrontRight.setPower(0);
        DriveFrontLeft.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
    }

    public void MM_Stick() {
        update_motor_vect();
     /*   M = Math.sqrt((gamepad1.left_stick_x)*(gamepad1.left_stick_x)+(gamepad1.left_stick_y)*(gamepad1.left_stick_y));*/

        DriveFrontRight.setPower(vect_x);
        DriveFrontLeft.setPower(vect_y);
        DriveBackLeft.setPower(vect_x);
        DriveBackRight.setPower(vect_y);

    }


    @Override
    public void loop() {

        update_stick_status();
        update_motor_vect();

        if (stick == false) { MM_Null();}
        if (stick == true) { MM_Stick();}


    }
}