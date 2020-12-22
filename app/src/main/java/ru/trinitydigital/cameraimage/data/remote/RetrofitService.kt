package ru.trinitydigital.cameraimage.data.remote

import retrofit2.Response
import retrofit2.http.*
import ru.trinitydigital.cameraimage.data.model.AuthModel
import ru.trinitydigital.cameraimage.data.model.ProfileModel
import ru.trinitydigital.cameraimage.data.model.TokenModel

interface RetrofitService {

    @POST("api/v1/users/auth")
    suspend fun login(@Body data: Map<String, String>): Response<TokenModel>

    @POST("api/v1/users/auth")
    suspend fun loginDataClass(@Body data: AuthModel): Response<TokenModel>

    @GET("api/v1/users/profile")
    suspend fun loadUserProfile(): Response<ProfileModel?>
}