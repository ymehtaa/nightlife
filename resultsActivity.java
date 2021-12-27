package com.example.partysmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class resultsActivity extends AppCompatActivity {
    private TextView testFoodBox, testActivityBox, randomSelection;
    public String soloBudget, userAtm, userInputTheme1, userInputTheme2;
    private double soloFoodPrice, soloActivityPrice;
    private DatabaseReference mEstablishmentDB;
    User z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // reference my database
        mEstablishmentDB = FirebaseDatabase.getInstance().getReference("establishments");

        testFoodBox = findViewById(R.id.testFood);
        testActivityBox = findViewById(R.id.testActivity);
        randomSelection = findViewById(R.id.randomOptions);

        //userZ = findViewById(R.id.test_objects);

        String userBudget = getIntent().getStringExtra("keyBudget");
        String userSize = getIntent().getStringExtra("keySize");
        ArrayList<String> userSelections = (ArrayList<String>) getIntent().getSerializableExtra("keySelections");
        // String userChoices2 = printSelections(userSelections);

        ArrayList<Establishment> foodEstablishments = new ArrayList<Establishment>();
        ArrayList<Establishment> activityEstablishments = new ArrayList<Establishment>();

        z = new User();
        z.budget = Integer.parseInt(userBudget);
        z.partySize = Integer.parseInt(userSize);
        z.theme1 = userSelections.get(0); // most often food but could also be activity, entertainment, or movie
        z.theme2 = userSelections.get(1); // activity, entertainment, or movie
        z.atm = userSelections.get(2); // relaxed or fancy

        int partySize = z.partySize;
        userInputTheme1 = z.theme1;
        userInputTheme2 = z.theme2;
        userAtm = z.atm; //atmosphere

        //find price that an individual will pay for their food and activities, separately
        soloFoodPrice = (z.budget / 2) / z.partySize;
        //soloActivityPrice = (z.budget/2)/z.partySize;

        if (soloFoodPrice <= 15) {
            soloBudget = "$";
        } else if (soloFoodPrice > 15 && soloFoodPrice <= 50) {
            soloBudget = "$$";
        } else {
            soloBudget = "$$$";
        }

        //add listener for firebase db
        mEstablishmentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // random option
                int foodSize = foodEstablishments.size();
                Random r = new Random();
                int i = r.nextInt(foodSize - 0) + 0;
                Establishment foodEst = foodEstablishments.get(i);
                randomSelection.append("Start with dinner at: \n" + foodEst.name + " - " + foodEst.budget + "\n\n");
                int activitySize = activityEstablishments.size();
                Random j = new Random();
                int w = r.nextInt(activitySize - 0) + 0;
                Establishment activityEst = activityEstablishments.get(w);
                randomSelection.append("Followed by:  \n" + activityEst.name + " - " + activityEst.budget + "\n");


                // list of restaurants
                testFoodBox.append("Not interested in the option above? \n\nHere's a list of restaurants that fall within your preferences: \n");
                for (Establishment est : foodEstablishments) {
                    testFoodBox.append(est.name + " - " + est.budget + "\n");
                }

                // list of activities
                testActivityBox.append("And some things to do: \n");
                for (Establishment est1 : activityEstablishments) {
                    testActivityBox.append(est1.name + " - " + est1.budget + "\n");
                }
                // Log.d("nightlife", snapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //firebase query for establishment within budget, rest of work is done on client side through code
        mEstablishmentDB.orderByChild("budget").equalTo(soloBudget).addChildEventListener(new ChildEventListener() { // searching for establishment within budget
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("nightlife", snapshot.toString());


                Establishment est = snapshot.getValue(Establishment.class);
                est.name = snapshot.getKey();

                // replace nulls with fake strings to enable string comparisons
                if (est.atmosphere == null) {
                    est.atmosphere = "";
                }
                if (est.theme1 == null) {
                    est.theme1 = "";
                }
                if (est.theme2 == null) {
                    est.theme2 = "";
                }
                if (est.budget == null) {
                    est.budget = "";
                }
                if (userInputTheme1 == null) {
                    userInputTheme1 = " "; // extra space to avoid fake true comparison with other null variables
                }
                if (userInputTheme2 == null) {
                    userInputTheme2 = " "; // extra space to avoid fake true comparison with other null variables
                }

                // checking if establishment capacity is greater than user party size and if atmosphere fits
                if (est.size >= partySize && est.atmosphere.equals(userAtm)) {
                    // check if its a restaurant or an activity
                    if (est.theme1.equals("food") || est.theme2.equals("food")) {
                        foodEstablishments.add(est); // add restaurant to ArrayList of approved restaurants
                    } else if (est.theme1.equals(userInputTheme1) || est.theme1.equals(userInputTheme2) || est.theme2.equals(userInputTheme1) || est.theme2.equals(userInputTheme2)) {
                        // possible bug: if a theme is null and if a userInputTheme is null, they get turned into empty strings and will be
                        activityEstablishments.add(est); // add the place to the ArrayList of activity establishments
                    }
                }
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        /*for (Establishment est : foodEstablishments) {
            testBox.append(est.budget);
            size.append("!");
        }*/
    }

    // turns user selections ArrayList into a printable string
    /*public String printSelections(ArrayList<String> userSelections) {
        StringBuilder userSelectionsBuilder = new StringBuilder();
        for (String s : userSelections) {
            userSelectionsBuilder.append(s + ", ");
        }

        String userChoices2 = userSelectionsBuilder.toString();
        return userChoices2;
    }*/
}
// user class to help build other variables
class User {
    int budget;
    int partySize;
    String theme1, theme2, atm;
}
// establishment class, turns each group in the firebase db into a usable object
class Establishment {
    public String atmosphere, theme1, theme2, budget, name;
    public int size;

    @Override
    public String toString() {
        return "Establishment{" +
                "name='" + name + '\'' +
                ", atmosphere='" + atmosphere + '\'' +
                ", theme1='" + theme1 + '\'' +
                ", theme2='" + theme2 + '\'' +
                ", budget='" + budget + '\'' +
                ", size=" + size +
                '}';
    }
}