package com.mehmetpetek.satellitedemo.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mehmetpetek.satellitedemo.common.Constant
import com.mehmetpetek.satellitedemo.data.Resource
import com.mehmetpetek.satellitedemo.data.local.model.PositionList
import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail
import com.mehmetpetek.satellitedemo.domain.repository.SatelliteRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatelliteRepositoryImp @Inject constructor(
    private val context: Context
) : SatelliteRepository {

    override fun getAllSatellite(): Flow<Resource<List<Satellite>?>> =
        callbackFlow {
            try {
                val jsonFileString = getJsonDataFromAsset(context, Constant.SATELLITE_LIST)
                val listPersonType = object : TypeToken<List<Satellite>>() {}.type
                val gson = Gson()
                val list: List<Satellite>? = gson.fromJson(jsonFileString, listPersonType)
                trySend(Resource.Success(list))
            } catch (ex: java.lang.Exception) {
                trySend(Resource.Error(ex))
            }
            awaitClose { cancel() }
        }

    override fun getSatelliteDetail(): Flow<Resource<List<SatelliteDetail>?>> =
        callbackFlow {
            try {
                val jsonFileString = getJsonDataFromAsset(context, Constant.SATELLITE_DETAIL)
                val listPersonType = object : TypeToken<List<SatelliteDetail>>() {}.type
                val gson = Gson()
                val list: List<SatelliteDetail>? = gson.fromJson(jsonFileString, listPersonType)
                trySend(Resource.Success(list))
            } catch (ex: java.lang.Exception) {
                trySend(Resource.Error(ex))
            }
            awaitClose { cancel() }
        }

    override fun getAllPosition(): Flow<Resource<PositionList?>> =
        callbackFlow {
            try {
                val jsonFileString = getJsonDataFromAsset(context, Constant.POSITION)
                val listPersonType = object : TypeToken<PositionList>() {}.type
                val gson = Gson()
                val list: PositionList? = gson.fromJson(jsonFileString, listPersonType)
                trySend(Resource.Success(list))
            } catch (ex: java.lang.Exception) {
                trySend(Resource.Error(ex))
            }
            awaitClose { cancel() }
        }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}