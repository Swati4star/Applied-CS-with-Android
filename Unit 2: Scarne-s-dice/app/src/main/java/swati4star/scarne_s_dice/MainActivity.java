package swati4star.scarne_s_dice;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /**
     * Four global variables to store:
     the user's overall score state
     the user's turn score
     the computer's overall score
     the computer's turn score
     */
    int User = 0, UserTurn = 0, Comp = 0, CompTurn = 0;
    // To generate random numbers
    Random random = new Random();
    int dice[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    TextView score;
    ImageView diceImage;
    Button rollBtn, holdBtn, resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
        score = (TextView) findViewById(R.id.score);
        diceImage = (ImageView) findViewById(R.id.dice);
        rollBtn = (Button) findViewById(R.id.roll);
        holdBtn = (Button) findViewById(R.id.hold);
        resetBtn = (Button) findViewById(R.id.reset);
    }

    // When user choose to roll the dice
    public void roll(View view) {
        int rollVal = random.nextInt(6);
        diceImage.setImageResource(dice[rollVal]);
        rollVal++;
        if (rollVal == 1) {
            score.setText("Your score:" + (User) + " Computer score:" + (Comp));
            UserTurn = 0;
            computerChance();
        } else {
            UserTurn += rollVal;
            score.setText("Your score:" + (User + UserTurn) + " Computer score:" + (Comp));

            if ((User + UserTurn) >= 100)
                stopGame(0);
        }
    }

    // Plays computer's turn
    private void computerChance() {

        rollBtn.setEnabled(false);
        holdBtn.setEnabled(false);
        resetBtn.setEnabled(false);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                int rollVal = random.nextInt(6);
                diceImage.setImageResource(dice[rollVal]);
                rollVal++;
                if (rollVal == 1) {
                    score.setText("Your score:" + (User) + " Computer score:" + (Comp));
                    CompTurn = 0;
                    userChance();
                    return;
                } else {
                    CompTurn += rollVal;
                    if ((Comp + CompTurn) >= 100) {
                        stopGame(1);
                        return;
                    }

                    score.setText("Your score:" + (User) + " Computer score:" + (Comp + CompTurn));
                    if (CompTurn > 20) {
                        Comp += CompTurn;
                        CompTurn = 0;
                        userChance();
                        return;
                    } else
                        computerChance();
                }
            }
        }.start();
    }

    // When score is >=100
    private void stopGame(int winner) {
        if (winner == 0)
            score.setText("YOU WON!!!!!");
        else
            score.setText("COMPUTER WON!!!!");
        rollBtn.setEnabled(false);
        holdBtn.setEnabled(false);
    }

    // Enable all the buttons
    private void userChance() {
        rollBtn.setEnabled(true);
        holdBtn.setEnabled(true);
        resetBtn.setEnabled(true);
    }

    // Resets game
    public void reset(View view) {
        UserTurn = 0;
        User = 0;
        CompTurn = 0;
        Comp = 0;
        score.setText("Your score:0 Computer score:0");
        rollBtn.setEnabled(true);
        holdBtn.setEnabled(false);
    }

    // If user clicks hold, user's score is updated and computer plays
    public void hold(View view) {
        User += UserTurn;
        UserTurn = 0;
        computerChance();
    }
}