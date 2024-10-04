package com.kiki.hellocompose.data.response

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("statuscode")
    val statuscode: Int,
    @SerializedName("copyright")
    val copyright: Copyright,
    @SerializedName("messages")
    val messages: List<String>
)

data class Copyright(
    @SerializedName("text")
    val text: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("imageAltText")
    val imageAltText: String
)

data class Options(
    @SerializedName("maxResults")
    val maxResults: Int,
    @SerializedName("thumbMaps")
    val thumbMaps: Boolean,
    @SerializedName("ignoreLatLngInput")
    val ignoreLatLngInput: Boolean
)

data class Location(
    @SerializedName("street")
    val street: String?,
    @SerializedName("adminArea6")
    val adminArea6: String?,
    @SerializedName("adminArea6Type")
    val adminArea6Type: String?,
    @SerializedName("adminArea5")
    val adminArea5: String?,
    @SerializedName("adminArea5Type")
    val adminArea5Type: String?,
    @SerializedName("adminArea4")
    val adminArea4: String?,
    @SerializedName("adminArea4Type")
    val adminArea4Type: String?,
    @SerializedName("adminArea3")
    val adminArea3: String?,
    @SerializedName("adminArea3Type")
    val adminArea3Type: String?,
    @SerializedName("adminArea1")
    val adminArea1: String?,
    @SerializedName("adminArea1Type")
    val adminArea1Type: String?,
    @SerializedName("postalCode")
    val postalCode: String?,
    @SerializedName("geocodeQualityCode")
    val geocodeQualityCode: String?,
    @SerializedName("geocodeQuality")
    val geocodeQuality: String?,
    @SerializedName("dragPoint")
    val dragPoint: Boolean,
    @SerializedName("sideOfStreet")
    val sideOfStreet: String?,
    @SerializedName("linkId")
    val linkId: String?,
    @SerializedName("unknownInput")
    val unknownInput: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("latLng")
    val latLng: LatLng?,
    @SerializedName("displayLatLng")
    val displayLatLng: LatLng?,
    @SerializedName("mapUrl")
    val mapUrl: String?
)

data class LatLng(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class Result(
    @SerializedName("providedLocation")
    val providedLocation: ProvidedLocation,
    @SerializedName("locations")
    val locations: List<Location>
)

data class ProvidedLocation(
    @SerializedName("location")
    val location: String
)

data class AddressResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("options")
    val options: Options,
    @SerializedName("results")
    val results: List<Result>
)