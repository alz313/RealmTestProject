package ua.in.out.realmproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import ua.in.out.realmproject.model.Booking;
import ua.in.out.realmproject.model.DataHelper;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    private RecyclerView mAllRecyclerView;
    private RecyclerView mAcceptedRecyclerView;

    private TextView mAcceptedCounterTextView;

    private BookingRecyclerViewAdapter mAdapter;

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {
        TouchHelperCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    DataHelper.rejectItemAsync(mRealm, viewHolder.getItemId());
                    break;
                case ItemTouchHelper.RIGHT:
                    DataHelper.acceptItemAsync(mRealm, viewHolder.getItemId());
                    break;
                default:
                    break;
            }
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAcceptedCounterTextView = (TextView) findViewById(R.id.text_view_accepted_counter);

        // Get a Realm instance for this thread
        mRealm = Realm.getDefaultInstance();

        mAllRecyclerView = (RecyclerView) findViewById(R.id.all_recycler_view);
        setUpAllRecyclerView();

        mAcceptedRecyclerView = (RecyclerView) findViewById(R.id.accepted_recycler_view);
        setUpAcceptedRecyclerView();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAllRecyclerView.setAdapter(null);
        mRealm.close();
    }


    private void setUpAllRecyclerView() {
        RealmResults<Booking> bookings = mRealm.where(Booking.class)
                .isNull(Booking.FIELD_ACCEPTED)
                .isNull(Booking.FIELD_REJECTED)
                .findAll()
                .sort(Booking.FIELD_DATE);
        mAdapter = new BookingRecyclerViewAdapter(bookings);

        mAllRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAllRecyclerView.setAdapter(mAdapter);
        mAllRecyclerView.setHasFixedSize(true);
        mAllRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(mAllRecyclerView);
    }

    private void setUpAcceptedRecyclerView() {
        RealmResults<Booking> bookings = mRealm.where(Booking.class)
                .isNotNull(Booking.FIELD_ACCEPTED)
                .isNull(Booking.FIELD_REJECTED)
                .findAll()
                .sort(Booking.FIELD_ACCEPTED, Sort.ASCENDING);

        mAcceptedCounterTextView.setText(String.valueOf(bookings.size()));

        bookings.addChangeListener(new RealmChangeListener<RealmResults<Booking>>() {
            @Override
            public void onChange(RealmResults<Booking> bookings) {
                mAcceptedCounterTextView.setText(String.valueOf(bookings.size()));
            }
        });

        mAdapter = new BookingRecyclerViewAdapter(bookings);

        mAcceptedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAcceptedRecyclerView.setAdapter(mAdapter);
        mAcceptedRecyclerView.setHasFixedSize(true);
        mAcceptedRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
