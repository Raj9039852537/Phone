package org.fossify.phone.models

data class TrueCallerDataResponse (
    val name: String?,
    val image : String?,
    val addresses: List<TrueCallerAddress>?,
    val phones: List<TrueCallerPhones>?,
)
