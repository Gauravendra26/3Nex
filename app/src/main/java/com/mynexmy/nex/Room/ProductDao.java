package com.mynexmy.nex.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao
{
    @Insert
    void insertrecord(Product product);


    @Query("SELECT EXISTS(SELECT * FROM Product WHERE pid = :productid)")
    Boolean is_exist(int productid);


    @Query("SELECT * FROM Product")
    List<Product> getallproduct();

    @Query("DELETE FROM Product WHERE pid = :id")
    void deleteById(int id);

    @Query("UPDATE Product SET pname = :name, price=:Price, qnt=:Qnt, sale=:Sale, image=:Image WHERE pid = :id")
    public void updateRecord(int id ,String name,int Price,int Qnt,int Sale,String Image );
}
