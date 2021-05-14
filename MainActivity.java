package com.example.tictoetoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundcount;

    private int player1points;
    private int player2points;

    private TextView textViewplayer1;
    private TextView textViewplayer2;
    private int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        textViewplayer1 = findViewById (R.id.text_view_p1);
        textViewplayer2 = findViewById (R.id.text_view_p2);


        for (int i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources ( ).getIdentifier (buttonID, "id", getPackageName ( ));
                buttons[i][j] = findViewById (resID);
                buttons[i][j].setOnClickListener (this);


            }
        }

        Button buttonReset = findViewById (R.id.button_reset);
        buttonReset.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText ( ).toString ( ).equals ("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText ("x");
        } else {
            ((Button) v).setText ("o");
        }
        roundcount++;

        if (checkForwin ( )) {
            if (player1Turn) {
                player1wins ( );
            } else {
                player2wins ( );
            }
        } else if (roundcount == 9) {
            draw ( );
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForwin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText ( ).toString ( );
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals (field[i][1])
                    && field[i][0].equals (field[i][2])
                    && !field[i][0].equals ("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals (field[1][i])
                    && field[0][i].equals (field[2][i])
                    && !field[0][i].equals ("")) {
                return true;
            }
        }
        if (field[0][0].equals (field[1][1])
                && field[0][0].equals (field[2][2])
                && !field[0][0].equals ("")) {
            return true;

        }
        if (field[0][2].equals (field[1][1])
                && field[0][2].equals (field[2][0])
                && !field[0][2].equals ("")) {
            return true;

        }
        return false;
    }

    private void player1wins() {
        player1points++;
        Toast.makeText (this, "player 1 wins !", Toast.LENGTH_SHORT).show ();
        updatepointsText();
        resetBoard();
    }


    private void player2wins() {
        player2points++;
        Toast.makeText (this, "player 2 wins !", Toast.LENGTH_SHORT).show ();
        updatepointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText (this, "Draw!", Toast.LENGTH_SHORT).show ();
        resetBoard();
    }

    private void updatepointsText() {
        textViewplayer1.setText ("player 1: " + player1points);
        textViewplayer2.setText ("player 2: " + player2points);
    }

    private void resetBoard() {
        for (int i=0; i<3; i++){
            for (j=0; j<3; j++){
                buttons[i][j].setText ("");
            }
        }
        roundcount = 0;
        player1Turn = true;
    }
    private void resetGame() {
        player1points = 0;
        player2points = 0;
        updatepointsText ();
        resetBoard ();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState (outState, outPersistentState);

        outState.putInt ("roundcount", roundcount);
        outState.putInt ("player1points", player1points);
        outState.putInt ("player2points", player2points);
        outState.putBoolean ("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState (savedInstanceState);

        roundcount = savedInstanceState.getInt ("roundCount");
        player1points = savedInstanceState.getInt ("player1points");
        player2points = savedInstanceState.getInt ("player2points");
        player1Turn = savedInstanceState.getBoolean ("player1Turn");
    }
}