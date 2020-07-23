package com.enesdokuz.gitrepoapp.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesdokuz.gitrepoapp.R
import com.enesdokuz.gitrepoapp.model.Repo
import com.enesdokuz.gitrepoapp.ui.base.BaseFragment
import com.enesdokuz.gitrepoapp.ui.home.adapter.RepoAdapter
import com.enesdokuz.gitrepoapp.ui.home.listener.ListClickListener
import com.enesdokuz.gitrepoapp.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment(), ListClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by activityViewModels()
    private val repoAdapter = RepoAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUI()
        observeLiveData()
        initController()
        viewModel.getReposFromSqLite()
    }

    private fun initUI() {
        requireActivity().toolbar.title = "Home"
        viewModel.isLoading.value = false
        recyclerHome.setHasFixedSize(true)
        recyclerHome.isNestedScrollingEnabled = false
        recyclerHome.layoutManager = LinearLayoutManager(context)
        recyclerHome.adapter = repoAdapter
    }

    private fun observeLiveData() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (isLoading) {
                    progressHome.visibility = View.VISIBLE
                } else {
                    progressHome.visibility = View.GONE
                }
            }
        })

        viewModel.hasError.observe(viewLifecycleOwner, Observer { hasError ->
            hasError?.let {
                if (hasError) {
                    txtError.visibility = View.VISIBLE
                } else
                    txtError.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                txtError.text = errorMessage
            }
        })

        viewModel.repos.observe(viewLifecycleOwner, Observer { repoList ->
            repoAdapter.update(repoList)
        })

    }

    private fun initController() {
        btnGetRepos.setOnClickListener {
            viewModel.getUserRepos(editQuery.text.toString())
        }
    }

    override fun onClickedItem(selectedRepo: Repo) {
        val bundle = bundleOf("selectedRepo" to selectedRepo)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun onClickedFavorite(pos: Int, id: String, isFavorite: Boolean) {
        repoAdapter.setFavorite(pos)
        viewModel.setFavoriteItem(isFavorite = isFavorite,itemId = id)
    }

}
