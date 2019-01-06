package com.satish.mvvmapp.network.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MenuItem extends RealmObject {
    @PrimaryKey
    int id;
    String name;
    String description;
    double price;
    String thumbnail;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
