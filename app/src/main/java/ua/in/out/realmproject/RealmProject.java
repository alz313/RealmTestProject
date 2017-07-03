package ua.in.out.realmproject;

import android.app.Application;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ua.in.out.realmproject.model.Booking;


public class RealmProject extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Random rnd = new Random();
                        AtomicInteger INTEGER_COUNTER = new AtomicInteger(0);

                        for (int i = 1; i < 11; i++) {
                            Date acceptedDate = null;
                            Booking booking = realm.createObject(Booking.class, INTEGER_COUNTER.getAndIncrement());

                            if (i % 3 == 0) {
                                acceptedDate = new Date(Math.abs(System.currentTimeMillis() - rnd.nextLong()));
                            }
                            booking.setUserName("User Name " + i);
                            booking.setDate(new Date(Math.abs(System.currentTimeMillis() - rnd.nextLong())));
                            booking.setAcceptedOn(acceptedDate);
                        }
                    }
                })
                .build();
        Realm.deleteRealm(realmConfig); // Delete Realm between app restarts.
        Realm.setDefaultConfiguration(realmConfig);
    }
}
