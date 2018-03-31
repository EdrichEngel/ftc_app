package org.firstinspires.ftc.teamcode.Other.Edrich;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Edric on 2018/02/10.
 */
@Autonomous
//@Disabled
public class PhoneServo extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo Phone;


        Phone = hardwareMap.servo.get("Phone");

        waitForStart();
        while (opModeIsActive()){
            Phone.setPosition(0);
            Thread.sleep(1000);
            Phone.setPosition(0.1);
            Thread.sleep(1000);
            Phone.setPosition(0.2);
            Thread.sleep(1000);
            Phone.setPosition(0.3);
            Thread.sleep(1000);
            Phone.setPosition(0.4);
            Thread.sleep(1000);
            Phone.setPosition(0.5);
            Thread.sleep(1000);
            Phone.setPosition(0.6);
            Thread.sleep(1000);
            Phone.setPosition(0.7);
            Thread.sleep(1000);
            Phone.setPosition(0.8);
            Thread.sleep(1000);
            Phone.setPosition(0.9);
            Thread.sleep(1000);
            Phone.setPosition(1);
            Thread.sleep(1000);
            stop();

        }
    }
}
