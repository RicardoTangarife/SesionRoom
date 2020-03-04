package com.edwinacubillos.sesionroom.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwinacubillos.sesionroom.R
import com.edwinacubillos.sesionroom.SesionRoom
import com.edwinacubillos.sesionroom.model.Deudor
import com.edwinacubillos.sesionroom.model.DeudorDAO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    var allDeudores: MutableList<Deudor> = mutableListOf()
    lateinit var deudoresRVAdapter: DeudoresRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_list, container, false)

        deudoresRVAdapter = DeudoresRVAdapter(
            activity!!.applicationContext,
            allDeudores as ArrayList<Deudor>
        )
        root.rv_deudores.layoutManager = LinearLayoutManager(
            activity!!.applicationContext,
            RecyclerView.VERTICAL,
            false
        )
        root.rv_deudores.setHasFixedSize(true)
        root.rv_deudores.adapter = deudoresRVAdapter


        /*var deudorDAO: DeudorDAO = SesionRoom.database.DeudorDAO()
        var allDeudores: List<Deudor> = deudorDAO.getDeudores()

        var deudoresRVAdapter = DeudoresRVAdapter(
            activity!!.applicationContext,
            allDeudores as ArrayList<Deudor>
        )

        root.rv_deudores.adapter = deudoresRVAdapter*/

        return root
    }

    override fun onResume() {
        super.onResume()
        cargarDeudores()
    }

    private fun cargarDeudores() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        allDeudores.clear()
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //val value = dataSnapshot.getValue(String::class.java)
                for (snapshot in dataSnapshot.children) {
                    var deudor: Deudor? = snapshot.getValue(Deudor::class.java)
                    allDeudores.add(deudor!!)
                }
                deudoresRVAdapter.notifyDataSetChanged()
                //Log.d("Lista", dataSnapshot.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Lista", "Failed to read value.", error.toException())
            }
        })
    }
}