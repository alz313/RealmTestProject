package ua.in.out.realmproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import ua.in.out.realmproject.model.Booking;


class BookingRecyclerViewAdapter extends RealmRecyclerViewAdapter<Booking, BookingRecyclerViewAdapter.MyViewHolder> {
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyy-mm-dd", Locale.getDefault());

    BookingRecyclerViewAdapter(OrderedRealmCollection<Booking> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Booking obj = getItem(position);
        holder.data = obj;

        //noinspection ConstantConditions
        holder.userName.setText(obj.getUserName());
        holder.date.setText(mDateFormat.format(obj.getDate()));
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getCount();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView date;

        public Booking data;

        MyViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.textViewUuserNname);
            date = (TextView) view.findViewById(R.id.textViewDate);
        }
    }
}
