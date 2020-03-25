package org.juicecode.hlam.core.contacts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contacts")
    ArrayList<Contact> getAll();
    @Query("SELECT * FROM contacts WHERE contact_id = :id")
    Contact getById(long id);
    @Insert
    void insert (Contact contact);
    @Insert
    void insertMany (Contact contact);
    @Delete
    void delete(Contact contact);
    @Query("DELETE FROM contacts WHERE contact_id = :id")
    void deleteById(long id);
    @Update
    void update (Contact contact);
}
