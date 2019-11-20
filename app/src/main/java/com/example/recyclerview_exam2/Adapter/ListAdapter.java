package com.example.recyclerview_exam2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview_exam2.Activity.DetailActivity;
import com.example.recyclerview_exam2.R;
import com.example.recyclerview_exam2.model.ItemData;
import com.example.recyclerview_exam2.model.ItemImg;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements Filterable {

    private ArrayList<ItemData> itemData;
    private ArrayList<ItemData> itemDataFull;
    private Context context;

    public ListAdapter(Context context,ArrayList<ItemData> list) {
        this.itemData = list;
        this.context = context;
        itemDataFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list,parent,false);

        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {

        holder.title.setText(itemData.get(position).getTitle());
        holder.day.setText(itemData.get(position).getDay());
        holder.content.setText(itemData.get(position).getContent());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailActivity = new Intent(context, DetailActivity.class);
                detailActivity.putExtra(DetailActivity.EXTRA_DATA, itemData.get(position));
                context.startActivity(detailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemData ? itemData.size() : 0);
    }

    @Override
    public Filter getFilter() {
        return itemDataFilter;
    }
    private  Filter itemDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ItemData> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(itemDataFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(ItemData item : itemDataFull){
                    if(item.getContent().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemData.clear();
            itemData.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        protected TextView title;
        protected TextView day;
        protected TextView content;
        protected CardView cardView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.tv_title);
            this.day = (TextView) itemView.findViewById(R.id.tv_day);
            this.content = (TextView) itemView.findViewById(R.id.tv_content) ;
            this.cardView = (CardView) itemView.findViewById(R.id.layout);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1001:

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context)
                                .inflate(R.layout.edit_box, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                        final EditText editTextTitle = (EditText) view.findViewById(R.id.editText_dialog_title);
                        final EditText editTextDay = (EditText) view.findViewById(R.id.editText_dialog_day);
                        final EditText editTextContent = (EditText) view.findViewById(R.id.editText_dialog_content);

                        editTextTitle.setText(itemData.get(getAdapterPosition()).getTitle());
                        editTextDay.setText(itemData.get(getAdapterPosition()).getDay());
                        editTextContent.setText(itemData.get(getAdapterPosition()).getContent());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String strTitle = editTextTitle.getText().toString();
                                String strDay = editTextDay.getText().toString();
                                String strContent = editTextContent.getText().toString();

                                ItemData item = new ItemData(strTitle, strDay, strContent);

                                itemData.set(getAdapterPosition(), item);
                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();

                            }
                        });
                        dialog.show();
                        break;
                    case 1002:
                        itemData.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), itemData.size());
                        break;
                }
                return true;
            }
        };
    }

}
