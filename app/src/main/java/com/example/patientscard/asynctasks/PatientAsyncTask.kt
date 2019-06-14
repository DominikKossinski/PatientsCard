package com.example.patientscard.asynctasks

import android.os.AsyncTask
import android.util.Log
import com.example.patientscard.BuildConfig
import com.example.patientscard.models.Patient
import com.example.patientscard.activities.PatientActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.net.URL

class PatientAsyncTask(var id:String, private val patientActivity: PatientActivity):AsyncTask<String, String, JsonObject>()  {
    override fun doInBackground(vararg params: String?): JsonObject {
        val url = URL("http://10.0.2.2:8080/baseDstu3/Patient/$id/?_format=json&_pretty=true")
        val gson = GsonBuilder().create()
        return gson.fromJson(url.readText(), JsonObject::class.java)
    }

    override fun onPostExecute(result: JsonObject?) {
        val patient = Patient.getPatientFromJsonObject(result!!)
        if(BuildConfig.DEBUG) {
            Log.d("MyLog:PatientAsyncTask", "Pateint:$patient")
        }
        patientActivity.setUpPatient(patient)
        super.onPostExecute(result)
    }


}