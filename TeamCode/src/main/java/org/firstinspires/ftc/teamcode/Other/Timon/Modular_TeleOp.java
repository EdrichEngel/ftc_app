package org.firstinspires.ftc.teamcode.Other.Timon;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by user on 2018-01-10.
 */


@com.qualcomm.robotcore.eventloop.opmode.TeleOp
@Disabled

public class Modular_TeleOp extends OpMode{

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
    
    double frontright = 0;
    double frontleft = 0;
    double backleft = 0;
    double backright = 0;

    double FR_multiplier = 1;
    double FL_multiplier = 1;
    double BL_multiplier = 1;
    double BR_multiplier = 1;


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


//region MotorErrFix
    public void front_right(double p){
        frontright = p * FR_multiplier;
    }
    public void front_left(double p){
        frontleft = p * FL_multiplier;
    }
    public void back_left(double p){
        backleft = p * BL_multiplier;
    }
    public void back_right(double p){
        backright = p * BR_multiplier;
    }
//endregion

//region Omnidirectional_Steering
    double in_rad = 0;
    double vect_rad = 0;
    double vect_x = 0;
    double vect_y = 0;
    //double M = 1;

    int[] left_rot_dir = {1,-1};
    int[] right_rot_dir = {-1,1};
    int rot_dir = -1;
    double rot_power;


    boolean bumper = false;
    boolean stick = false;




    public void update_stick_status() {
        if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {stick = true;} else {stick = false;}
    }

    public void update_bumper_status(){
        if (gamepad1.left_trigger > 0 || gamepad1.right_trigger > 0){
            if(!(gamepad1.left_trigger > 0) || !(gamepad1.right_trigger > 0)){
                bumper = true;
                if (gamepad1.left_trigger > 0)
                    {rot_dir = 0; rot_power = (double)gamepad1.left_trigger;}else
                    {rot_dir = 1; rot_power = (double)gamepad1.right_trigger;}
            }else {bumper = false;}
        }else {bumper = false;}
    }

    public void update_motor_vect() {
    //in case of x = 0
        if(gamepad1.left_stick_x == 0){
            if (gamepad1.left_stick_y > 0)
            {in_rad = Math.PI / 2;} else {in_rad = -Math.PI / 2;}
        }
    //in case of y = 0 and we need a direction
        else if(gamepad1.left_stick_y == 0){
            if(gamepad1.left_stick_x > 0)
            {in_rad = 0;} else {in_rad = Math.PI;}
        }

        else{
            if (gamepad1.left_stick_x > 0)
            {in_rad = Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);}
            else{in_rad = Math.PI + Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);}
        }


        vect_rad = in_rad - Math.PI / 4;
        vect_x = Math.cos(vect_rad);
        vect_y = Math.sin(vect_rad);
    }//<3


    public void Omni_MM_Null() {
        front_right(0);
        front_left(0);
        back_left(0);
        back_right(0);
    }

    public void Omni_MM_Bumpers(){
        front_right(rot_power*right_rot_dir[rot_dir]);
        front_left(rot_power*left_rot_dir[rot_dir]);
        back_left(rot_power*left_rot_dir[rot_dir]);
        back_right(rot_power*right_rot_dir[rot_dir]);
    }

    public void Omni_MM_Stick(){
        update_motor_vect();

        front_right(vect_x);
        front_left(vect_y);
        back_left(vect_x);
        back_right(vect_y);
    }

    public void Omni_MM_Stick_And_Bumper(){
        update_motor_vect();

        front_right(0.5*(vect_x+rot_power*right_rot_dir[rot_dir]));
        front_left(0.5*(vect_y+rot_power*left_rot_dir[rot_dir]));
        back_left(0.5*(vect_x+rot_power*left_rot_dir[rot_dir]));
        back_right(0.5*(vect_y+rot_power*right_rot_dir[rot_dir]));
    }


    public void Omnidirectional_Steering(){
        update_stick_status();
        update_bumper_status();

        if(!stick && !bumper){Omni_MM_Null();}
        if(!stick && bumper){Omni_MM_Bumpers();}
        if(stick && !bumper){Omni_MM_Stick();}
        if(stick && bumper){Omni_MM_Stick_And_Bumper();}
    }

//endregion

