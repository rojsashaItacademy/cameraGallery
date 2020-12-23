package ru.trinitydigital.cameraimage.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.trinitydigital.cameraimage.data.model.ProfileModel
import ru.trinitydigital.cameraimage.data.model.TokenModel
import ru.trinitydigital.cameraimage.data.repositories.UserRepository

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
                Log.d("asdsadasd", "asdasdasdasd")
            }.onFailure {

            }
        }
    }
}