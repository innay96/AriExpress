package com.example.emotz.ariexpress.modules;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.emotz.ariexpress.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter  {
    private ArrayList<ProductWithID> list = new ArrayList<ProductWithID>();
    private Context context;
    private DatabaseReference productsDatabase;
    private int count = 0;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;

    public MyCustomAdapter(ArrayList<ProductWithID> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {

        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final String ID=list.get(position).ID;
        View view = convertView;
        notifyDataSetChanged();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cortume_layout_list, null);
        }

        //Handle TextView and display string from your list
        //here we take myCart single item and print it to the list, I took only name
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).name);

        //Handle buttons and add onClickListeners
        Button cartBtn = (Button)view.findViewById(R.id.cart_btn);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID=user.getUid();
        cartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                    Log.d("productID", ID);
                    addNewItem("id", ID);
                    //parent.getChildAt(0).getId();
                    //Log.d("out: ", );
                notifyDataSetChanged();
            }
        });

        notifyDataSetChanged();
        return view;
    }



    public void addNewItem(String productName, String productID){

        productsDatabase = FirebaseDatabase.getInstance().getReference().child("Cart").child(userID);
        productsDatabase.child(productID).setValue(productID)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(MyCustomAdapter.this,"Item was added successfully!!",
//                                Toast.LENGTH_LONG).show();
                    }
                })


                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
//                        Toast.makeText(MyCustomAdapter.this,"Failed adding item",
//                                Toast.LENGTH_LONG).show();
                    }
                });
    }

//    public void removeItem(String productID){
//        productsDatabase = FirebaseDatabase.getInstance().getReference().child("Cart").child(productID);
//        productsDatabase.removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
////                        Toast.makeText(AdminActivity.this,"Item removed!!",
////                                Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
////                        Toast.makeText(AdminActivity.this,"Failed removing item",
////                                Toast.LENGTH_LONG).show();
//                    }
//                });
//
//    }
}