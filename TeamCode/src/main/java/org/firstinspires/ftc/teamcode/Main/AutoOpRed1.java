package org.firstinspires.ftc.teamcode.Main;


import android.opengl.GLU;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Edrich on 9/23/2017.
 */

@Autonomous(name = "Red1", group = "Red")
@Disabled
public class AutoOpRed1 extends LinearOpMode
{
    DcMotor DriveFrontLeft;
    DcMotor DriveFrontRight;
    DcMotor DriveBackLeft;
    DcMotor DriveBackRight;
    DcMotor Glyph;
    Servo Arm;
    Servo Phone;
    Servo RightArm;
    Servo LeftArm;
    String StringVuForia;
    boolean VuLeft;
    boolean VuCentre;
    boolean VuRight;
    String Colour;
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime timer = new ElapsedTime();
    int zAccumulated;
    int target = 0;
    GyroSensor sensorGyro;
    ModernRoboticsI2cGyro mrGyro;
    int PillarsToBePassed;
    private I2cDevice colorC;
    private I2cDeviceSynch color_C_reader;
    private byte[] colorCcache;
    double Pillars;
    double GyroX;




    ModernRoboticsI2cRangeSensor rangeSensor;

    double VuforiaActive = 1;
    private RelicRecoveryVuMark FoundVumark;
    private RelicRecoveryVuMark DecodedMessage;
    public static final String TAG = "Vuforia VuMark Sample";
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        InitSystem();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AUO3EHb/////AAAAGSI5YtsUZUnXmqs2hS+aWDczQOW35bYvHctTDIwu8Cri2uQQQMF806Y9y19+y/tRwcx7BcTtmonYebC+34yGbTFKEYk7WKScXsAsdkb0F+D36udYE0b4Y5ytuFgzFoimN7gLa4P2xhrgfuBjgBJtDIhVlDECMiQaASZBdrUUHPIDDLe8BLQ0Pqa/tj4D6L4Lr68Pwr/PR4JYov8NncvJtdG7WvDtJFY4fqRGWCoLPwvAkvmDUmoTRlovnpiyDpdn0mhaLIY7baSn0VspvIoxY8utZgjOpsOF3WJM88GVaijqus5p1j47aNFJtPOGYfwaSEjiHUbigyohkcsTAg65Bl2469QJNTScnuwk1jAWtXJj";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
        relicTrackables.activate();
        AfterWaitForStart();
        mrGyro.resetZAxisIntegrator();


