package ru.trinitydigital.cameraimage.data.interactors

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import ru.trinitydigital.cameraimage.data.model.ProfileModel
import ru.trinitydigital.cameraimage.data.model.TokenModel
import ru.trinitydigital.cameraimage.data.remote.RetrofitService

interface UserInteractor {

    suspend fun login(userName: String, password: String): Response<TokenModel>
    suspend fun loadUserProfile(): Response<ProfileModel?>
    suspend fun updateUserWithImage(
        body: RequestBody,
        avatar: MultipartBody.Part
    ): Response<ProfileModel?>
}

class UserInteractorImpl(private val service: RetrofitService) : UserInteractor {

    override suspend fun login(userName: String, password: String): Response<TokenModel> {
        val map = mapOf(
            "email" to userName,
            "password" to password
        )

        return service.login(map)
    }

    override suspend fun loadUserProfile(): Response<ProfileModel?> {
        return service.loadUserProfile()
    }

    override suspend fun updateUserWithImage(
        body: RequestBody,
        avatar: MultipartBody.Part
    ): Response<ProfileModel?> {
        return service.updateUserWithImage(body = body, avatar = avatar)
    }

}