package com.example.patientscard.asynctasks

import android.os.AsyncTask
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.example.patientscard.BuildConfig
import com.example.patientscard.models.MedicationRequest
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.net.URL
import java.util.concurrent.Semaphore

class MedicationRequestAsyncTask(val id:String, val medicationRequests:ArrayList<MedicationRequest>, val swipeRefreshLayout: SwipeRefreshLayout):AsyncTask<String, String, JsonObject>() {
    override fun doInBackground(vararg params: String?): JsonObject {
        val url = URL("http://10.0.2.2:8080/baseDstu3/MedicationRequest?patient=$id&_format=json&_pretty=true")
        val gson = GsonBuilder().create()
        return gson.fromJson(url.readText(), JsonObject::class.java)

    }

    override fun onPostExecute(result: JsonObject?) {
        super.onPostExecute(result)
        if(result!!.has("entry")) {
            medicationRequests.clear()
            val entry = result.getAsJsonArray("entry")
            for(resource in entry) {
                val mRequest = resource.asJsonObject.getAsJsonObject("resource")
                val medicationRequest = MedicationRequest.getMedicationRequestFromJsonObject(mRequest)
                if(BuildConfig.DEBUG) {
                    Log.d("MyLog", "$medicationRequest")
                }
                medicationRequests.add(medicationRequest)
            }
        }

    }
}