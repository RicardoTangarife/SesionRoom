package com.edwinacubillos.sesionroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.edwinacubillos.sesionroom.R
import com.edwinacubillos.sesionroom.SesionRoom
import com.edwinacubillos.sesionroom.model.Deudor
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.sql.Types.NULL

class CreateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_create, container, false)

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")

        root.bt_save.setOnClickListener {
            val nombre = root.et_name.text.toString()
            val telefono = root.et_phone.text.toString()
            val cantidad = root.et_owe.text.toString()

            val idDeudor = myRef.push().key

            val deudor = Deudor (idDeudor!!, nombre, telefono, cantidad.toInt())

            /*val deudorDAO = SesionRoom.database.DeudorDAO()
            deudorDAO.insertDeudor(deudor)*/

            myRef.child(idDeudor).setValue(deudor)

            root.et_name.setText("")
            root.et_phone.setText("")
            root.et_owe.setText("")
        }

        return root
    }
}