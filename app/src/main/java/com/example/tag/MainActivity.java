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

    public ArrayList<CheckBox> checkBoxArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                ArrayList<String> tempArrList;
                ArrayList<FriendItem> mfriendItems = new ArrayList<>();

                for(int i = 0; i < checkBoxArrListSize; i++){
                    if(checkBoxArrayList.get(i).isChecked()){
                        if(i >= 0 && i <= 3){
                            tempArrList = new ArrayList<>(Arrays.asList("파", "솔", "라", "시"));
                            targetOctave = 2;
                            targetPitch = tempArrList.get(i);
                            mfriendItems = addTagList(mfriendItems, targetOctave, targetPitch);
                            Log.d(TAG, "TAG by onClick()"+ mfriendItems);
                        }
                        else if(i >= 4 && i <= 9){
                            int idx = i - 4;
                            tempArrList = new ArrayList<>(Arrays.asList("도", "레", "미", "파", "솔", "라"));
                            targetOctave = 3;
                            targetPitch = tempArrList.get(idx);
                            mfriendItems = addTagList(mfriendItems, targetOctave, targetPitch);
                            Log.d(TAG, "TAG by onClick()"+ mfriendItems);
                        }
                    }
                }
                updateTagList(mfriendItems);
            }
        };

        for(CheckBox tempChkBox : checkBoxArrayList){
            tempChkBox.setOnClickListener(myClickListener);
        }
    }

    public ArrayList<FriendItem> addTagList(ArrayList<FriendItem> arr, int _tOctave, String tPitch){

        if(_tOctave == -1 || tPitch == null){ return null; }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        String tOctave = _tOctave + "옥";

        db.collection(tOctave).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Map<String, Object> tempDBMap;

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Log.d(TAG, document.getId() + "=>" + document.getData());

                        if(Objects.equals(document.getId(), tPitch)){
                            int x = 10;
                            tempDBMap = document.getData();
                            // Log.d(TAG,tempDBMap + "");
                            /* adapt data */
                            for (Map.Entry<String, Object> elem : tempDBMap.entrySet()) {
                                arr.add(new FriendItem(R.drawable.pancake, elem.getKey(), String.valueOf(elem.getValue()), document.getId()));
                            }

                            break;
                        }  // error point
                        // Log.d(TAG, "TAG by ADD(): "+ arr.get(arr.size()-1).getText_title_insert() + arr.get(arr.size()-1).getText_singer_insert());
                    }
                } else {
                    Log.w(TAG, "Error getting documents", task.getException());
                }
            }
        });
        // Log.d(TAG, "TAG by ADD(): "+ arr.get(arr.size()-1).getText_title_insert() + arr.get(arr.size()-1).getText_singer_insert());
        Log.d(TAG, "" + arr);
        return arr;
    }

    public void updateTagList(ArrayList<FriendItem> arr){
        if(arr == null){ return; }
        else {
            mRecyclerAdapter.setFriendList(arr);
            Log.d(TAG,"TAG by update(): "+arr);
        }
    }

}