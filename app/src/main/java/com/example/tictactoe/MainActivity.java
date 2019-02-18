package com.example.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            game = (Game) savedInstanceState.getSerializable("game");
            for(int i=1; i<4; i++) {
                for(int j=1; j<4; j++){
                    TileState tilestate = game.board[i-1][j-1];
                    String name = "b"+i+j;
                    int id = getResources().getIdentifier(name, "id", getPackageName());
                    Button btn = (Button) findViewById(id);
                    switch(tilestate){
                        case CIRCLE:
                            btn.setText("O");
                            break;
                        case CROSS:
                            btn.setText("X");
                            break;
                    }
                }
            }

        }
        else {
            game = new Game();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
    }

    public int getID(View view) {
        String id1 =  view.getResources().getResourceName(view.getId());
        String id2 = id1.replace("b","");
        String id3 = id2.substring(Math.max(id2.length() - 2, 0));
        return Integer.parseInt(id3);
    }

    public void tileClicked(View view) {

        if (!game.gameOver) {
            int id = getID(view);
            int row = id / 10 % 10;
            int column = id % 10;
            TileState state = game.choose(row, column);
            GameState gamestate = game.won(row, column, state);
            Button btn = (Button) findViewById(view.getId());

            TextView winnertext = (TextView) findViewById(R.id.player);
            TextView textmoves = (TextView) findViewById(R.id.textmoves);
            TextView nmoves = (TextView) findViewById(R.id.nmoves);
            nmoves.setText("" + game.moves);
            TextView finalmoves = (TextView) findViewById(R.id.finalmoves);

            if (game.moves > 5) {
                finalmoves.setText("Almost there...");
            }

            switch (state) {
                case CROSS:
                    btn.setText("X");
                    break;
                case CIRCLE:
                    btn.setText("O");
                    break;
            }

            if (game.moves > 8 && !game.gameOver) {
                textmoves.setText("");
                nmoves.setText("");
                finalmoves.setText("");
                winnertext.setText("Draw");
            }
            if (game.gameOver) {
                textmoves.setText("");
                nmoves.setText("");
                finalmoves.setText("");
                wingamestate(gamestate, winnertext);
            }
        }
    }

    public void wingamestate(GameState gamestate, TextView winnertext){
        TextView nmoves = (TextView) findViewById(R.id.nmoves);
        nmoves.setText("0");
        switch (gamestate){
            case PLAYER_ONE:
                winnertext.setText("Player two has won!!");
                game.gameOver = true;
                break;
            case PLAYER_TWO:
                winnertext.setText("Player one has won!!");
                game.gameOver = true;
                break;
        }
    }

    public void resetClicked(View view) {
        TextView winnertext = (TextView) findViewById(R.id.player);
        winnertext.setText("");
        game = new Game();
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                String name = "b"+i+j;
                int id = getResources().getIdentifier(name, "id", getPackageName());
                Button btn = (Button) findViewById(id);
                btn.setText("");
            }
        }
    }
}
