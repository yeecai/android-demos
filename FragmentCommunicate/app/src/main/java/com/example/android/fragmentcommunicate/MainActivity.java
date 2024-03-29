/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fragmentcommunicate;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
    implements SimpleFragment.OnFragmentInterationListener{
    private static Button mButton;
    private Button nButton;

    private static boolean isFragmentDisplayed = false;

    static final String STATE_FRAGMWNT = "state_of_fragment";
    private static int mRadioButtonChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.open_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFragmentDisplayed) {
                    displayFragment();
                }else {
                    closeFragment();
                }
            }
        });

        nButton = (Button) findViewById(R.id.next_button);

        nButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);

            }
        });

        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMWNT);
            if (isFragmentDisplayed) {
                mButton.setText(R.string.close);
            }
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of fragment
        savedInstanceState.putBoolean(STATE_FRAGMWNT,isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice); // Do we have to new an instance each single time?
        //Get the FragmentManager and start a transaction
        FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit();
        mButton.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    public void closeFragment() {

        FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
        SimpleFragment simpleFragment =  (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        //  if the simplefragment exists, need to remove the callback. which means the displayFragment() called
        if( simpleFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }

        mButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }


    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;
        Toast.makeText(this,"Choice is " + Integer.toString(choice),Toast.LENGTH_SHORT).show();
    }
}
