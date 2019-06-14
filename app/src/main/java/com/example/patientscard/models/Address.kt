package com.example.patientscard.models

import com.google.gson.JsonObject

data class Address(
    var address: String,
    var city: String,
    var state: String,
    var postalCode: String,
    var country: String
) {

    companion object {
        fun getAddresFromJsonObject(addressObject: JsonObject): Address {
            val address = addressObject.getAsJsonArray("line")[0].asString
            return Address(
                address, addressObject.get("city").asString, addressObject.get("state").asString,
                addressObject.get("postalCode").asString, addressObject.get("country").asString
            )
        }
    }
}