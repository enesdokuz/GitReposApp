package com.enesdokuz.gitrepoapp.ui.detail.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.enesdokuz.gitrepoapp.model.Repo
import com.enesdokuz.gitrepoapp.repository.room.RepoDatabase
import com.enesdokuz.gitrepoapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val repo = MutableLiveData<Repo>()
    private val repoDao = RepoDatabase(context = getApplication()).repoDao()

    fun setFavoriteItem(isFavorite: Boolean, itemId: String) {
        launch {
            repoDao.updateRepo(isFavorite = isFavorite, id = itemId)
            repo.value?.copy(isFavorite = isFavorite)
        }
    }
}
