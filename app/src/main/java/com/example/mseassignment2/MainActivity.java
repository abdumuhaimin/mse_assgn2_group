package com.example.mseassignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Friend> friendsList = new ArrayList<>();
    private DatabaseHelper db;

    private TextView noFriendsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this,LinearLayoutManager.VERTICAL,16));
        recyclerView.setAdapter(mAdapter);

        mAdapter = new FriendAdapter(this,friendsList);
        recyclerView.setLayoutManager(layoutManager);

        db = new DatabaseHelper(this);
        friendsList.addAll(db.getAllFriends());

        noFriendsView = findViewById(R.id.empty_friends_view);

        final EditText inputFriend = findViewById(R.id.inputName);

        Button add = findViewById(R.id.add_new);
        Button delete = findViewById(R.id.delete_first);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewFriend(inputFriend.getText().toString());
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFirstFriend();
            }
        });

    }

    private void addNewFriend(String friend){
        long id = db.insertFriend(friend);
        Friend f = db.getFriend(id);

        if(f!=null){
            friendsList.add(0,f);
            mAdapter.notifyDataSetChanged();
            toggleEmptyFriends();
        }
    }

    private void deleteFirstFriend() {
        // deleting the note from db
        db.deleteFriend(friendsList.get(0));

        // removing the note from the list
        friendsList.remove(0);
        mAdapter.notifyItemRemoved(0);

        toggleEmptyFriends();
    }


    private void toggleEmptyFriends() {
        if(db.getFriendsCount() > 0){
            noFriendsView.setVisibility(View.GONE);
        }
        else {
            noFriendsView.setVisibility(View.VISIBLE);
        }
    }

}
