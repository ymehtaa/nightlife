package com.example.partysmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
// currently working on fixing the removal of a theme from the array list once a button is un-clicked
public class MainActivity extends AppCompatActivity {

    private EditText budget, size;
    private Button nextBtn;
    //private int maxButton; use this to add limit on theme buttons clicked at a time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        budget = findViewById(R.id.budgetBox);
        size = findViewById(R.id.sizeBox);
        nextBtn = findViewById(R.id.next_button);
        ArrayList<String> userSelections = new ArrayList<String>();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userBudget = budget.getText().toString();
                String userSize = size.getText().toString();

                if (TextUtils.isEmpty(userBudget) || TextUtils.isEmpty(userSize)) {
                    Toast.makeText(MainActivity.this, "Please input a value for budget and/or party size", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, resultsActivity.class);
                    intent.putExtra("keyBudget", userBudget);
                    intent.putExtra("keySize", userSize);
                    intent.putExtra("keySelections", userSelections);

                    startActivity(intent);
                }
            }
        });

        final Button foodBtn = findViewById(R.id.foodButton);
        foodBtn.setOnClickListener(new View.OnClickListener() {
            int check = 1;
            @Override
            public void onClick(View view1) {
                check++;
                if (check % 2 == 0) {
                    foodBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    userSelections.add("food");
                } else {
                    foodBtn.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    /*for (String j : userSelections) {
                        if ("food".matches(j)) {
                            userSelections.remove(j);
                        }
                    }*/
                }
            }
        });
        final Button activityBtn = findViewById(R.id.activityButton);
        activityBtn.setOnClickListener(new View.OnClickListener() {
            int check1 = 1;
            @Override
            public void onClick(View view2) {
                check1++;
                if (check1 % 2 == 0) {
                    activityBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    userSelections.add("activity");
                } else {
                    activityBtn.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    /*for (String m : userSelections) {
                        if ("activity".matches(m)) {
                            userSelections.remove(m);
                        }
                    }*/
                }
            }
        });
        final Button thirdBtn = findViewById(R.id.button3);
        thirdBtn.setOnClickListener(new View.OnClickListener() {
            int check2 = 1;
            @Override
            public void onClick(View view3) {
                check2++;
                if (check2 % 2 == 0) {
                    thirdBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    userSelections.add("entertainment");
                } else {
                    thirdBtn.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    /*for (String w : userSelections) {
                        if ("third".matches(w)) {
                            userSelections.remove(w);
                        }
                    }*/
                }
            }
        });
        final Button fourthBtn = findViewById(R.id.button4);
        fourthBtn.setOnClickListener(new View.OnClickListener() {
            int check3 = 1;
            @Override
            public void onClick(View view4) {
                check3++;
                if (check3 % 2 == 0) {
                    fourthBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    userSelections.add("movie");
                } else {
                    fourthBtn.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    /*for (String x : userSelections) {
                        if ("fourth".matches(x)) {
                            userSelections.remove(x);
                        }
                    }*/
                }
            }
        });
        final Button fifthBtn = findViewById(R.id.button5);
        fifthBtn.setOnClickListener(new View.OnClickListener() {
            int check4 = 1;
            @Override
            public void onClick(View view5) {
                check4++;
                if (check4 % 2 == 0) {
                    fifthBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    userSelections.add("relaxed");
                } else {
                    fifthBtn.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    /*for (String z : userSelections) {
                        if ("fifth".matches(z)) {
                            userSelections.remove(z);
                        }
                    }*/
                }
            }
        });
        final Button sixthBtn = findViewById(R.id.button6);
        sixthBtn.setOnClickListener(new View.OnClickListener() {
            int check5 = 1;
            @Override
            public void onClick(View view6) {
                check5++;
                if (check5 % 2 == 0) {
                    sixthBtn.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    userSelections.add("fancy");
                } else {
                    sixthBtn.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    /*for (String i : userSelections) {
                        if ("sixth".matches(i)) {
                            userSelections.remove(i);
                        }
;                    }*/
                }
            }
        });
    }
}