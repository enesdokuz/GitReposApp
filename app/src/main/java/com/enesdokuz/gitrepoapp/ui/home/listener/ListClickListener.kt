package com.enesdokuz.gitrepoapp.ui.home.listener

import android.view.View
import com.enesdokuz.gitrepoapp.model.Repo

interface ListClickListener {
    fun onClickedItem(selectedRepo: Repo)
    fun onClickedFavorite(pos: Int,id: String,isFavorite: Boolean)
}