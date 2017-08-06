package com.example.lenovo.minesweeper;

import android.content.Context;
import android.widget.Button;

/**
 * Created by lenovo on 16-06-2017.
 */

public class MyImageButton extends Button {

    int number;
    boolean flag;
    boolean buttonpressed;
    public MyImageButton(Context context) {
        super(context);
        number=0;
        flag=false;
        buttonpressed=false;
    }
}
