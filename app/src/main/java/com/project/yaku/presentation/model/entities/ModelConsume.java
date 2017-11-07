package com.project.yaku.presentation.model.entities;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by luis on 23/10/17.
 */

@IgnoreExtraProperties
public class ModelConsume {
    long bath_room_consume;
    long clean_clothes_consume;
    long garden_consume;
    long kitchen_consume;
    long wash_car_consume;

    public ModelConsume() {
    }

    public ModelConsume(long bath_room_consume, long clean_clothes_consume, long garden_consume, long kitchen_consume, long wash_car_consume) {
        this.bath_room_consume = bath_room_consume;
        this.clean_clothes_consume = clean_clothes_consume;
        this.garden_consume = garden_consume;
        this.kitchen_consume = kitchen_consume;
        this.wash_car_consume = wash_car_consume;
    }

    public long getBath_room_consume() {
        return bath_room_consume;
    }

    public long getClean_clothes_consume() {
        return clean_clothes_consume;
    }

    public long getGarden_consume() {
        return garden_consume;
    }

    public long getKitchen_consume() {
        return kitchen_consume;
    }

    public long getWash_car_consume() {
        return wash_car_consume;
    }
}
