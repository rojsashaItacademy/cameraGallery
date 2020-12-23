package ru.trinitydigital.cameraimage.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.trinitydigital.cameraimage.data.model.ProfileModel
import ru.trinitydigital.cameraimage.data.repositories.UserRepository
import ru.trinitydigital.cameraimage.utils.toImageRequestBody
import ru.trinitydigital.cameraimage.utils.toJsonRequestBody
import java.io.File

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    val userData = MutableLiveData<ProfileModel>()

    fun authUser() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val result = repository.login("rojsasha@gmail.com", "fifa11alex")
                if (result.isSuccessful)
                    loadUser()
            }.onFailure {
                it.message?.let { it1 -> Log.e("error", it1) }
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val result = repository.loadUserProfile()
                userData.postValue(result.body())
//                userData.value = result.body()
                Log.d("asdsadasd", "asdasdasdasd")
            }.onFailure {

            }
        }
    }

    fun updateUserWithPhoto(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val result = userData.value?.toJsonRequestBody()?.let { repository.updateUserWithImage(body = it, avatar = file.toImageRequestBody(AVATAR)) }
                Log.d("asdasdasdasd", "asdasdasdasdasd")
                if (result != null) {
                    if (result.isSuccessful)
                        userData.postValue(result.body())
                }
            }.onFailure {
                Log.d("asdasdasdasd", "asdasdasdasdasd")
            }
        }
    }

    companion object {
        private const val AVATAR = "avatar"
    }
}