package org.firstinspires.ftc.teamcode.Main.Worlds;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Edric on 2018/03/31.
 */

@Autonomous(name = "Red1", group = "Red")
public class Red1 extends LinearOpMode {
    AutoOpHardware robot = new AutoOpHardware();
    public VuforiaLocalizer vuforia;
    double VuforiaActive = 1;
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
                    robot.Phone.setPosition(0.9);
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
                        if (robot.FailedAttempts > 7) {
                            com.vuforia.CameraDevice.getInstance().setFlashTorchMode(false);
                            if (robot.FailedAttempts == 16){
                                VuforiaActive = 0;
                                robot.PillarsToBePassed = 1;
                            }
                        }
                        telemetry.update();
                    }
                }
            com.vuforia.CameraDevice.getInstance().setFlashTorchMode(false);
            robot.VuforiaLogic("Red");
            telemetry.addData("Pillars to be passed:", robot.PillarsToBePassed);
            telemetry.update();
            robot.Phone.setPosition(0);
            robot.GlyphPickUp(1000);
            robot.Jewel("Red");
            telemetry.addData("Jewel Colour:",robot.Colour);
            telemetry.addData("Distance:",robot.ExtraDistance);
            telemetry.update();
            robot.DriveForwardGyro(1000,0.3);
            robot.DriveWithDeltaRange(0.2,"Red");
            robot.Jewel.setPosition(0);
            robot.DriveForwardGyro(robot.ExtraDropDistance,0.4);
            if (robot.VuRight = true){
                robot.Reading2 = robot.Reading2 + 8;
            }
            robot.GetDriveDistanceDeg(105);
            robot.GyroTurn(-105,0.4);
            telemetry.addData("Dist from Pillar:", robot.Reading2);
            telemetry.addData("DriveDist:", robot.DriveDistance/33.33333333);
            telemetry.update();
            robot.DriveForwardGyro(robot.DriveDistance,0.4);
            robot.DriveTrain(-0.1);
            Thread.sleep(100);
            robot.DriveTrain(0);
            robot.GlyphDrop(900);
            robot.DriveTrain(-0.4);
            Thread.sleep(400);
            robot.DriveTrain(0);
            stop();
        }
    }
}




