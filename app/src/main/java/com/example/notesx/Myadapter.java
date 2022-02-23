package com.example.notesx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class Myadapter extends RecyclerView.Adapter<Myadapter.myviewholder>{
    Context context;
    RealmResults<Note> notelist;

    public Myadapter(Context context, RealmResults<Note> notelist) {
        this.context = context;
        this.notelist = notelist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myviewholder (LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.myviewholder holder, int position) {
        Note note = notelist.get(position);
        holder.titleoutput.setText(note.getTitle());
        holder.descriptionoutput.setText(note.getDescription());
        String formattedtime = DateFormat.getDateTimeInstance().format(note.time);
        holder.timeoutput.setText(formattedtime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Note Deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        TextView titleoutput;
        TextView descriptionoutput;
        TextView timeoutput;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            titleoutput = itemView.findViewById(R.id.titleoutput);
            descriptionoutput = itemView.findViewById(R.id.descriptionoutput);
            timeoutput = itemView.findViewById(R.id.timeoutput);

        }
    }
}
