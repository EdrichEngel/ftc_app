package org.firstinspires.ftc.teamcode.Main.Nationals;



import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMetaAndClass;

/*
 * Created by Edrich on 2018/02/27.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
@Disabled
public class MainTeleOp extends OpMode {// Declare Motors
    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackLeft;
    DcMotor DriveBackRight;
   /* DcMotor Glyph;
    DcMotor Arm;
    DcMotor Extending;
    // Declare Servos
    Servo Jewel;
    Servo Phone;
    Servo RightArm;
    Servo LeftArm;
    Servo RelicClaw;
    */// Declare Varuables
    double SpeedControl = 1;
    public double Power;
    double GlyphSpeed = 1;

    double FR_M = -1;
    double FL_M = 0.9;
    double BL_M = -0.9;
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
    int[] left_rot_dir = {1, -1};
    int[] right_rot_dir = {-1, 1};
    int rot_dir = -1;

    boolean rotate = false;
    boolean stick = false;

    int Selected = 0;


    public void init() {
// Hardwaremap motors and servos
        DriveFrontRight = hardwareMap.dcMotor.get("DriveFrontRight");
        DriveFrontLeft = hardwareMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hardwareMap.dcMotor.get("DriveBackLeft");
        DriveBackRight = hardwareMap.dcMotor.get("DriveBackRight");
        /*Glyph = hardwareMap.dcMotor.get("Glyph");
        Arm = hardwareMap.dcMotor.get("Arm");
        Extending = hardwareMap.dcMotor.get("Extending");
        Jewel = hardwareMap.servo.get("ServoArm");
        Phone = hardwareMap.servo.get("Phone");
        RightArm = hardwareMap.servo.get("RightArm");
        LeftArm = hardwareMap.servo.get("LeftArm");
        RelicClaw = hardwareMap.servo.get("RelicClaw");
// Set the direction of the motors
        Arm.setDirection(DcMotor.Direction.REVERSE);
        Glyph.setDirection(DcMotorSimple.Direction.REVERSE);
        */DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);
