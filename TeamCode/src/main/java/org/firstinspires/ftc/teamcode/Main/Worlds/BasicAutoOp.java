package org.firstinspires.ftc.teamcode.Main.Worlds;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Edrich on 2018/04/03.
 */
@Autonomous(name = "Basic", group = "Backup")
public class BasicAutoOp extends LinearOpMode {
    AutoOpHardware robot = new AutoOpHardware();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Robot Should Be Placed with Glyph Collector Closest to the safe zone","");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()){
            robot.DriveFrontRight.setPower(0.4);
            robot.DriveFrontLeft.setPower(0.3);
            robot.DriveBackLeft.setPower(0.3);
            robot.DriveBackRight.setPower(0.4);
            Thread.sleep(4000);
            robot.DriveTrain(0);
            stop();
        }
    }
}
