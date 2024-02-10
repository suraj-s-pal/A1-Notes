package com.surajpal.a1_notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.surajpal.a1_notes.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //OnConflictStrategy.Replace: If two notes has same primary key old data will be replaced with new data
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun  deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>
/*
    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteDesc LIKE :query")
    fun searchNote(query: String?) : LiveData<List<Note>>*/

    @Query("SELECT * FROM notes WHERE noteTitle LIKE '%' || :query || '%' OR noteDesc LIKE '%' || :query || '%'")
    fun searchNote(query: String?): LiveData<List<Note>>
}