package com.enesdokuz.gitrepoapp.ui.home.listener

import android.view.View

interface ListClickListener {
    fun onClickedItem(view: View, pos: Int)
    fun onClickedFavorite(pos: Int)
}