package com.example.questionaire;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.questionaire.db.Category;

public class CategoryListAdapeter extends BaseAdapter
{
    ArrayList<Category> categories;
    Context context;
    
    @Override
    public void notifyDataSetChanged()
    {
        Log.d("TAG","categories length in adapter "+categories.size());
        super.notifyDataSetChanged();
    }
    
    public void setCategoriesList(List<Category> categories)
    {
        this.categories = (ArrayList<Category>) categories;
    }
    
    public CategoryListAdapeter(List<Category> categories, Context context)
    {
        this.categories = (ArrayList<Category>) categories;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return categories.size();
    }

    @Override
    public Object getItem(int arg0)
    {
        // TODO Auto-generated method stub
        return categories.get(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        // TODO Auto-generated method stub
        return categories.get(arg0).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = convertView;
    
        // reuse views
        if (rowView == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.categorylist_row, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) rowView.findViewById(R.id.firstLine);
            viewHolder.desc = (TextView) rowView.findViewById(R.id.secondLine);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.title.setText(categories.get(position).getTitle());
        holder.desc.setText(categories.get(position).getDescription());
        Log.d("TAG","getView "+ holder.title.getText()+","+holder.desc.getText());
        return rowView;
    }

    static class ViewHolder
    {
        public TextView  title;
        public TextView  desc;
    }

}
