package com.edwinacubillos.sesionroom.ui


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
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

/**
 * A simple [Fragment] subclass.
 */
class UpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_update, container, false)

        var opcion = 0 //0 buscar 1 actualizar
        var idDeudor = 0
        var deudor = Deudor()
        root.bt_buscar.setOnClickListener {
            val nombre = root.et_deudor.text.toString()
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("deudores")
            if(opcion==0){
                var existeDeudor = false
                // Read from the database
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            deudor = snapshot.getValue(Deudor::class.java)!!
                            if(deudor!!.name.equals(nombre)){
                                et_nuevo_valor.setText(deudor.owe.toString())
                                et_nuevo_telefono.setText(deudor.phone)
                                bt_buscar.text = "ACTUALIZAR"
                                existeDeudor = true
                                opcion = 1
                                break           //Conservar último dato encontrado
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
            }
            else{   //Actualizar
                val childUpdate = HashMap<String, Any>()
                childUpdate["phone"] = et_nuevo_telefono.text.toString()
                childUpdate["name"] = nombre
                childUpdate["owe"] = et_nuevo_valor.text.toString().toInt()

                myRef.child(deudor.id).updateChildren(childUpdate)
                bt_buscar.text = "BUSCAR"
                opcion = 0
            }
            /*val deudorDAO: DeudorDAO = SesionRoom.database.DeudorDAO()

            if(opcion == 0){
                var deudor = deudorDAO.searchDedudor(nombre)
                if(deudor!= null){
                    root.et_nuevo_valor.setText(deudor.owe.toString())
                    root.et_nuevo_telefono.setText(deudor.phone)
                    root.bt_buscar.text = "ACTUALIZAR"
                    idDeudor = deudor.id
                    opcion = 1
                }else{
                    Toast.makeText(
                        activity!!.applicationContext,
                        "Deudor no encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else if(opcion==1){   //ACTUALIZACION
                deudorDAO.updateDeudor(
                    Deudor(
                        idDeudor,
                        nombre,
                        root.et_nuevo_telefono.text.toString(),
                        root.et_nuevo_valor.text.toString().toInt()
                    )
                )
                opcion = 0
                root.bt_buscar.text = "BUSCAR"
            }*/
        }
        return root
    }
}
