package org.firstinspires.ftc.teamcode.Main.Worlds;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Edric on 2018/03/31.
 */

@Autonomous(name = "Blue1", group = "Blue")
public class Blue1 extends LinearOpMode {
    AutoOpHardware robot = new AutoOpHardware();
    public RelicRecoveryVuMark DecodedMessage;
    public static final String TAG = "Vuforia VuMark Sample";
    public VuforiaLocalizer vuforia;
    double VuforiaActive = 1;
    int zAccumulated;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AUO3EHb/////AAAAGSI5YtsUZUnXmqs2hS+aWDczQOW35bYvHctTDIwu8Cri2uQQQMF806Y9y19+y/tRwcx7BcTtmonYebC+34yGbTFKEYk7WKScXsAsdkb0F+D36udYE0b4Y5ytuFgzFoimN7gLa4P2xhrgfuBjgBJtDIhVlDECMiQaASZBdrUUHPIDDLe8BLQ0Pqa/tj4D6L4Lr68Pwr/PR4JYov8NncvJtdG7WvDtJFY4fqRGWCoLPwvAkvmDUmoTRlovnpiyDpdn0mhaLIY7baSn0VspvIoxY8utZgjOpsOF3WJM88GVaijqus5p1j47aNFJtPOGYfwaSEjiHUbigyohkcsTAg65Bl2469QJNTScnuwk1jAWtXJj";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
        relicTrackables.activate();
        while (robot.mrGyro.isCalibrating()) {
        }
        robot.sensorGyro.resetZAxisIntegrator();

        while (opModeIsActive()) {



            com.vuforia.CameraDevice.getInstance().setFlashTorchMode(true);
                while (VuforiaActive == 1) {
                    robot.Phone.setPosition(1);

                    RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                    if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                        telemetry.addData("VuMark", "%s visible", vuMark);
                        telemetry.update();
                        RelicRecoveryVuMark DecodedMessage = RelicRecoveryVuMark.from(relicTemplate);

                        VuforiaActive = 0;
                        if (DecodedMessage.equals(RelicRecoveryVuMark.CENTER)) {
                            robot.VuCentre = true;
                        }
                        if (DecodedMessage.equals(RelicRecoveryVuMark.LEFT)) {
                            robot.VuLeft = true;
                        }
                        if (DecodedMessage.equals(RelicRecoveryVuMark.RIGHT)) {
                            robot.VuRight = true;
                        }
                    } else {
                        telemetry.addData("VuMark", "not visible");
                        Thread.sleep(250);
                        robot.FailedAttempts = robot.FailedAttempts + 1;
                        if (robot.FailedAttempts == 8) {
                            com.vuforia.CameraDevice.getInstance().setFlashTorchMode(false);
                            if (robot.FailedAttempts == 16){
                                VuforiaActive = 0;
                                robot.PillarsToBePassed = 1;
                            }
                        }
                        telemetry.update();
                    }
                }
            robot.VuforiaLogic("Blue");
            robot.GlyphPickUp(250);
            robot.Jewel("Blue");
            robot.DriveWithRange(10,-0.2,"Blue");
            robot.GyroTurn(45,0.2);



            stop();
        }
    }
}




