package com.example.patientscard.models

import com.example.patientscard.activities.MainActivity
import com.google.gson.JsonObject
import java.util.*

data class Patient(
    var id: String,
    var firstName: String,
    var lastName: String,
    var telephone: String,
    var birthDate: Date,
    var gender: String,
    var address: Address,
    var version:String
) {

    companion object {
        fun getPatientFromJsonObject(patientObject: JsonObject): Patient {

            val address = Address.getAddresFromJsonObject(patientObject.getAsJsonArray("address")[0].asJsonObject)

            val meta = patientObject.getAsJsonObject("meta")
            val version = meta.get("versionId").asString
            val id = patientObject.get("id").asString

            val nameObject = patientObject.getAsJsonArray("name")[0].asJsonObject
            val firstName = nameObject.getAsJsonArray("given")[0].asString
            val lastName = nameObject.get("family").asString

            val telecomObject = patientObject.getAsJsonArray("telecom")[0].asJsonObject
            val telephone = telecomObject.get("value").asString

            val birthDate = MainActivity.simpleDateFormat.parse(patientObject.get("birthDate").asString)
            val gender = patientObject.get("gender").asString
            return Patient(
                id,
                firstName,
                lastName,
                telephone,
                birthDate,
                gender,
                address,
                version
            )
        }
    }
}