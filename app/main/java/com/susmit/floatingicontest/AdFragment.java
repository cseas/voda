package com.susmit.floatingicontest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.susmit.floatingicontest.firebase.FirebaseObject;

import java.util.HashMap;
import java.util.Map;

public class AdFragment extends Fragment {

    static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(0, "Gaana");
        map.put(1, "Pizza Hut");
        map.put(2, "Lakme Salon");
        map.put(3, "Myntra");
        map.put(4, "Faasoos");
        map.put(5, "Amazon Prime");
    }

    int position;
    FirebaseObject object;

    @SuppressLint("ValidFragment")
    public AdFragment(int pos){
        position = pos;
    }

    public AdFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_adframe, null);
        ImageView v = view.findViewById(R.id.image);
        //position = getArguments().getInt("position");
        switch (position){
            case 0:
                v.setImageDrawable(getActivity().getDrawable(R.drawable.gaana));
                break;

            case 1:
                v.setImageDrawable(getActivity().getDrawable(R.drawable.pizza_hut));
                break;

            case 2:
                v.setImageDrawable(getActivity().getDrawable(R.drawable.lakme));
                break;

            case 3:
                v.setImageDrawable(getActivity().getDrawable(R.drawable.myntra));
                break;

            case 4:
                v.setImageDrawable(getActivity().getDrawable(R.drawable.fasoos));
                break;

            case 5:
                v.setImageDrawable(getActivity().getDrawable(R.drawable.amazon_prime));
                break;
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i;
                for(i=0; i < MainActivity.objects.size(); i++){
                    FirebaseObject obj = MainActivity.objects.get(i);
                    if(map.get(position).equals(obj.getName())){
                        object = obj;
                        break;
                    }
                }
                if(object==null){
                    FirebaseObject o = new FirebaseObject(map.get(position), 1);
                    MainActivity.objects.add(o);
                    MainActivity.databaseReference.child(map.get(position)).setValue(o).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    object.setNumber(object.getNumber()+1);
                    MainActivity.databaseReference.child(map.get(position)).setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                    MainActivity.objects.get(i).setNumber(object.getNumber());
                }

            }
        });
        return view;
    }


}
