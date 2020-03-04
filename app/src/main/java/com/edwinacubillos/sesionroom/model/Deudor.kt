package com.edwinacubillos.sesionroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_deudor")
class Deudor (
    @PrimaryKey @ColumnInfo( name = "id") val id: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "phone") val phone: String = "",
    @ColumnInfo(name = "cantidad") val owe: Int = 0
)