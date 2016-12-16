package com.foursquare.takehome;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvRecyclerView;
    private PersonAdapter personAdapter;
    // use LinkedList for using add person frequetly, instead of ArrayList
    private final List<Person> personList = new LinkedList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRecyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);
        personAdapter = new PersonAdapter(MainActivity.this, personList);

        //TODO hook up your adapter and any additional logic here
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvRecyclerView.setAdapter(personAdapter);

        parseVenueFromResponse();
    }


    /**
     * Parsing a fake json response from assets/people.json
     */
    private void parseVenueFromResponse() {
        new AsyncTask<Void, Void, Venue>() {
            @Override
            protected Venue doInBackground(Void... params) {
                try {
                    InputStream is = getAssets().open("people.json");
                    InputStreamReader inputStreamReader = new InputStreamReader(is);

                    PeopleHereJsonResponse response = new Gson().fromJson(inputStreamReader, PeopleHereJsonResponse.class);
                    return response.getVenue();
                } catch (Exception e) {}

                return null;
            }

            @Override
            protected void onPostExecute(Venue venue) {
                //TODO use the venue object to populate your recyclerview
                for(Person visitor : venue.getVisitors()) {
                    personList.add(visitor);
                }

                // save arrTimes and leaveTimes into two arrays
                long[] arrTimes = new long[personList.size()];
                long[] leaveTimes = new long[personList.size()];
                for (int i = 0; i < personList.size(); i++) {
                    arrTimes[i] = personList.get(i).getArriveTime();
                    leaveTimes[i] = personList.get(i).getLeaveTime();
                }

                // sort two arrays
                Arrays.sort(arrTimes);
                Arrays.sort(leaveTimes);

                // before first visitor arrives
                if (arrTimes[0] > venue.getOpenTime()) {
                    Person gap = new Person();
                    gap.setName(getResources().getString(R.string.GAP_NAME));
                    gap.setArriveTime(venue.getOpenTime());
                    gap.setLeaveTime(arrTimes[0]);
                    personList.add(gap);
                }
                // after last visitor leaves
                if (leaveTimes[leaveTimes.length - 1] < venue.getCloseTime()) {
                    Person gap = new Person();
                    gap.setName(getResources().getString(R.string.GAP_NAME));
                    gap.setArriveTime(leaveTimes[leaveTimes.length - 1]);
                    gap.setLeaveTime(venue.getCloseTime());
                    personList.add(gap);
                }

                // deal with the intermediate time gap
                for (int i = 1; i < arrTimes.length; i++) {
                    // before sb arrives, # of person arrives = # of person leaves
                    if (arrTimes[i] > leaveTimes[i - 1]) {
                        // gap time before last leaves and sb arrives
                        Person gap = new Person();
                        gap.setName(getResources().getString(R.string.GAP_NAME));
                        gap.setArriveTime(leaveTimes[i - 1]);
                        gap.setLeaveTime(arrTimes[i]);
                        personList.add(gap);
                    }
                }

                // Sort visitors and gaps based on arrTime and leaveTime
                Collections.sort(personList, new Comparator<Person>() {
                    @Override
                    public int compare(Person p1, Person p2) {
                        if (p1.getArriveTime() == p2.getArriveTime()) {
                            return (int) (p1.getLeaveTime() - p2.getLeaveTime());
                        } else {
                            return (int) (p1.getArriveTime() - p2.getArriveTime());
                        }
                    }
                });
                personAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
