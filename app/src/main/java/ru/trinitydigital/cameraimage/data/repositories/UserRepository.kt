package ru.trinitydigital.cameraimage.data.repositories

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import ru.trinitydigital.cameraimage.data.interactors.UserInteractor
import ru.trinitydigital.cameraimage.data.local.PrefsHelper
import ru.trinitydigital.cameraimage.data.model.ProfileModel
import ru.trinitydigital.cameraimage.data.model.TokenModel

interface UserRepository {
    suspend fun login(userName: String, password: String): Response<TokenModel>
    suspend fun loadUserProfile(): Response<ProfileModel?>
    suspend fun updateUserWithImage(
        body: RequestBody,
        avatar: MultipartBody.Part
    ): Response<ProfileModel?>
}

class UserRepositoryImpl(private val network: UserInteractor) : UserRepository {

    override suspend fun login(userName: String, password: String): Response<TokenModel> {
        val result = network.login(userName, password)
        result.body()?.token?.let { PrefsHelper.saveToken(it) }

        return result
    }

    override suspend fun loadUserProfile(): Response<ProfileModel?> {
        return network.loadUserProfile()
    }

    override suspend fun updateUserWithImage(
        body: RequestBody,
        avatar: MultipartBody.Part
    ): Response<ProfileModel?> {
        return network.updateUserWithImage(
            body = body, avatar = avatar
        )
    }
}