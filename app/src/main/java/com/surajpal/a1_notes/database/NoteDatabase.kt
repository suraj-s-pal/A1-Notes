package com.surajpal.a1_notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.surajpal.a1_notes.model.Note


@Database (entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object {
        @Volatile
        private var INSTANCE : NoteDatabase ? = null

        fun getDatabase(context: Context) : NoteDatabase {
            if (INSTANCE == null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java,"note_db").build()
                }
            }
            return INSTANCE!!
        }
    }
}