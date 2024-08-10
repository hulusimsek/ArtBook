package com.hulusimsek.a3_artbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import com.hulusimsek.a3_artbook.model.ImageResponse
import com.hulusimsek.a3_artbook.repo.ArtRepository
import com.hulusimsek.a3_artbook.repo.ArtRepositoryInterface
import com.hulusimsek.a3_artbook.roomdb.Art
import com.hulusimsek.a3_artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
        private val repository: ArtRepositoryInterface
): ViewModel() {
    //Art Fragment
    val artList = repository.getArt()

    //Image API Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
        get() = selectedImage

    // Art Details Fragment

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }
    fun setSelectedImage(url : String) {
        selectedImage.postValue(url)
    }
    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art: Art ) = viewModelScope.launch {
        repository.insertArt(art)
    }
    fun makeArt(name: String, artistName: String, year: String) {
        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("Enter name",null))
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("Year should be number", null))
            return
        }
        val art = Art(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        insertArtMsg.postValue(Resource.success(art))


    }
    fun searchForImage(searchString: String) {
        if(searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
            setSelectedImage("")
        }
    }

}