//region Tank_Drive

    public void Tank_drive(){


        if (gamepad1.back){
            SpeedControl = 0.5;
        }
        if (gamepad1.start){
            SpeedControl = 1;
        }

        if ((gamepad1.left_bumper && gamepad1.right_bumper) == false)
        {

            front_left(gamepad1.left_stick_y);
            front_right(gamepad1.right_stick_y);
            back_left(gamepad1.left_stick_y);
            back_right(gamepad1.right_stick_y);
        }
        while (gamepad1.right_bumper) {
            SpeedControl = gamepad1.right_stick_y;
            front_left(1 * SpeedControl);
            front_right(-1 * SpeedControl);
            back_left(-1 * SpeedControl);
            back_right(1 * SpeedControl);
        }
        while (gamepad1.left_bumper) {
            SpeedControl = gamepad1.right_stick_y;
            front_left(-1 * SpeedControl);
            front_right(1 * SpeedControl);
            back_left(1 * SpeedControl);
            back_right(-1 * SpeedControl);

        }

    }
//endregion

//region RelicMode/InvertedTankDrive
public void Inverted_Tank_drive(){

    if (gamepad1.back){
        SpeedControl = 0.3;
    }
    if (gamepad1.start){
        SpeedControl = 1;
    }

    if ((gamepad1.left_bumper && gamepad1.right_bumper) == false)
    {

        front_left(gamepad1.left_stick_y*-1);
        front_right(gamepad1.right_stick_y*-1);
        back_left(gamepad1.left_stick_y*-1);
        back_right(gamepad1.right_stick_y*-1);
    }
    while (gamepad1.right_bumper) {
        SpeedControl = gamepad1.right_stick_y;
        front_left(1 * SpeedControl);
        front_right(-1 * SpeedControl);
        back_left(-1 * SpeedControl);
        back_right(1 * SpeedControl);
    }
    while (gamepad1.left_bumper) {
        SpeedControl = gamepad1.right_stick_y;
        front_left(-1 * SpeedControl);
        front_right(1 * SpeedControl);
        back_left(1 * SpeedControl);
        back_right(-1 * SpeedControl);

    }

}
//endregion
//Single driver nog nie klaar nie, moet omni steering op hom om al die knoppies in te pas
//region SingleDriver



    public void singleD_update_bumper_status(){
        if (gamepad1.right_stick_x != 0){
            bumper = true;
            rot_dir = 0;
            rot_power = gamepad1.left_stick_x;

        }else {bumper = false;}
    }

    public void Single_Driver(){




        while (gamepad1.right_trigger > 0)
        {
            Glyph.setPower((double)gamepad1.right_trigger*GlyphSpeed);
        }
        while (gamepad2.dpad_down)
        {
            Glyph.setPower(-1*(double)gamepad1.right_trigger*GlyphSpeed);
        }

        Glyph.setPower(0);



        if (gamepad1.x){

            RightArm.setPosition(0.9);
            LeftArm.setPosition(0.1);
        }
        if (gamepad1.b) {

            RightArm.setPosition(0.5);
            LeftArm.setPosition(0.5);
        }

        if (gamepad1.a) {

            RightArm.setPosition(0.7);
            LeftArm.setPosition(0.3);
        }


        if (gamepad1.y) {

            RightArm.setPosition(0.55);
            LeftArm.setPosition(0.45);
        }

        if (gamepad1.dpad_left){
            RelicClaw.setPosition(0);
        }
        if (gamepad1.dpad_right){
            RelicClaw.setPosition(0.2);
        }

        while (gamepad2.right_bumper == true){
            Arm.setPower(0);
        }




        Extending.setPower(gamepad2.left_stick_y);
        Arm.setPower(gamepad2.right_stick_y*-0.5);


    }       
    
//endregion
    
//region Gamepad2
    public void Gamepad2(){
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
        Arm.setPower(gamepad2.right_stick_y*-0.5);

    }

//endregion


    //Non-modular methods
    int Selected = 0;
    void Select_Mode() {

        //Select left setting
        if (gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_right && !gamepad1.dpad_down) {Selected = 0;}
        //Select right setting
        if (!gamepad1.dpad_right && gamepad1.dpad_up && !gamepad1.dpad_left && !gamepad1.dpad_down) {Selected = 1;}
        //Select top setting
        if (!gamepad1.dpad_right && !gamepad1.dpad_up && !gamepad1.dpad_left && gamepad1.dpad_down) {Selected = 2;}

        if (gamepad1.dpad_right && !gamepad1.dpad_up && !gamepad1.dpad_left && !gamepad1.dpad_down) {Selected = 3;}
        }

    @Override
    public void loop() {
        Gamepad2();
        Select_Mode();

        if(Selected == 0){Tank_drive();}//left
        if(Selected == 1){Omnidirectional_Steering();}//up
        if(Selected == 2){Single_Driver();}//down
        if(Selected == 3){Inverted_Tank_drive();}//right

        DriveFrontLeft.setPower(frontleft);
        DriveFrontRight.setPower(frontright);
        DriveBackLeft.setPower(backleft);
        DriveBackRight.setPower(backright);


        telemetry.addData("Active Setting:", Selected);
        telemetry.update();
    }
}
