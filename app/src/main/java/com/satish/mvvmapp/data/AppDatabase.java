package com.satish.mvvmapp.data;

import com.satish.mvvmapp.network.model.MenuItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class AppDatabase {
    public void insertMenuItems(final List<MenuItem> menuItems) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(menuItems);
            }
        });
    }

    public RealmResults<MenuItem> getMenuItems() {
        return Realm.getDefaultInstance().where(MenuItem.class).findAll();
    }
}
