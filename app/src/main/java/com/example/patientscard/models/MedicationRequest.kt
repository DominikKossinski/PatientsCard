package com.example.patientscard.models

import com.example.patientscard.activities.MainActivity
import com.google.gson.JsonObject
import java.util.*

data class MedicationRequest(
    var id: String,
    var date: Date,
    var text: String,
    var version: String,
    var mediication: Medication
):Comparable<MedicationRequest> {
    override fun compareTo(other: MedicationRequest): Int {
        return when {
            this.date.time > other.date.time -> 1
            this.date.time == other.date.time -> 0
            else -> -1
        }
    }

    companion object {
        fun getMedicationRequestFromJsonObject(medicationRequestObject: JsonObject) : MedicationRequest {
            val id = medicationRequestObject.get("id").asString

            val meta = medicationRequestObject.getAsJsonObject("meta")
            val version = meta.get("versionId").asString
            val date = MainActivity.isoSSSDateFormat.parse(meta.get("lastUpdated").asString)

            val extention = medicationRequestObject.getAsJsonArray("extension")[0].asJsonObject
            val valueCodeableConcept = extention.getAsJsonObject("valueCodeableConcept")
            val text = valueCodeableConcept.get("text").asString

            val medicationCodeableConcept = medicationRequestObject.getAsJsonObject("medicationCodeableConcept")
            val coding = medicationCodeableConcept.getAsJsonArray("coding")[0].asJsonObject
            val code = coding.get("code").asString
            val mText = medicationCodeableConcept.get("text").asString
            val medication = Medication(null, code, mText, null)

            return MedicationRequest(id, date, text, version, medication)

        }
    }
}