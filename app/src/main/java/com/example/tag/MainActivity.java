package com.example.tag;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import java.util.ArrayList;
import java.util.Map.Entry;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    private ArrayList<FriendItem> mfriendItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /* initiate adapter */
        mRecyclerAdapter = new MyRecyclerAdapter();

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        db.collection("2옥").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Log.d(TAG, document.getId() + "=>" + document.getData());

                    }
                } else {
                    Log.w(TAG, "Error getting documents", task.getException());
                }
            }
        });


        /* adapt data */
        mfriendItems = new ArrayList<>();
        for(int i=1;i<=10;i++){
            mfriendItems.add(new FriendItem(R.drawable.pancake,i+"번",i+"번째 제목",i+"번째 가수", i+"번째 음역대"));
        }
        mRecyclerAdapter.setFriendList(mfriendItems);
    }

    View.OnClickListener myClickLIstener = new View.OnClickListener() {
        private int targetOctave = 0;
        private String[] targetPitch = null;

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_2octave_1:
                    targetOctave = 2;
                    targetPitch = new String[]{"파", "솔"};
                    break;
                case R.id.button_2octave_2:
                    targetOctave = 2;
                    targetPitch = new String[]{"라", "시"};
                    break;
                case R.id.button_3octave_1:
                    targetOctave = 3;
                    targetPitch = new String[]{"도", "레"};
                    break;
                case R.id.button_3octave_2:
                    targetOctave = 3;
                    targetPitch = new String[]{"미", "파"};
                    break;
                case R.id.button_3octave_3:
                    targetOctave = 3;
                    targetPitch = new String[]{"솔", "라"};
                    break;
            }

        }
    };
}