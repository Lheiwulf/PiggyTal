package mark.nobleza.uhack4;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by markjon on 12/11/2016.
 */
public class myDataLogAdapter extends ArrayAdapter<myDataLog> {

    public class ViewHolder {
        TextView dataAmount, dataDate;
    }

    public myDataLogAdapter(Context context, ArrayList<myDataLog> dataLogs) {
        super(context, 0, dataLogs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        myDataLog dataLog = getItem(position);

        ViewHolder viewHolder;


        if(convertView == null){
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dataloglayout,parent,false);

            viewHolder.dataAmount = (TextView)convertView.findViewById(R.id.dataAmount);
            viewHolder.dataDate = (TextView)convertView.findViewById(R.id.dataDate);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.dataAmount.setText("P " + String.format("%.4f",dataLog.getAccAmount()));
        viewHolder.dataDate.setText(dataLog.getDate());

        return convertView;
    }
}
