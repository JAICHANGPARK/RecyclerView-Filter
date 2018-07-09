package com.dreamwalker.recyclerviewfilter.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dreamwalker.recyclerviewfilter.Model.Item;
import com.dreamwalker.recyclerviewfilter.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable{


   Context context;
   List<Item> sourceItems;
   List<Item> filterdItems;

   MyAdapterListener listener;

    public MyAdapter(Context context, List<Item> sourceItems, MyAdapterListener listener) {
        this.context = context;
        this.sourceItems = sourceItems;
        this.filterdItems = sourceItems;
        this.listener = listener;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(filterdItems.get(position).getTitle());
        holder.url.setText(filterdItems.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return filterdItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = constraint.toString();
                if (searchString.isEmpty()){
                    filterdItems = sourceItems;
                }else {
                    List<Item> resultList = new ArrayList<>();
                    for (Item item: sourceItems){
                        if (item.getTitle().toLowerCase().contains(searchString.toLowerCase())){
                            resultList.add(item);
                        }
                    }

                    filterdItems = resultList;
                }

                FilterResults filterResults = new FilterResults();

                filterResults.values = filterdItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterdItems = (ArrayList<Item>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        public TextView title, url;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            url = itemView.findViewById(R.id.url);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemSelected(filterdItems.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface MyAdapterListener{
        void OnItemSelected(Item item);
    }
}
