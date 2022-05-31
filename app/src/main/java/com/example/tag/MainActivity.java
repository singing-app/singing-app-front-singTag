package com.example.tag;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    ArrayList<FriendItem> mfriendItems;
    public FriendItem tempFriendItem;
    public ArrayList<CheckBox> checkBoxArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempFriendItem = new FriendItem();
        mfriendItems = new ArrayList<>();

        checkBoxArrayList = new ArrayList<>();
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_2octave_1));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_2octave_2));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_2octave_3));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_2octave_4));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_3octave_1));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_3octave_2));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_3octave_3));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_3octave_4));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_3octave_5));
        checkBoxArrayList.add((CheckBox) findViewById(R.id.checkbox_3octave_6));
        final int checkBoxArrListSize = checkBoxArrayList.size();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /* initiate adapter */
        mRecyclerAdapter = new MyRecyclerAdapter();

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        // mfriendItems = new ArrayList<>();

        View.OnClickListener myClickListener = new View.OnClickListener() {
            private int targetOctave = -1;
            private String targetPitch = null;

            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                ArrayList<String> tempArrList;
                tempFriendItem.init();
                mfriendItems.clear();
                mRecyclerAdapter.setFriendList(mfriendItems);

                for(int i = 0; i < checkBoxArrListSize; i++){
                    if(checkBoxArrayList.get(i).isChecked()){
                        if(i >= 0 && i <= 3){
                            tempArrList = new ArrayList<>(Arrays.asList("파", "솔", "라", "시"));
                            targetOctave = 2;
                            targetPitch = tempArrList.get(i);
                        }
                        else if(i >= 4 && i <= 9){
                            int idx = i - 4;
                            tempArrList = new ArrayList<>(Arrays.asList("도", "레", "미", "파", "솔", "라"));
                            targetOctave = 3;
                            targetPitch = tempArrList.get(idx);
                        }

                        final String tOctave = targetOctave + "옥";
                        db.collection(tOctave).document(targetPitch).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Map<String, Object> tempDBMap;

                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    tempDBMap = document.getData();

                                    mfriendItems.clear();
                                    /* adapt data */
                                    for (Map.Entry<String, Object> elem : tempDBMap.entrySet()) {
                                        mfriendItems.add(new FriendItem(R.drawable.pancake, String.valueOf(elem.getValue()), elem.getKey(), document.getId(), tOctave));
                                        // Log.d(TAG, "Tag - octave text: " + document.getId());
                                    }
                                    mRecyclerAdapter.addFriendList(mfriendItems);


                                } else {
                                    Log.d(TAG, "Error getting documents", task.getException());
                                }
                            }
                        });
                    }
                    // else
                }
            }
        };

        for(CheckBox tempChkBox : checkBoxArrayList){
            tempChkBox.setOnClickListener(myClickListener);
        }
    }
}