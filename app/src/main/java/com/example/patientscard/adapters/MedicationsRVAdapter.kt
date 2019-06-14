package com.example.patientscard.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.patientscard.R
import com.example.patientscard.models.Medication

class MedicationsRVAdapter(var medications: ArrayList<Medication>): RecyclerView.Adapter<MedicationsRVAdapter.MedicationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MedicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medication_row, parent, false)
        return MedicationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medications.size
    }

    override fun onBindViewHolder(viewHolder: MedicationViewHolder, position: Int) {
        val medication = medications[position]
        val code = "Code: ${medication.code}"
        viewHolder.medicationCodeTextview.text = code
        viewHolder.medicationTextTextView.text = medication.text

    }

    class MedicationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val medicationCodeTextview = itemView.findViewById(R.id.medicationCodeTextView) as TextView
        val medicationTextTextView = itemView.findViewById(R.id.medicationTextTextView) as TextView
    }
}