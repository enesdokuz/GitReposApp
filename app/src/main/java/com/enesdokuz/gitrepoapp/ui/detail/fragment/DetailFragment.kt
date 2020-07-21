package com.enesdokuz.gitrepoapp.ui.detail.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.enesdokuz.gitrepoapp.R
import com.enesdokuz.gitrepoapp.ui.base.BaseFragment
import com.enesdokuz.gitrepoapp.ui.detail.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.item_repo.*

class DetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //txtNameItem.text = arguments
    }

}
