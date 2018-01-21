package org.firstinspires.ftc.teamcode.Other.Timon;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Timon PC on 09/01/2018.
 */

@Disabled
public class Selector_Code extends OpMode{

    boolean pressed = false;
    boolean left_selected = true;
    boolean right_selected = false;

    void Select(){

        //Select left setting
        if (!pressed&&gamepad1.dpad_left) {
            if (!left_selected) {left_selected = true;}
            pressed = true;
        }else if(!gamepad1.dpad_left&&!gamepad1.dpad_right){pressed = false;}
        //Select right setting
        if (!pressed&&gamepad1.dpad_right) {
            if (!right_selected) {right_selected = true;}
            pressed = true;
        }else if(!gamepad1.dpad_left&&!gamepad1.dpad_right){pressed = false;}

    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}

//use if(){} with above mentioned left/right var to run specified code in loop
