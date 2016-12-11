package mark.nobleza.uhack4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by markjon on 12/10/2016.
 */
public class myPiggyAdapter extends ArrayAdapter<myPiggy> {

    public class ViewHolder {
        TextView tvPD, tvPA;
    }

    public myPiggyAdapter(Context context, ArrayList<myPiggy> myPiggies) {
        super(context, 0, myPiggies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final myPiggy piggy = getItem(position);

        final ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.piggylayout, parent, false);

            viewHolder.tvPD = (TextView) convertView.findViewById(R.id.piggyDesc);
            viewHolder.tvPA = (TextView) convertView.findViewById(R.id.piggyAmount);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            viewHolder.tvPD.setText(piggy.getPiggyDesc());
            viewHolder.tvPA.setText(piggy.getPiggyDate());

            return convertView;
    }
}

