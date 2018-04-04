package org.firstinspires.ftc.teamcode.Main.Worlds;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Edrich on 2018/04/03.
 */

@Autonomous(name = "Red Jewel", group = "Backup")
public class RedJewel extends LinearOpMode {
    AutoOpHardware robot = new AutoOpHardware();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Robot Should Be Placed with Relic Claw Closest to the safe zone","");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()){
            robot.Jewel("Red");
            robot.DriveForwardGyro(3000, 0.3);
            stop();
        }
    }
}