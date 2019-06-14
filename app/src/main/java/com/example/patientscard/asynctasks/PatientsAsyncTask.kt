package com.example.patientscard.asynctasks

import android.os.AsyncTask
import android.util.Log
import com.example.patientscard.BuildConfig
import com.example.patientscard.models.Patient
import com.example.patientscard.adapters.PatientsRVAdapter
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.net.URL

class PatientsAsyncTask(private val pattern:String, private val patientsRVAdapter: PatientsRVAdapter):AsyncTask<String, String, JsonObject>() {
    override fun doInBackground(vararg params: String?): JsonObject {
        val url = URL("http://10.0.2.2:8080/baseDstu3/Patient?name=$pattern&_count=20&_format=json&_pretty=true")
        val gson = GsonBuilder().create()
        return gson.fromJson(url.readText(), JsonObject::class.java)
    }

    override fun onPostExecute(result: JsonObject?) {
        if(result!!.has("entry")) {
            if(BuildConfig.DEBUG) {
                Log.d("MyLog:PatientAsyncTask", "Response patients length: ${result.getAsJsonArray("entry").size()}")
            }
            val patientsArray = result.getAsJsonArray("entry")
            val patients = ArrayList<Patient>()
            for (patientObject in patientsArray) {
                val patient =
                    Patient.getPatientFromJsonObject(patientObject.asJsonObject.getAsJsonObject("resource"))
                patients.add(patient)
            }
            if (BuildConfig.DEBUG) {
                Log.d("MyLog:PatientsAsyncTask", "Patients size: ${patients.size}")
            }
            patientsRVAdapter.setNewPatients(patients)
        } else  {
            patientsRVAdapter.setNewPatients(ArrayList())
        }
    }
}