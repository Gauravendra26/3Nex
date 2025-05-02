
package com.mynexmy.nex.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Address.class}, version = 4)
public abstract class AddressDataBase extends RoomDatabase {

    public abstract AddressDao addressDao();

}
