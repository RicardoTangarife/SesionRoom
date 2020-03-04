package com.edwinacubillos.sesionroom.ui


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.edwinacubillos.sesionroom.R
import com.edwinacubillos.sesionroom.SesionRoom
import com.edwinacubillos.sesionroom.model.Deudor
import com.edwinacubillos.sesionroom.model.DeudorDAO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_delete.view.*

/**
 * A simple [Fragment] subclass.
 */
class DeleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_delete, container, false)

        root.bt_eliminar.setOnClickListener{
            val nombre = root.et_deudor.text.toString()

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("deudores")

            var existeDeudor = false
            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val deudor: Deudor? = snapshot.getValue(Deudor::class.java)
                        if(deudor!!.name.equals(nombre)){
                            val alertDialog: AlertDialog? = activity?.let{
                                val builder = AlertDialog.Builder(it)
                                builder.apply {
                                    setMessage("Desea Eliminar a "+deudor.name+" que le debe "+deudor.owe+"?")
                                    setPositiveButton(
                                        "Eliminar"
                                    ){ dialog, id ->
                                        myRef.child(deudor.id).removeValue()
                                    }
                                    setNegativeButton(
                                        "Cancelar"
                                    ){dialog, id ->
                                    }
                                }
                                builder.create()
                            }
                            alertDialog?.show()
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

         /*   val deudorDAO : DeudorDAO = SesionRoom.database.DeudorDAO()
            val deudor = deudorDAO.searchDedudor(nombre)

            if(deudor != null){
                val alertDialog: AlertDialog? = activity?.let{
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setMessage("Desea Eliminar a "+deudor.name+" que le debe "+deudor.owe+"?")
                        setPositiveButton(
                            "Eliminar"
                        ){ dialog, id ->
                            deudorDAO.deleteDeudor(deudor)
                        }
                        setNegativeButton(
                            "Cancelar"
                        ){dialog, id ->
                        }
                    }
                    builder.create()
                }
                alertDialog?.show()
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
