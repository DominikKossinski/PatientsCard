package com.example.patientscard.asynctasks

import android.os.AsyncTask
import android.util.Log
import com.example.patientscard.BuildConfig
import com.example.patientscard.models.Medication
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.net.URL

class MedicationAsyncTask(val medications: ArrayList<Medication>): AsyncTask<String, String, JsonObject> () {
    override fun doInBackground(vararg params: String?): JsonObject {
        //http://localhost:8080/baseDstu3/Medication?_code=608680&_format=json&_pretty=true
        val url = URL("http://10.0.2.2:8080/baseDstu3/Medication?_format=json&_pretty=true")
        val gson = GsonBuilder().create()
        return gson.fromJson(url.readText(), JsonObject::class.java)
    }

    override fun onPostExecute(result: JsonObject?) {
        super.onPostExecute(result)
        if(result!!.has("entry")) {
            medications.clear()
            val entry = result.getAsJsonArray("entry")
            for(e in entry) {
                val medication = Medication.getMedicationFromJsonObject(e.asJsonObject.getAsJsonObject("resource"))
                medications.add(medication)
                if(BuildConfig.DEBUG) {
                    Log.d("MyLog:MedicationAT", "$medication")
                }
            }
        } else {
            Log.d("MyLog:MedicationAT", "$result")
        }
    }

}