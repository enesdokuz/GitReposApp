package com.enesdokuz.gitrepoapp.ui.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enesdokuz.gitrepoapp.model.Repo

class DetailViewModel : ViewModel() {

    val repo = MutableLiveData<Repo>()
}