        while (opModeIsActive()) {

            while (VuforiaActive == 1) {
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                    telemetry.addData("VuMark", "%s visible", vuMark);
                    FoundVumark = vuMark;
                    RelicRecoveryVuMark DecodedMessage = RelicRecoveryVuMark.from(relicTemplate);

                    VuforiaActive = 0;
                    if (DecodedMessage.equals(RelicRecoveryVuMark.CENTER)) {
                        VuCentre = true;
                    }
                    if (DecodedMessage.equals(RelicRecoveryVuMark.LEFT)) {
                        VuLeft = true;
                    }
                    if (DecodedMessage.equals(RelicRecoveryVuMark.RIGHT)) {
                        VuRight = true;
                    }
                } else {
                    telemetry.addData("VuMark", "not visible");
                }
                telemetry.update();
            }
            telemetry.addData("Vuforia", "Finished");
            telemetry.update();
            telemetry.clearAll();
            telemetry.addData("Centre", "%s visible", VuCentre);
            telemetry.addData("Left", "%s visible", VuLeft);
            telemetry.addData("Right", "%s visible", VuRight);
            telemetry.update();
            VuLogic();
            GlyphPickUp();
            Jewel();
            Arm.setPosition(0.1);
            Thread.sleep(2000);
            DriveGyroToRange(0.3);
            PillarsToBePassed = 1;
            DriveGyroToRange(-0.05);
            GyroTurn(-70,0.2);
            Drive(400,0.1);
            DropGlyph();
            Drive(-100,0.3);
            stop();

        }
    }

    public void InitSystem() {


        DriveFrontRight = hardwareMap.dcMotor.get("DriveFrontRight");
        DriveFrontLeft = hardwareMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hardwareMap.dcMotor.get("DriveBackLeft");
        DriveBackRight = hardwareMap.dcMotor.get("DriveBackRight");
        Glyph = hardwareMap.dcMotor.get("Glyph");
        Arm = hardwareMap.servo.get("ServoArm");
        Phone = hardwareMap.servo.get("Phone");
        RightArm = hardwareMap.servo.get("RightArm");
        LeftArm =hardwareMap.servo.get("LeftArm");
        DriveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        DriveBackLeft.setDirection(DcMotor.Direction.REVERSE);
        Glyph.setDirection(DcMotorSimple.Direction.REVERSE);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setPosition(0);
        Phone.setPosition(1);
        RightArm.setPosition(0.5);
        LeftArm.setPosition(0.5);
        VuCentre = false;
        VuRight = false;
        VuLeft = false;

        colorC = hardwareMap.i2cDevice.get("cc");
        color_C_reader = new I2cDeviceSynchImpl(colorC,I2cAddr.create8bit(0x3c),false);
        color_C_reader.engage();



        sensorGyro = hardwareMap.gyroSensor.get("gyro");
        mrGyro = (ModernRoboticsI2cGyro) sensorGyro;
        mrGyro.calibrate();
        Colour = "None";
        Pillars = 0;
    }


    public void AfterWaitForStart()
    {
           while (mrGyro.isCalibrating()) {}
        runtime.reset();
    }

    public void StopDriveTrain(){
        DriveFrontLeft.setPower(0);
        DriveFrontRight.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);

    }

     public void GyroTurn(int target, double TurnSpeed) {
            zAccumulated = mrGyro.getIntegratedZValue();
            while (Math.abs(zAccumulated - target) > 3 && opModeIsActive()) {
                if (zAccumulated > target) {
                    DriveFrontLeft.setPower(TurnSpeed);
                    DriveFrontRight.setPower(-TurnSpeed);
                    DriveBackLeft.setPower(TurnSpeed);
                    DriveBackRight.setPower(-TurnSpeed);
                }

                if (zAccumulated < target) {
                    DriveFrontLeft.setPower(-TurnSpeed);
                    DriveFrontRight.setPower(TurnSpeed);
                    DriveBackLeft.setPower(-TurnSpeed);
                    DriveBackRight.setPower(TurnSpeed);
                }
                zAccumulated = mrGyro.getIntegratedZValue();
                telemetry.addData("accu", String.format("%03d", zAccumulated));
                telemetry.update();
            }
            DriveFrontLeft.setPower(0);
            DriveFrontRight.setPower(0);
            DriveBackLeft.setPower(0);
            DriveBackRight.setPower(0);
        }
    private void DriveForward(double power) {

        DriveFrontLeft.setPower(power);
        DriveBackLeft.setPower(power);
        DriveBackRight.setPower(power);
        DriveFrontRight.setPower(power);
    }



    private void stopRobot() {
        DriveFrontLeft.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
        DriveFrontRight.setPower(0);
    }

        public void Drive(int distance, double power){

                // Reset encoders
                DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                // Change to run to position prior to setting target
                // Run to position
            DriveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            DriveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // Set target position
            DriveFrontLeft.setTargetPosition(distance);
            DriveBackLeft.setTargetPosition(distance);
            DriveBackRight.setTargetPosition(distance);
            DriveFrontRight.setTargetPosition(distance);

                // Set drive power
                DriveForward(power);

                // wait until position is reached
                while (opModeIsActive() && DriveFrontLeft.isBusy() && DriveFrontRight.isBusy()) idle();

                //stopRobot and change modes back to normal
                stopRobot();
            DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        }

    public void DriveForwardGyro(int distance, double power){
        double leftSpeed; //Power to feed the motors
        double rightSpeed;
        double target = mrGyro.getIntegratedZValue();  //Starting direction
        double startPosition = DriveFrontLeft.getCurrentPosition();  //Starting position
        while (DriveFrontLeft.getCurrentPosition() < distance + startPosition && opModeIsActive()) {  //While we have not passed out intended distance
            zAccumulated = mrGyro.getIntegratedZValue();  //Current direction
            leftSpeed = power + (zAccumulated - target) / 100;  //Calculate speed for each side
            rightSpeed = power - (zAccumulated - target) / 100;  //See Gyro Straight video for detailed explanation
            leftSpeed = Range.clip(leftSpeed, -1, 1);
            rightSpeed = Range.clip(rightSpeed, -1, 1);
            DriveFrontLeft.setPower(leftSpeed);
            DriveBackLeft.setPower(leftSpeed);
            DriveBackRight.setPower(rightSpeed);
            DriveFrontRight.setPower(rightSpeed);
            telemetry.addData("1. Left", DriveFrontLeft.getPower());
            telemetry.addData("2. Right", DriveFrontRight.getPower());
            telemetry.addData("3. Distance to go", distance + startPosition - DriveFrontLeft.getCurrentPosition());
            telemetry.update();
        }

        DriveFrontLeft.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
        DriveFrontRight.setPower(0);
        DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void Jewel() throws InterruptedException {

        color_C_reader = new I2cDeviceSynchImpl(colorC,I2cAddr.create8bit(0x3c),false);
        color_C_reader.engage();
        Arm.setPosition(0.45);
        Thread.sleep(1000);
        colorCcache = color_C_reader.read(0x04,1);
        if (colorCcache[0] < 7) {
            Colour = "Blue";
        } else {
            Colour = "Red";
        }
        if (Colour.matches("Red")){
            telemetry.addData("Red", "%s visible");
            telemetry.update();
            //DriveForwardGyro(-300,-0.1);
            GyroTurn(-15,0.1);
            Thread.sleep(1000);
            GyroTurn(0,0.1);
        }
        if (Colour.matches("Blue")){
            telemetry.addData("Blue", "%s visible");
            telemetry.update();
            //DriveForwardGyro(300,0.1);
            GyroTurn(15,0.1);
            Thread.sleep(1000);
            GyroTurn(0,0.1);
        }
    }

    public void GlyphPickUp(){

        RightArm.setPosition(0.5);
        LeftArm.setPosition(0.5);
        GlyphMotor(800,1);

    }

    public void VuLogic(){
        if (VuLeft == true){
            PillarsToBePassed = 3;
            telemetry.addData("Pillars to be passed:","3");
            telemetry.update();
        }
        if (VuCentre == true){
            PillarsToBePassed = 2;
            telemetry.addData("Pillars to be passed:","2");
            telemetry.update();
        }
        if (VuRight == true){
            PillarsToBePassed = 1;
            telemetry.addData("Pillars to be passed:","1");
            telemetry.update();
        }
        else{
            PillarsToBePassed = 1;
            telemetry.addData("Error", "%s visible", "Don't know how many pillars to be passed");
        }
    }
    public void Range() throws InterruptedException {

        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range_test");
        while (Pillars != PillarsToBePassed ) {


            if (rangeSensor.rawUltrasonic() < 12) {
                Pillars = Pillars+1 ;
                telemetry.addData("Pillars Passed:",Pillars);
                telemetry.addData("Distance:", rangeSensor.getDistance(DistanceUnit.CM));
                telemetry.update();
                Thread.sleep(500);
            }
        }
        if (Pillars == PillarsToBePassed){
            telemetry.addData("Finished:","True");
            telemetry.update();
            StopDriveTrain();
        }

    }
    public void GlyphMotor(int distance, double power){


            // Reset encoders
        Glyph.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Change to run to position prior to setting target
            // Run to position

        Glyph.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Set target position
        Glyph.setTargetPosition(distance);


            // Set drive power
        Glyph.setPower(0.2);

            // wait until position is reached
            while (opModeIsActive() && Glyph.isBusy() ) idle();

            //stopRobot and change modes back to normal
        Glyph.setPower(0);

        Glyph.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    public void DriveGyroToRange(double power) throws InterruptedException {

/*        DriveFrontLeft.setPower(0);
        DriveBackLeft.setPower(0);
        DriveBackRight.setPower(0);
        DriveFrontRight.setPower(0);
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
*/
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range_test");
            double leftSpeed; //Power to feed the motors
            double rightSpeed;
            double target = mrGyro.getIntegratedZValue();  //Starting direction
        Pillars = 0;

        while (Pillars != PillarsToBePassed ) {  //While we have not passed out intended distance
                zAccumulated = mrGyro.getIntegratedZValue();  //Current direction
                leftSpeed = power + (zAccumulated - target) / 100;  //Calculate speed for each side
                rightSpeed = power - (zAccumulated - target) / 100;  //See Gyro Straight video for detailed explanation
                leftSpeed = Range.clip(leftSpeed, -1, 1);
                rightSpeed = Range.clip(rightSpeed, -1, 1);
                DriveFrontLeft.setPower(leftSpeed);
                DriveBackLeft.setPower(leftSpeed);
                DriveBackRight.setPower(rightSpeed);
                DriveFrontRight.setPower(rightSpeed);
                telemetry.addData("1. Left", DriveFrontLeft.getPower());
                telemetry.addData("2. Right", DriveFrontRight.getPower());
                telemetry.addData("3. Left Power Var",leftSpeed);
                telemetry.addData("4. Right Power Var",rightSpeed);
                telemetry.update();
            if (rangeSensor.rawUltrasonic() < 16) {
                Pillars = Pillars+1 ;
                telemetry.addData("Pillars Passed:",Pillars);
                telemetry.addData("Distance:", rangeSensor.getDistance(DistanceUnit.CM));
                telemetry.update();
                Thread.sleep(500);
            }
            }

            DriveFrontLeft.setPower(0);
            DriveBackLeft.setPower(0);
            DriveBackRight.setPower(0);
            DriveFrontRight.setPower(0);
        }


        public void DropGlyph(){
            RightArm.setPosition(0.9);
            LeftArm.setPosition(0.1);
            GlyphMotor(-700,-1);
        }


        public void DriveOffStone(double Speed) throws InterruptedException {
            DriveFrontLeft.setPower(Speed);
            DriveBackLeft.setPower(Speed);
            DriveBackRight.setPower(Speed);
            DriveFrontRight.setPower(Speed);
            Thread.sleep(250);
            while ((GyroX != 0) && opModeIsActive()) {
                GyroX = mrGyro.rawX();
                DriveFrontLeft.setPower(Speed);
                DriveBackLeft.setPower(Speed);
                DriveBackRight.setPower(Speed);
                DriveFrontRight.setPower(Speed);
            }


            DriveFrontLeft.setPower(0);
            DriveBackLeft.setPower(0);
            DriveBackRight.setPower(0);
            DriveFrontRight.setPower(0);

        }
}





