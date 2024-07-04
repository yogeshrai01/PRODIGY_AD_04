
package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore;
    private TextView playerTwoScore;
    private TextView playerStatus;
    boolean playerOneActive;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    int round;
    private Button[] buttons = new Button[9];
    private Button reset, playagain;

    private int playerOneCount, playerTwoCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playerOneScore = findViewById(R.id.score_player1);
        playerTwoScore = findViewById(R.id.score_player2);
        playerStatus = findViewById(R.id.textStatus);
        reset = findViewById(R.id.btn_reset);
        playagain = findViewById(R.id.btn_playagain);

        buttons[0] = findViewById(R.id.btn1);
        buttons[1] = findViewById(R.id.btn2);
        buttons[2] = findViewById(R.id.btn3);
        buttons[3] = findViewById(R.id.btn4);
        buttons[4] = findViewById(R.id.btn5);
        buttons[5] = findViewById(R.id.btn6);
        buttons[6] = findViewById(R.id.btn7);
        buttons[7] = findViewById(R.id.btn8);
        buttons[8] = findViewById(R.id.btn9);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }

        playerOneCount = 0;
        playerTwoCount = 0;
        playerOneActive = true;
        round = 0;

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerOneCount = 0;
                playerTwoCount = 0;
                playAgain();
                updatePlayerScore();
            }
        });

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePoint = Integer.parseInt(buttonID.substring(buttonID.length() - 1)) - 1;

        if (gameState[gameStatePoint] != 2) {
            return;
        }

        if (playerOneActive) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#ffc34a"));
            gameState[gameStatePoint] = 0;
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#70fc3a"));
            gameState[gameStatePoint] = 1;
        }
        round++;

        if (checkWinner()) {
            if (playerOneActive) {
                playerOneCount++;
                updatePlayerScore();
                playerStatus.setText("Player-1 has Won");
            } else {
                playerTwoCount++;
                updatePlayerScore();
                playerStatus.setText("Player-2 has Won");
            }
        } else if (round == 9) {
            playerStatus.setText("No winner");
        } else {
            playerOneActive = !playerOneActive;
        }
    }

    private void playAgain() {
        round = 0;
        playerOneActive = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
        playerStatus.setText("Status");
    }

    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneCount));
        playerTwoScore.setText(Integer.toString(playerTwoCount));
    }

    private boolean checkWinner() {
        for (int[] winPosition : winPos) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                return true;
            }
        }
        return false;
    }
}
