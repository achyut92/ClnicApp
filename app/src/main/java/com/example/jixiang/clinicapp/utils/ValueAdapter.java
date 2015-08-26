package com.example.jixiang.clinicapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.jixiang.clinicapp.R;

import java.util.ArrayList;

/**
 * Created by jixiang on 23/8/15.
 */

public class ValueAdapter extends BaseAdapter implements Filterable {

    private ArrayList<String> mStringList;

    private ArrayList<String> mStringFilterList;

    private LayoutInflater mInflater;

    private ValueFilter valueFilter;

    private ClinicAdapterHolder holder;

    public ValueAdapter(ArrayList<String> mStringList,Context context) {

        this.mStringList=mStringList;

        this.mStringFilterList=mStringList;

        mInflater=LayoutInflater.from(context);

        getFilter();
    }

    //How many items are in the data set represented by this Adapter.
    @Override
    public int getCount() {

        return mStringList.size();
    }

    //Get the data item associated with the specified position in the data set.
    @Override
    public Object getItem(int position) {

        return mStringList.get(position);
    }

    //Get the row id associated with the specified position in the list.
    @Override
    public long getItemId(int position) {

        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */

    //Get a View that displays the data at the specified position in the data set.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ClinicAdapterHolder viewHolder;

        if(convertView==null) {

            viewHolder=new ClinicAdapterHolder();

            convertView=mInflater.inflate(R.layout.clinic_list,null);

            viewHolder.address_1 = (TextView)convertView.findViewById(R.id.address1);
            viewHolder.address_2 = (TextView)convertView.findViewById(R.id.aviva_code);
            viewHolder.clinic = (TextView)convertView.findViewById(R.id.estate);

            convertView.setTag(viewHolder);

        }else{

            viewHolder=(ClinicAdapterHolder)convertView.getTag();
        }

        viewHolder.address_1.setText(mStringList.get(position).toString());
        viewHolder.address_2.setText(mStringList.get(position).toString());
        viewHolder.clinic.setText(mStringList.get(position).toString());

        return convertView;
    }


    //Returns a filter that can be used to constrain data with a filtering pattern.
    @Override
    public Filter getFilter() {

        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }


    private class ValueFilter extends Filter {


        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if(constraint!=null && constraint.length()>0){

                ArrayList<String> filterList=new ArrayList<String>();

                for(int i=0;i<mStringFilterList.size();i++){

                    if(mStringFilterList.get(i).contains(constraint)) {

                        filterList.add(mStringFilterList.get(i));

                    }
                }


                results.count=filterList.size();

                results.values=filterList;

            }else{

                results.count=mStringFilterList.size();

                results.values=mStringFilterList;

            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            mStringList=(ArrayList<String>) results.values;

            notifyDataSetChanged();


        }

    }

}