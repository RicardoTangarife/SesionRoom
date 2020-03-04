package com.edwinacubillos.sesionroom.model

import androidx.room.*

@Dao
interface DeudorDAO {

    @Insert
    fun insertDeudor(deudor: Deudor)

    @Query("SELECT * FROM tabla_deudor WHERE name LIKE :nombre")
    fun searchDedudor(nombre: String): Deudor

    @Update
    fun updateDeudor(deudor: Deudor)

    @Delete
    fun deleteDeudor(deudor: Deudor)

    @Query("SELECT * FROM tabla_deudor")
    fun getDeudores()  : List<Deudor>

}