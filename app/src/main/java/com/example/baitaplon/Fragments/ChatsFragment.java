package com.example.baitaplon.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baitaplon.Adapter.UserAdapter;
import com.example.baitaplon.Model.Chat;
import com.example.baitaplon.Model.User;
import com.example.baitaplon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ChatsFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;
    private List<String> userslist ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_chats,container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fuser= FirebaseAuth.getInstance().getCurrentUser();

        userslist= new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("chats");
        //System.out.println("***"  +reference);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {// hiển thị danh sách user có liên lạc với mình
                userslist.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat= snapshot.getValue(Chat.class);
                    if(chat.getSender().equals(fuser.getUid())){
                        userslist.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(fuser.getUid())){
                        userslist.add(chat.getSender());
                    }
                }
               // System.out.println("****"+userslist.size());
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    private void readChats(){
        mUsers= new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user= snapshot.getValue(User.class);
                    //display 1 user from chat
                    for(String id: userslist){
                        if(user.getId().equals(id)){
                            if(mUsers.size() !=0){
                                if(issole(mUsers,user)){
                                    mUsers.add(user);
                                }
                            }else{
                                mUsers.add(user);
                            }
                        }
                    }

                    userAdapter= new UserAdapter(getContext(),mUsers,true);
                    recyclerView.setAdapter(userAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private boolean issole(List<User> list, User user){
        for(User user1: list){
            if(user1.getId().equals(user.getId())){
                return false;
            }
        }
        return true;
    }
}
