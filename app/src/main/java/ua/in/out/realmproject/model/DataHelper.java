package ua.in.out.realmproject.model;

import io.realm.Realm;

public class DataHelper {

    public static void rejectItemAsync(Realm realm, final long id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Booking.reject(realm, id);
            }
        });
    }

    public static void acceptItemAsync(Realm realm, final long id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Booking.accept(realm, id);
            }
        });
    }

}