// Set the starting speed/power of the motors to 0
        DriveFrontLeft.setPower(0);
        DriveFrontRight.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
    /*    Glyph.setPower(0);
        Extending.setPower(0);
// Set the starting position(angle) of the servo
        Phone.setPosition(0);
        LeftArm.setPosition(0);
        RightArm.setPosition(1);
        Jewel.setPosition(0);
*/
    }


    public void Tank_Drive() {

        if ((gamepad1.left_bumper && gamepad1.right_bumper) == false) {
            DriveFrontLeft.setPower(gamepad1.left_stick_y * SpeedControl);
            DriveFrontRight.setPower(gamepad1.right_stick_y * SpeedControl);
            DriveBackLeft.setPower(gamepad1.left_stick_y * SpeedControl);
            DriveBackRight.setPower(gamepad1.right_stick_y * SpeedControl);
        }
// Move Left
        while (gamepad1.left_bumper) {

            DriveFrontLeft.setPower(1);
            DriveFrontRight.setPower(-1);
            DriveBackLeft.setPower(-1);
            DriveBackRight.setPower(1);
        }
// Move Right
        while (gamepad1.right_bumper) {

            DriveFrontLeft.setPower(-1);
            DriveFrontRight.setPower(1);
            DriveBackLeft.setPower(1);
            DriveBackRight.setPower(-1);

        }

    }



    //region Omnidirectional_Steering
    public void front_right(double power) {
        DriveFrontRight.setPower(power * FR_M);
    }

    public void front_left(double power) {
        DriveFrontLeft.setPower(power * FL_M);
    }

    public void back_left(double power) {
        DriveBackLeft.setPower(power * BL_M);
    }

    public void back_right(double power) {
        DriveBackRight.setPower(power * BR_M);
    }


    public void update_rightS_status() { //checks details for rotating
        if (gamepad1.right_stick_x != 0) { //checks if any bumper is active
          rotate = true; //updates boolean to choose which method is run
            if (gamepad1.right_stick_x > 0) {// below code determines and sets the direction of rotation
                rot_dir = 1;
                rot_power = (double) gamepad1.right_trigger;
            } else {
                rot_dir = 0;
                rot_power = (double) -gamepad1.left_trigger;
            }
        } else {
            rotate = false;
        }//rotate = false ensures that the rotate method does not run
    }

    public void update_leftS_status() {//determines whether movement in a direction is input from sticks to choose running method
        if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
            stick = true;
        } else {
            stick = false;
        }
    }


    public void update_motor_vect() {//gets input direction from sticks and outputs motor values to move in that direction with mechanum wheels
//check x for possible error (y/x)
        if (gamepad1.left_stick_x == 0) {
            if (gamepad1.left_stick_y > 0) {
                in_rad = Math.PI / 2;
            } else {
                in_rad = -Math.PI / 2;
            }
        }
//check + or - y (atan(y/x) = +/- ????)
        else if (gamepad1.left_stick_y == 0) {
            if (gamepad1.left_stick_x > 0) {
                in_rad = 0;
            } else {
                in_rad = Math.PI;
            }
        }
//determine rad
        else {
            if (gamepad1.left_stick_x > 0) {
                in_rad = Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);
            } else {
                in_rad = Math.PI + Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);
            }
        }

        vect_rad = in_rad - Math.PI / 4;  //Orientates the input direction to work practically with sin and cos
        vect_x = Math.cos(vect_rad);     //Gives the output power for the frontright and backleft motors
        vect_y = Math.sin(vect_rad);    //Gives the output power for the frontleft and backright motors
    }

    public void OM_Null() {
        front_right(0);
        front_left(0);
        back_left(0);
        back_right(0);
    }

    public void OM_Rotate() {
        front_right(rot_power * right_rot_dir[rot_dir]);
        front_left(rot_power * left_rot_dir[rot_dir]);
        back_left(rot_power * left_rot_dir[rot_dir]);
        back_right(rot_power * right_rot_dir[rot_dir]);
    }

    public void OM_Stick() {
        update_motor_vect();
//M = Math.sqrt((gamepad1.left_stick_x)*(gamepad1.left_stick_x)+(gamepad1.left_stick_y)*(gamepad1.left_stick_y));
//M = 1 when init
        front_right(vect_x * M);
        front_left(vect_y * M);
        back_left(vect_x * M);
        back_right(vect_y * M);

    }

    public void OM_StickAndRotate() {
        update_motor_vect();

        front_right(0.5 * ((vect_x * M) + (rot_power * right_rot_dir[rot_dir])));
        front_left(0.5 * ((vect_y * M) + (rot_power * left_rot_dir[rot_dir])));
        back_left(0.5 * ((vect_x * M) + (rot_power * left_rot_dir[rot_dir])));
        back_right(0.5 * ((vect_y * M) + (rot_power * right_rot_dir[rot_dir])));
    }

    public void Omnidirectional_Steering() {
        update_leftS_status();
        update_rightS_status();

        if (!stick && !rotate) {
            OM_Null();
        }
        if (!stick && rotate) {
            OM_Rotate();
        }
        if (stick && !rotate) {
            OM_Stick();
        }
        if (stick && rotate) {
            OM_StickAndRotate();
        }
    }

    //endregion

    //region Driver1_Non-Steering
    public void Gamepad1_Controls(){
/*
        if(gamepad1.right_bumper){RightArm.setPosition(0.90);}//right arm closed
        if(gamepad1.right_trigger > 0){RightArm.setPosition(.55);}//right arm open

        if(gamepad1.left_bumper){LeftArm.setPosition(0.1);}//right arm closed
        if(gamepad1.left_trigger > 0){LeftArm.setPosition(0.45);}//right arm open

        if (gamepad2.x){// Completely close clyph arms
            RightArm.setPosition(0.9);
            LeftArm.setPosition(0.1);
        }

        if (gamepad2.b){// 60° angle clyph arms
            RightArm.setPosition(0.5);
            LeftArm.setPosition(0.5);
        }

        if (gamepad2.a){// Release Glyph. Arms are bearly open
            RightArm.setPosition(0.7);
            LeftArm.setPosition(0.3);
        }

        if (gamepad2.y){
            RightArm.setPosition(0.55);
            LeftArm.setPosition(0.45);
        }
    }

    public void Gamepad1_Elevator_Controls(){
        ToDoElevatorServo.setPower(gamepad1.left_stick_y);
    }
*/
    //endregion

    //region Gamepad2
/*
    public void Gamepad2() {
        while (gamepad2.dpad_up)

        {
            Glyph.setPower(1 * GlyphSpeed);
        }
        while (gamepad2.dpad_down)

        {
            Glyph.setPower(-1 * GlyphSpeed);
        }

        Glyph.setPower(0);


// Completely open clyph arms
        if (gamepad2.x)

        {

            RightArm.setPosition(0.9);
            LeftArm.setPosition(0.1);
        }

// Completely close clyph arms
        if (gamepad2.b)

        {

            RightArm.setPosition(0.5);
            LeftArm.setPosition(0.5);
        }

// 60° angle clyph arms
        if (gamepad2.a)

        {

            RightArm.setPosition(0.7);
            LeftArm.setPosition(0.3);
        }

// Release Glyph. Arms are bearly open
        if (gamepad2.y)

        {

            RightArm.setPosition(0.55);
            LeftArm.setPosition(0.45);
        }
// Double lock to ensure that we don't accidentally release the relic
        if ((gamepad2.left_trigger > 0.5) && (gamepad2.right_trigger > 0.5))

        {
            RelicClaw.setPosition(0.2);
        }
// Close Relic claw
        if (gamepad2.dpad_right)

        {
            RelicClaw.setPosition(0);
        }
// Lock front motor when dropping relic


        Extending.setPower(gamepad2.left_stick_y);

        Arm.setPower(gamepad2.right_stick_y * Power);
*/
        if (gamepad2.left_bumper) {
            Power = 0.3;
            telemetry.addData("Power:", Power);
            telemetry.update();
        }
        if (gamepad2.right_bumper) {
            Power = 0.4;
            telemetry.addData("Power:", Power);
            telemetry.update();
        }

        /* Changing speed when picking up the glyph.
   Slower when going down.
   Faster when moving up.
*/
     //   if(gamepad1.left_trigger >0){arm.setpower(gamepad1.right_stick_y);}else{arm.setpower(gamepad1.left_trigger);}



        // else
        // {Arm.setPower((double)gamepad2.right_trigger*0.6);}


        //endregion

// Change the speed controll variable  for the drive motor multiplier.


        if (gamepad1.back) {
            SpeedControl = 0.4;
        }
        if (gamepad1.start) {
            SpeedControl = 1;
        }
    }

    //endregion

    @Override
    public void loop() {

     //   Gamepad2();
        Gamepad1_Controls();

        if (gamepad1.dpad_left && !gamepad1.dpad_up && !gamepad1.dpad_right){Selected = 0;}else
        if (!gamepad1.dpad_left && gamepad1.dpad_up && !gamepad1.dpad_right){Selected = 1;}else
        if (!gamepad1.dpad_left && !gamepad1.dpad_up && gamepad1.dpad_right){Selected = 2;}

        if      (Selected == 0){Tank_Drive();}
      //  else if (Selected == 2){OM_Null();Gamepad1_Elevator_Controls();}
        else if (Selected == 3){Omnidirectional_Steering();}

    }

}