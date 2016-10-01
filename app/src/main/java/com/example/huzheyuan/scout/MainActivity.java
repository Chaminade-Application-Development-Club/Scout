package com.example.huzheyuan.scout;


        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{
    Button btnPlus;
    Button btnMinus;
    Button btnClear;
    TextView scoreText;
    public int totalScores = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findElements();
        countScores();
        clearData();

    }

    public void findElements()
    {
        btnPlus = (Button) findViewById(R.id.btnPlus1);
        btnMinus = (Button) findViewById(R.id.btnMinus1);
        btnClear = (Button) findViewById(R.id.btnClear1);
        scoreText = (TextView) findViewById(R.id.scoreText1);
    }

    public void countScores()
    {
        View.OnClickListener oclBtnPlus = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                totalScores++;
                String totalScoresString = Integer.toString(totalScores);
                scoreText.setText(totalScoresString);
            }
        };
        btnPlus.setOnClickListener(oclBtnPlus);

        View.OnClickListener oclBtnMinus = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                totalScores--;
                if(totalScores < 0)
                {
                    totalScores = 0;
                }

                String totalScoresString = Integer.toString(totalScores);
                scoreText.setText(totalScoresString);
            }

        };
        btnMinus.setOnClickListener(oclBtnMinus);
    }

    public void clearData()
    {
        View.OnClickListener oclBtnClear = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                totalScores = 0;
                String totalScoresString = Integer.toString(totalScores);
                scoreText.setText(totalScoresString);
            }
        };
        btnClear.setOnClickListener(oclBtnClear);
    }
}
