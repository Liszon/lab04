package pollub.ism.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button Reset;

    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;

    int [] gameStatus = {2,2,2,2,2,2,2,2,2};
    //p1 zamienia na 0
    //p2 zamienia na 1

    int[][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        Reset = (Button) findViewById(R.id.Reset);

        for(int i=0; i<buttons.length;i++)
        {
            String buttonID = "btn_"+i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals(""))
        {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatusPointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activePlayer)
        {
            ((Button)v).setText("X");
            gameStatus[gameStatusPointer] = 0;
        }
        else
        {
            ((Button)v).setText("O");
            gameStatus[gameStatusPointer] = 1;
        }
        roundCount++;

        if(checkWinner())
        {
            if(activePlayer)
            {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won", Toast.LENGTH_SHORT).show();
                ResetGame();
            }
            else
            {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won", Toast.LENGTH_SHORT).show();
                ResetGame();
            }
        }
        else if(roundCount == 9)
        {
            ResetGame();
            Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
        }
        else
        {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount>playerTwoScoreCount)
        {
            playerStatus.setText("Player One is Winning");
        }
        else if(playerTwoScoreCount>playerOneScoreCount)
        {
            playerStatus.setText("Player Two is Winning");
        }
        else
        {
            playerStatus.setText("Draw");
        }

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetGame();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("Draw");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner() {
        boolean winnerResult = false;
        for(int [] winningPosition : winningPositions)
        {
            if(gameStatus[winningPosition[0]] == gameStatus[winningPosition[1]] && gameStatus[winningPosition[1]] == gameStatus[winningPosition[2]] && gameStatus[winningPosition[0]] !=2)
            {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void ResetGame(){
        roundCount = 0;
        activePlayer = true;
        for(int i=0; i<buttons.length;i++)
        {
            gameStatus[i] = 2;
            buttons[i].setText("");
        }
    }
}