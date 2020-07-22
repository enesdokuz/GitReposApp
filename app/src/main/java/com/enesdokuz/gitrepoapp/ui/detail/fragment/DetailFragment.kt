package com.enesdokuz.gitrepoapp.ui.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.enesdokuz.gitrepoapp.R
import com.enesdokuz.gitrepoapp.model.Repo
import com.enesdokuz.gitrepoapp.ui.base.BaseFragment
import com.enesdokuz.gitrepoapp.ui.detail.viewmodel.DetailViewModel
import com.enesdokuz.gitrepoapp.ui.home.fragment.HomeFragmentArgs
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : BaseFragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by activityViewModels()
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeLiveData()
        arguments?.let {
            requireArguments().get("selectedRepo").let {
                viewModel.repo.value = it as Repo?
            }

        }
    }

    private fun observeLiveData() {
        viewModel.repo.observe(viewLifecycleOwner, Observer { repo ->
            repo?.let {
                txtOwnerName.text = repo.name
                txtIssues.text = "Open Issues: ${repo.openIssueCount}"
                txtStars.text = "Stars: ${repo.starCount}"
                txtOwnerName.text = repo.owner.username
                Glide.with(requireContext()).load(repo.owner.avatarUrl).into(imgAvatar)
            }

        })
    }

}
