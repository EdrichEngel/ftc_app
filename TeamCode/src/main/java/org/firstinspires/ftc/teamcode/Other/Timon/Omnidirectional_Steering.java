package org.firstinspires.ftc.teamcode.Other.Timon;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
@Disabled
public class Omnidirectional_Steering extends OpMode {

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

    double GlyphSpeed = 1;

    double FR_M = 1;
    double FL_M = 0.9;
    double BL_M = 0.9;
    double BR_M = 1;

    double FrontRight = 0;
    double FrontLeft = 0;
    double BackLeft = 0;
    double BackRight = 0;

    double in_rad = 0;
    double vect_rad = 0;
    double vect_x = 0;
    double vect_y = 0;
    double M = 1;
    double rot_power = 0;
    int[] left_rot_dir = {1,-1};
    int[] right_rot_dir = {-1,1};
    int rot_dir = -1;

    boolean rotate = false;
    boolean stick = false;

    double Power = 0.4;



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


    public void front_right(double power){FrontRight = power*FR_M;}
    public void  front_left(double power){FrontLeft = power*FL_M;}
    public void  back_left(double power){BackLeft = power*BL_M;}
    public void back_right(double power){BackRight = power*BR_M;}

    public void update_bumper_status(){
        if(gamepad1.left_trigger > 0 || gamepad1.right_trigger > 0 ){
            if(gamepad1.left_trigger == 0 || gamepad1.right_trigger == 0 ){
                rotate = true;
                if(gamepad1.right_trigger > 0){
                    rot_dir = 1;
                    rot_power = (double)gamepad1.right_trigger;
                }else{
                    rot_dir = 0;
                    rot_power = (double)gamepad1.left_trigger;
                }
            }else{rotate = false;}
        }else{rotate = false;}
    }

    public void update_stick_status() {
        if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0)
        {stick = true;} else {stick = false;}
    }


    public void update_motor_vect() {
//check x for possible error (y/x)
        if(gamepad1.left_stick_x == 0){
            if (gamepad1.left_stick_y > 0)
            {in_rad = Math.PI / 2;} else {in_rad = -Math.PI / 2;}
        }
//check + or - y (atan(y/x) = +/- ????)
        else if(gamepad1.left_stick_y == 0){
            if(gamepad1.left_stick_x > 0)
            {in_rad = 0;} else {in_rad = Math.PI;}
        }
//determine rad
        else{
            if (gamepad1.left_stick_x > 0)
            {in_rad = Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);}
            else{in_rad = Math.PI + Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);}
        }

        vect_rad = in_rad - Math.PI / 4;
        vect_x = Math.cos(vect_rad);
        vect_y = Math.sin(vect_rad);
    }

    public void OM_Null() {
        front_right(0);
        front_left(0);
        back_left(0);
        back_right(0);
    }

    public void OM_Rotate(){
        front_right(rot_power*right_rot_dir[rot_dir]);
        front_left(rot_power*left_rot_dir[rot_dir]);
        back_left(rot_power*left_rot_dir[rot_dir]);
        back_right(rot_power*right_rot_dir[rot_dir]);
    }

    public void OM_Stick() {
        update_motor_vect();
//M = Math.sqrt((gamepad1.left_stick_x)*(gamepad1.left_stick_x)+(gamepad1.left_stick_y)*(gamepad1.left_stick_y));
//M = 1 when init
        front_right(vect_x*M);
        front_left(vect_y*M);
        back_left(vect_x*M);
        back_right(vect_y*M);

    }

    public void OM_StickAndRotate(){
        update_motor_vect();

        front_right(0.5*((vect_x*M)+(rot_power*right_rot_dir[rot_dir])));
        front_left(0.5*((vect_y*M)+(rot_power*left_rot_dir[rot_dir])));
        back_left(0.5*((vect_x*M)+(rot_power*left_rot_dir[rot_dir])));
        back_right(0.5*((vect_y*M)+(rot_power*right_rot_dir[rot_dir])));
    }

    public void Omnidirection_Steerining_Loop(){
        update_stick_status();
        update_bumper_status();

        if(!stick && !rotate){OM_Null();}
        if(!stick && rotate) {OM_Rotate();}
        if(stick  && !rotate){OM_Stick();}
        if(stick  && rotate) {OM_StickAndRotate();}

    }

    @Override
    public void loop() {

        Omnidirection_Steerining_Loop();

        DriveFrontRight.setPower(FrontRight);
        DriveFrontLeft.setPower(FrontLeft);
        DriveBackLeft.setPower(BackLeft);
        DriveBackRight.setPower(BackRight);

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


        if (gamepad2.y) {

            RightArm.setPosition(0.55);
            LeftArm.setPosition(0.45);
        }

        if (gamepad2.dpad_left){
            RelicClaw.setPosition(0);
        }
        if (gamepad2.dpad_right){
            RelicClaw.setPosition(0.2);
        }

        while (gamepad2.right_bumper == true){
            Arm.setPower(0);
        }

        Extending.setPower(gamepad2.left_stick_y);

        Arm.setPower(gamepad2.right_stick_y*Power);

        if (gamepad2.back){
            Power = 0.1;
        }
        if (gamepad2.start){
            Power = 0.4;
        }

        /*
        if((gamepad2.right_trigger == 0) &&(gamepad2.right_trigger != 0)){
            Arm.setPower(gamepad2.left_trigger*-0.2);
        }
        if((gamepad2.left_trigger == 0) &&(gamepad2.left_trigger != 0)){
            Arm.setPower(gamepad2.right_trigger*0.2);
        }
        Arm.setPower(0);
  */      /*
        if(gamepad2.right_stick_y > 0)
        {Arm.setPower(gamepad2.right_stick_y*-0.3);}
        else
        {Arm.setPower((double)gamepad2.right_trigger*0.4);}
*/


    }
}
