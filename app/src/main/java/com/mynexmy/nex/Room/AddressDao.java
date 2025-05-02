
package com.mynexmy.nex.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AddressDao {

    @Insert
    void insert(Address address);

    @Query("SELECT * FROM Address")
    List<Address> getalladdress();

    @Query("DELETE FROM Address WHERE cid = :id")
    void deleteById(int id);

}