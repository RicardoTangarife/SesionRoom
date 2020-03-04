package com.edwinacubillos.sesionroom.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edwinacubillos.sesionroom.R
import com.edwinacubillos.sesionroom.SesionRoom
import com.edwinacubillos.sesionroom.model.Deudor
import com.edwinacubillos.sesionroom.model.DeudorDAO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_read.*
import kotlinx.android.synthetic.main.fragment_read.view.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class ReadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_read, container, false)

        root.bt_search.setOnClickListener {
            var nombre :String = root.et_nombre.text.toString()
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("deudores")

            var existeDeudor = false
            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val deudor: Deudor? = snapshot.getValue(Deudor::class.java)
                        if(deudor!!.name.equals(nombre)){
                            tv_deuda.text = deudor.owe.toString()
                            tv_nombre.text = deudor.name
                            tv_telefono.text = deudor.phone
                            existeDeudor = true
                        }
                    }
                    if(!existeDeudor) Toast.makeText(
                        activity!!.applicationContext,
                        "Deudor no encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Lista", "Failed to read value.", error.toException())
                }
            })


        /*    val deudorDAO: DeudorDAO = SesionRoom.database.DeudorDAO()
            var deudor = deudorDAO.searchDedudor(nombre)

            if(deudor!= null){
                tv_nombre.text= deudor.name
                tv_deuda.text = deudor.owe.toString()
                tv_telefono.text = deudor.phone
            }else{
                Toast.makeText(
                    activity!!.applicationContext,
                    "Deudor no encontrado",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
        }
        return root
    }
}