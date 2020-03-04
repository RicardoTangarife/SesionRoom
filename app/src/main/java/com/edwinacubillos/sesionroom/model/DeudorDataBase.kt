package com.edwinacubillos.sesionroom.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Deudor::class],version = 1)
abstract class DeudorDataBase : RoomDatabase(){

    abstract  fun DeudorDAO(): DeudorDAO


}