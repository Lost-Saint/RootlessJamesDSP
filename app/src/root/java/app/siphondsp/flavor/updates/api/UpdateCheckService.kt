package app.siphondsp.flavor.updates.api

import app.siphondsp.flavor.updates.model.UpdateCheckResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface UpdateCheckService {
    @GET("updates/check/siphondsp/{flavor}/{versionCode}")
    fun checkUpdate(@Path("flavor") flavor: String, @Path("versionCode") versionCode: String): Call<UpdateCheckResponse>

    @Streaming
    @GET("updates/download/siphondsp/{flavor}")
    suspend fun downloadUpdate(@Path("flavor") flavor: String): ResponseBody
}