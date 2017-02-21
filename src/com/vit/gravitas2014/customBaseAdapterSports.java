package com.vit.gravitas2014;

import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class customBaseAdapterSports extends BaseAdapter 
{
    Context context;
    List<RowItemsports> rowItems;


    public customBaseAdapterSports(Context context, List<RowItemsports> items) {
        this.context = context;
        this.rowItems = items;
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
       // holder.txtCat.setText(rowItem.getCat());
        holder.txtLoc.setText(rowItem.getLocation());
        holder.txtTime.setText(rowItem.getTime());
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
