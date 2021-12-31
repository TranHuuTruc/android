package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Note> notes;

    Adapter(Context context, List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = inflater.inflate(R.layout.cutom_list_view, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        String tiltle = notes.get(position).getTiltle();
        String date = notes.get(position).getDate();
        String time = notes.get(position).getTime();


        holder.nTiltle.setText(tiltle);
        holder.nDate.setText(date);
        holder.nTime.setText(time);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTiltle, nDate, nTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nTiltle = itemView.findViewById(R.id.nTiltle);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), Details.class);
                    i.putExtra("ID",notes.get(getAdapterPosition()).getID());

                    v.getContext().startActivity(i);

                }
            });

        }
    }


    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

            String charString = constraint.toString();
                if (charString.isEmpty()) {
               notes = notes;
            } else {
                    List<Note> filteredList = new ArrayList<>();
                for (Note row : notes) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getTiltle().toLowerCase().contains(charString.toLowerCase()) ) {
                        filteredList.add(row);
                    }
                }

                notes = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = notes;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
             notes= (ArrayList<Note>) results.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}
