package com.example.lenovo.minesweeper;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    public static final int MINEPRESENT = -1;
    public static MyImageButton buttons[][];
    public static LinearLayout rowLayout[];
    public static LinearLayout mainLayout;
    public static int boardSize = 6;
    public static boolean gamOver=false;
    public static HashMap<Integer,String> map =new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout=(LinearLayout)findViewById(R.id.main_linearlayout);
        setMinesweeperBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id=item.getItemId();
        if(id==R.id.newGame){
            resetGame();
        }else if(id==R.id.level){

        }
        return true;
    }

    private void resetGame() {
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                buttons[i][j].buttonpressed=false;
                buttons[i][j].flag=false;
                buttons[i][j].setText("");
                buttons[i][j].number=0;

            }
        }
        setGame();

        gamOver=false;

    }

    private void setMinesweeperBoard() {
        mainLayout.removeAllViews();
        rowLayout = new LinearLayout[boardSize];
        for (int i = 0; i < boardSize; i++) {
            rowLayout[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
            params.setMargins(5, 5, 5, 5);
            rowLayout[i].setLayoutParams(params);
            rowLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rowLayout[i]);
        //    Log.i("row layout ",i+"th rowlayout");    // log statement
        }

        buttons = new MyImageButton[boardSize][boardSize];
        int key=0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new MyImageButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                params.setMargins(5, 5, 5, 5);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setTextSize(40);
                buttons[i][j].setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setOnLongClickListener(this);
                String str=""+i+'a'+j;
                map.put(key,str);
                buttons[i][j].setId(key);
                key++;
                rowLayout[i].addView(buttons[i][j]);
             //   Log.i("buttons ","i value: "+i+" j value: "+j);  //log statement
            }
        }

        setGame();
    }

    private void setGame() {
        locateMines();
        setNuberAtButton();

//        for (int i = 0; i < boardSize; i++) {
//            for (int j = 0; j < boardSize; j++) {
//                if (buttons[i][j].number == 0) {
//                    buttons[i][j].setText(buttons[i][j].number + "");
//                }
////               Log.i("TAG", "setGame: "+buttons[i][j].buttonpressed);
////
////            }
//            }
//        }
    }
    private void setNuberAtButton() {
        // setting number at four corners
        //At corner1
        if (buttons[0][0].number != MINEPRESENT) {
            if (buttons[0][1].number == MINEPRESENT) {
                buttons[0][0].number += 1;
            }
            if (buttons[1][1].number == MINEPRESENT) {
                buttons[0][0].number += 1;
            }
            if (buttons[1][0].number == MINEPRESENT) {
                buttons[0][0].number += 1;
            }

        }

        //At corner2
        if (buttons[boardSize - 1][0].number != MINEPRESENT) {
            if (buttons[boardSize - 1][1].number == MINEPRESENT) {
                buttons[boardSize - 1][0].number += 1;
            }
            if (buttons[boardSize - 2][0].number == MINEPRESENT) {
                buttons[boardSize - 1][0].number += 1;
            }
            if (buttons[boardSize - 2][1].number == MINEPRESENT) {
                buttons[boardSize - 1][0].number += 1;
            }

        }

        //At corner3
        if (buttons[0][boardSize - 1].number != MINEPRESENT) {
            if (buttons[0][boardSize - 2].number == MINEPRESENT) {
                buttons[0][boardSize - 1].number += 1;
            }
            if (buttons[1][boardSize - 2].number == MINEPRESENT) {
                buttons[0][boardSize - 1].number += 1;
            }
            if (buttons[1][boardSize - 1].number == MINEPRESENT) {
                buttons[0][boardSize - 1].number += 1;
            }

        }

        //At corner4
        if (buttons[boardSize - 1][boardSize - 1].number != MINEPRESENT) {
            if (buttons[boardSize - 2][boardSize - 2].number == MINEPRESENT) {
                buttons[boardSize - 1][boardSize - 1].number += 1;
            }
            if (buttons[boardSize - 1][boardSize - 2].number == MINEPRESENT) {
                buttons[boardSize - 1][boardSize - 1].number += 1;
            }
            if (buttons[boardSize - 2][boardSize - 1].number == MINEPRESENT) {
                buttons[boardSize - 1][boardSize - 1].number += 1;
            }

        }

        //at first and Last Row & first and Last column
        for (int i = 1; i < boardSize - 1; i++) {
            //first row
            if (buttons[0][i].number != MINEPRESENT) {
                if (buttons[0][i - 1].number == MINEPRESENT) {
                    buttons[0][i].number += 1;
                }
                if (buttons[0][i + 1].number == MINEPRESENT) {
                    buttons[0][i].number += 1;
                }
                if (buttons[1][i].number == MINEPRESENT) {
                    buttons[0][i].number += 1;
                }
                if (buttons[1][i - 1].number == MINEPRESENT) {
                    buttons[0][i].number += 1;
                }
                if (buttons[1][i + 1].number == MINEPRESENT) {
                    buttons[0][i].number += 1;
                }
            }
             // last row
            if (buttons[boardSize - 1][i].number != MINEPRESENT) {
                if (buttons[boardSize - 1][i - 1].number == MINEPRESENT) {
                    buttons[boardSize - 1][i].number += 1;
                }
                if (buttons[boardSize - 1][i + 1].number == MINEPRESENT) {
                    buttons[boardSize - 1][i].number += 1;
                }
                if (buttons[boardSize - 2][i].number == MINEPRESENT) {
                    buttons[boardSize - 1][i].number += 1;
                }
                if (buttons[boardSize - 2][i - 1].number == MINEPRESENT) {
                    buttons[boardSize - 1][i].number += 1;
                }
                if (buttons[boardSize - 2][i + 1].number == MINEPRESENT) {
                    buttons[boardSize - 1][i].number += 1;
                }
            }
            // first column
                if (buttons[i][0].number != MINEPRESENT) {
                    if (buttons[i-1][0].number == MINEPRESENT) {
                        buttons[i][0].number += 1;
                    }
                    if (buttons[i+1][0].number == MINEPRESENT) {
                        buttons[i][0].number += 1;
                    }
                    if (buttons[i][1].number == MINEPRESENT) {
                        buttons[i][0].number += 1;
                    }
                    if (buttons[i-1][1].number == MINEPRESENT) {
                        buttons[i][0].number += 1;
                    }
                    if (buttons[i+1][1].number == MINEPRESENT) {
                        buttons[i][0].number += 1;
                    }
                }
                 //last column
                if (buttons[i][boardSize - 1].number != MINEPRESENT) {
                    if (buttons[i - 1][boardSize - 1].number == MINEPRESENT) {
                        buttons[i][boardSize - 1].number += 1;
                    }
                    if (buttons[i + 1][boardSize - 1].number == MINEPRESENT) {
                        buttons[i][boardSize - 1].number += 1;
                    }
                    if (buttons[i][boardSize - 2].number == MINEPRESENT) {
                        buttons[i][boardSize - 1].number += 1;
                    }
                    if (buttons[i - 1][boardSize - 2].number == MINEPRESENT) {
                        buttons[i][boardSize - 1].number += 1;
                    }
                    if (buttons[i + 1][boardSize - 2].number == MINEPRESENT) {
                        buttons[i][boardSize - 1].number += 1;
                    }
                }
            }

            //at middle of board
             for(int i=1;i<boardSize-1;i++){
                 for(int j=1;j<boardSize-1;j++){
                     if(buttons[i][j].number!=MINEPRESENT){
                         if(buttons[i-1][j-1].number==MINEPRESENT){
                             buttons[i][j].number+=1;
                         }
                         if(buttons[i-1][j].number==MINEPRESENT){
                             buttons[i][j].number+=1;
                         }
                         if(buttons[i-1][j+1].number==MINEPRESENT){
                             buttons[i][j].number+=1;
                         }
                         if(buttons[i][j-1].number==MINEPRESENT){
                             buttons[i][j].number+=1;
                         }
                         if(buttons[i+1][j-1].number==MINEPRESENT){
                             buttons[i][j].number+=1;
                         }
                         if(buttons[i+1][j].number==MINEPRESENT){
                             buttons[i][j].number+=1;
                         }
                         if(buttons[i+1][j+1].number==MINEPRESENT){
                             buttons[i][j].number+=1;
                         }
                         if(buttons[i][j+1].number==MINEPRESENT){
                             buttons[i][j].number+=1;
                         }
                     }
                 }
             }
        }

    private void locateMines() {
        int noOfMines=0;
        int xArray[]=new int[boardSize];
        int yArray[]=new int[boardSize];
        Random rd=new Random();
        while(noOfMines<4){
            int x= rd.nextInt(boardSize);
            int y= rd.nextInt(boardSize);     //error may occur ArrayIndexOutOfBound
            if(xArray[x]!=1){
                yArray[y]=1;
                xArray[x]=1;
               // buttons[x][y].minepresent=true;
                buttons[x][y].number=MINEPRESENT;
                noOfMines++;
                Log.i("Locate mines x!=1 ","x value: "+x+"y value: "+y);  //log statement
            }else if(yArray[y]!=1){
                yArray[y]=1;
                xArray[x]=1;
                noOfMines++;
              //  buttons[x][y].minepresent=true;
                Log.i("Locate mines y!=1 ","x value: "+x+"y value: "+y);  //log statement
                buttons[x][y].number=MINEPRESENT;
            }
            Log.i("Locate mines ","noOfMines: "+noOfMines);
        }
        
    }

    @Override
    public void onClick(View v) {
        if(gamOver){
            Toast.makeText(this,"GAME OVER PLEASE SET NEWGAME",Toast.LENGTH_SHORT).show();
            return;
        }
        MyImageButton buttonclicked=(MyImageButton)v;
        if(buttonclicked.buttonpressed){
            Toast.makeText(this,"button is already pressed ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(buttonclicked.flag){
            Toast.makeText(this,"button is flaged remove flag first",Toast.LENGTH_SHORT).show();
            return;
        }
        if(buttonclicked.number!=0&&buttonclicked.number!=MINEPRESENT){
            buttonclicked.buttonpressed=true;
            buttonclicked.setText(buttonclicked.number+""); //error may occur
        }
        if(buttonclicked.number==MINEPRESENT){
            gamOver=true;
            Toast.makeText(this,"You Loose Try Again",Toast.LENGTH_LONG).show();
            displayAllMines();
            return;
        }
        if(buttonclicked.number==0){
            oPressed(buttonclicked);
        }

        if(!gamOver){
            gamOver=getGameStatus();
        }
        if(gamOver){
            Toast.makeText(this,"you win ",Toast.LENGTH_SHORT).show();
        }

    }

    private void displayAllMines() {
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                if(buttons[i][j].number==MINEPRESENT){
                    String a="M";
                   buttons[i][j].setText(a); //error might occur
                }
            }
        }
    }

    private boolean getGameStatus() {
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                if(buttons[i][j].buttonpressed==false){
                    if(buttons[i][j].flag==false){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void oPressed(MyImageButton buttonclicked) {
        int id = buttonclicked.getId();
        String value = map.get(id);
        int i = 0, j = 0;
        int k = 0;
        String temp = "";
        while (k < value.length() && value.charAt(k) != 'a') {
            temp += value.charAt(k++);
        }
        i = Integer.parseInt(temp);
        k++;
        temp = "";
        while (k < value.length()) {
            temp += value.charAt(k++);
        }
        j = Integer.parseInt(temp);
        Log.i("oPressed ", "i value:" + i + "j value:" + j);   //Log statement
        openButtonContent(i, j);
    }
    private void openButtonContent(int i,int j){
        if((i<0)||(i>(boardSize-1))||(j<0)||(j>(boardSize-1))){
            return;
        }
        if(buttons[i][j].buttonpressed||buttons[i][j].number==MINEPRESENT){
            return;
        }
        if(buttons[i][j].number!=0){
          //  Log.i("openButton:Not Zero ","i= "+i+"j= "+j);
            buttons[i][j].buttonpressed=true;
            buttons[i][j].setText(buttons[i][j].number+"");
            return;
        }
        buttons[i][j].buttonpressed=true;
       // Log.i("openButton:Zero ","i= "+i+"j= "+j);
        buttons[i][j].setText(buttons[i][j].number+"");
        openButtonContent(i+1,j-1);
        openButtonContent(i+1,j+1);
        openButtonContent(i+1,j);
        openButtonContent(i-1,j+1);
        openButtonContent(i-1,j);
        openButtonContent(i-1,j-1);
        openButtonContent(i,j-1);
        openButtonContent(i,j+1);

    }


    @Override
    public boolean onLongClick(View v) {
        if(gamOver){
            Toast.makeText(this,"Gameover Try Newgame",Toast.LENGTH_SHORT);
            return true;
        }
        MyImageButton buttonclicked=(MyImageButton)v;
        if(buttonclicked.buttonpressed){
            return true;
        }
        buttonclicked.flag=!buttonclicked.flag;
        if(buttonclicked.flag){
            buttonclicked.setText("F"); //error might occur
        }else{
            buttonclicked.setText("");  //error might occur
        }
        return true;
    }
}
