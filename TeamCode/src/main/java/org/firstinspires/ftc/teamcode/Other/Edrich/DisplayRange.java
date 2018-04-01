package org.firstinspires.ftc.teamcode.Other.Edrich;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Main.Worlds.AutoOpHardware;
import org.firstinspires.ftc.teamcode.Main.Worlds.TeleOpHardware;

/**
 * Created by Edric on 2018/04/01.
 */
@Autonomous(name = "DisplayRange", group = "Testing")
public class DisplayRange extends LinearOpMode {
    AutoOpHardware robot = new AutoOpHardware();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        robot.Jewel.setPosition(0.1);

        waitForStart();
        while (opModeIsActive()) {
            robot.color_C_reader = new I2cDeviceSynchImpl(robot.colorC, I2cAddr.create8bit(0x3c),false);
            robot.color_C_reader.engage();
            robot.colorCcache = robot.color_C_reader.read(0x04,1);
            telemetry.addData("raw ultrasonic", robot.rangeSensor.rawUltrasonic());
            telemetry.addData("Gyro" , robot.sensorGyro.rawZ());
            telemetry.addData("Colour" , robot.colorCcache[0]);
            telemetry.update();

        }

    }
}
