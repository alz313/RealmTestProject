package ua.in.out.realmproject.model;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Booking extends RealmObject {
    public static final String FIELD_COUNT = "count";
    public static final String FIELD_DATE = "date";
    public static final String FIELD_ACCEPTED = "acceptedOn";
    public static final String FIELD_REJECTED = "rejectedOn";

    @PrimaryKey
    private int count;

    private String userName;
    private Date date;
    private Date acceptedOn;
    private Date rejectedOn;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getAcceptedOn() {
        return acceptedOn;
    }

    public void setAcceptedOn(Date acceptedOn) {
        this.acceptedOn = acceptedOn;
    }

    public Date getRejectedOn() {
        return rejectedOn;
    }

    public void setRejectedOn(Date rejectedOn) {
        this.rejectedOn = rejectedOn;
    }

    public int getCount() {
        return count;
    }

    public String getCountString() {
        return Integer.toString(count);
    }

    static void reject(Realm realm, long id) {
        Booking booking = realm.where(Booking.class).equalTo(FIELD_COUNT, id).findFirst();
        // Otherwise it has been deleted already.
        if (booking != null) {
            //booking.setRejectedOn(new Date());
            booking.deleteFromRealm();
        }
    }

    static void accept(Realm realm, long id) {
        Booking booking = realm.where(Booking.class).equalTo(FIELD_COUNT, id).findFirst();
        // Otherwise it has been deleted already.
        if (booking != null) {
            booking.setAcceptedOn(new Date());
        }
    }
}