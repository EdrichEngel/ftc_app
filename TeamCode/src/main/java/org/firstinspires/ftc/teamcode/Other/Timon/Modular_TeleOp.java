package org.firstinspires.ftc.teamcode.Other.Timon;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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




//crab mode=========================================================================================================
    int MQ = 0;//MQ=movement quadrant(0-3)
    int[]x_pair = {1,-1,-1,1};//The FL and BR motors give same vector output, motors cross vect_x line when drawn
    int[]y_pair = {1,1,-1,-1};//The FR and BL motors give same vector output, motors cross vect_y line when drawn

    double in_rad = 0;
    double vect_rad = 0;

    double FR_rot =0;
    double FL_rot =0;
    double BL_rot =0;
    double BR_rot =0;

    boolean bumper = false;
    boolean stick = false;

    public void update_bumper_status(){

    if (!((!gamepad1.left_bumper && !gamepad1.right_bumper) || (gamepad1.left_bumper && gamepad1.right_bumper)))
    {bumper=true;}else {bumper=false;}

}
    public void update_stick_status() {
        if ( gamepad1.left_stick_x !=0 ||  gamepad1.left_stick_y !=0) {stick=true;}else{stick=false;}
    }

    public void update_crab_dir() {
        if(gamepad1.left_stick_x == 0){
            if (gamepad1.left_stick_y > 0)
            {in_rad = Math.PI / 2;} else {in_rad = -Math.PI / 2;}
        }

        else if(gamepad1.left_stick_y == 0){
            if(gamepad1.left_stick_x > 0)
            {in_rad = Math.PI;} else {in_rad = -Math.PI;}
        }

        else{in_rad = Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);}


        vect_rad=in_rad-Math.PI/4;

        MQ = 2+(int)(Math.ceil(vect_rad/(Math.PI/2)));

    }

    public void set_rotation() {
        if (!bumper) {
            FR_rot =0;
            FL_rot =0;
            BL_rot =0;
            BR_rot =0;
        }else if(gamepad1.left_bumper) {
            FR_rot = 1;
            FL_rot = -1;
            BL_rot = -1;
            BR_rot = 1;
        }else {
            FR_rot = -1;
            FL_rot = 1;
            BL_rot = 1;
            BR_rot = -1;
        }

    }


    //MM staan vir motor method
    //PM staan vir power multiplier
    /*
    public void MM_Null() {
        DriveFrontRight.setPower(0);
        DriveFrontLeft.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
    }

    public void MM_Stick() {
        update_crab_dir();

        DriveFrontRight.setPower(x_pair[MQ] );
        DriveFrontLeft.setPower(y_pair[MQ] );
        DriveBackLeft.setPower(x_pair[MQ] );
        DriveBackRight.setPower(y_pair[MQ] );

    }

    public void MM_Bumper() {
        set_rotation();

        DriveFrontRight.setPower(FR_rot);
        DriveFrontLeft.setPower(FL_rot);
        DriveBackLeft.setPower(BL_rot);
        DriveBackRight.setPower(BR_rot);
    }

    public void MM_StickAndBumper() {
        update_crab_dir();
        set_rotation();

        DriveFrontRight.setPower(0.5 * (x_pair[MQ] + FR_rot));
        DriveFrontLeft.setPower(0.5 * (y_pair[MQ] + FL_rot));
        DriveBackLeft.setPower(0.5 * (x_pair[MQ] + BL_rot));
        DriveBackRight.setPower(0.5 * (y_pair[MQ] + BR_rot));

    }
*/
    public void Crab_loop() {

        update_bumper_status();
        update_stick_status();
        update_crab_dir();
        telemetry.addData("Die bliksemse kwardrant",MQ);
/*
        if (!bumper && !stick) {MM_Null();}
        if (!bumper && stick) {MM_Stick();}
        if (bumper && !stick) {MM_Bumper();}
        if (bumper && stick) {MM_StickAndBumper();}
*/
    }


    //Tank drive===============================================
    double SpeedControl = 1;

    public void Tank_loop(){


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

    }

//Omnidirection_Steering


    double vect_x = 0;
    double vect_y = 0;

    public void update_motor_vect() {
        if(gamepad1.left_stick_x == 0){
            if (gamepad1.left_stick_y > 0)
            {in_rad = Math.PI / 2;} else {in_rad = -Math.PI / 2;}
        }

        else if(gamepad1.left_stick_y == 0){
            if(gamepad1.left_stick_x > 0)
            {in_rad = Math.PI;} else {in_rad = -Math.PI;}
        }

        else{in_rad = Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);}


        vect_rad = in_rad - Math.PI / 4;
        vect_x = Math.cos(vect_rad);
        vect_y = Math.sin(vect_rad);
    }//<3

      /*Minder compiler werk weergawe:
      if (gamepad1.leftstick_x != 0) {vect_rad=Math.atan(gamepad1.leftstick_x/gamepad1.leftstick_y)-Math.PI/4;}
         else if(gamepad1.leftstick_y>0){vect_rad=Math.PI/4}else {vect_rad=-Math.PI/(4/3)}

         vect_x=Math.cos(vect_rad);
         vect_y=Math.sin(vect_rad);
       */



    //MM staan vir motor method
    //PM staan vir power multiplier
    public void Omni_MM_Null() {
        DriveFrontRight.setPower(0);
        DriveFrontLeft.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
    }

    public void Omni_MM_Stick() {
        update_motor_vect();

        DriveFrontRight.setPower(vect_x);
        DriveFrontLeft.setPower(vect_y);
        DriveBackLeft.setPower(vect_x);
        DriveBackRight.setPower(vect_y);

    }

    public void Omni_loop() {

        update_stick_status();


        if(!stick){Omni_MM_Null();}
        if(stick){update_motor_vect();Omni_MM_Stick();}

    }

    //Non-modular methods
    int Selected = 0;
    void Select_Mode() {

        //Select left setting
        if (gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_right) {Selected = 0;}
        //Select right setting
        if (!gamepad1.dpad_right && gamepad1.dpad_up && !gamepad1.dpad_left) {Selected = 1;}
        //Select top setting
        if (gamepad1.dpad_right && !gamepad1.dpad_up && !gamepad1.dpad_left) {Selected = 2;}
        }


    @Override
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




    @Override
    public void loop() {
        Select_Mode();

        if(Selected == 0){Tank_loop();}
        if(Selected == 1){Crab_loop();}
        if(Selected == 2){Omni_loop();}
        telemetry.addData("Active Setting:", Selected);
        telemetry.addData("in_rad:",in_rad);
        telemetry.addData("Vect_rad:",vect_rad);
        telemetry.addData("Vect_x:",vect_x);
        telemetry.addData("Vect_y:",vect_y);
        telemetry.update();
    }
}
