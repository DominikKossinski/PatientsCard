package com.example.patientscard.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.patientscard.R
import com.example.patientscard.activities.MainActivity
import com.example.patientscard.models.Observation

class ObservationsRVAdapter(var observations:ArrayList<Observation>): RecyclerView.Adapter<ObservationsRVAdapter.ObservationViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ObservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.observation_row, parent, false)
        return ObservationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return observations.size
    }

    override fun onBindViewHolder(viewHolder: ObservationViewHolder, position: Int) {
        val observation = observations[position]
        val name = "Observation: ${observation.name}"
        viewHolder.nameTextView.text = name
        viewHolder.dateTextView.text = MainActivity.dateTimeFormat.format(observation.date)
        viewHolder.unitTextView.text = observation.unit
        if(observation.unit.contentEquals("")) {
            viewHolder.valueTextView.text = ""
        } else {
            val value = "Value: ${observation.value}"
            viewHolder.valueTextView.text = value
        }
    }


    class ObservationViewHolder(itemView: View):RecyclerView.ViewHolder(itemView ){
        val nameTextView = itemView.findViewById(R.id.observationNameTextView) as TextView
        val valueTextView = itemView.findViewById(R.id.observationValueTextView) as TextView
        val unitTextView = itemView.findViewById(R.id.observationUnitTextView) as TextView
        val dateTextView = itemView.findViewById(R.id.observationDateTextView) as TextView
    }
}