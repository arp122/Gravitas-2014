package com.vit.gravitas2014;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter 
{
    Context context;
    List<RowItem> rowItems;
    Typeface font;

    public CustomBaseAdapter(Context context, List<RowItem> items,Typeface font) {
        this.context = context;
        this.rowItems = items;
        this.font = font;
    }

    

	/*private view holder class*/
    private class ViewHolder {

        TextView txtTitle;
        TextView txtLoc;
        TextView txtTime;
        TextView txtCat;
        View	colourBar;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textViewtitle);
            holder.txtLoc = (TextView) convertView.findViewById(R.id.textViewlocation);
            holder.txtTime=(TextView) convertView.findViewById(R.id.textViewtime);
            holder.colourBar=(View) convertView.findViewById(R.id.eView); 
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getItem(position);

        
        holder.txtTitle.setText(rowItem.getTitle());
        holder.txtTitle.setTypeface(font);
       // holder.txtCat.setText(rowItem.getCat());
        holder.txtLoc.setText(rowItem.getLocation());
        holder.txtLoc.setTypeface(font);
        holder.txtTime.setText(rowItem.getTime());
        holder.txtTime.setTypeface(font);
        holder.colourBar.setBackgroundColor(Color.parseColor(rowItem.getColour()));

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}