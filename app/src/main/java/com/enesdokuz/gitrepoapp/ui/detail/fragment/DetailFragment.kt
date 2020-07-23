package com.enesdokuz.gitrepoapp.ui.detail.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.enesdokuz.gitrepoapp.R
import com.enesdokuz.gitrepoapp.model.Repo
import com.enesdokuz.gitrepoapp.ui.base.BaseFragment
import com.enesdokuz.gitrepoapp.ui.detail.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_fragment.*

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

        observeLiveData()
        arguments?.let {
            requireArguments().get("selectedRepo").let {
                viewModel.repo.value = it as Repo?
            }

        }
        setHasOptionsMenu(true)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun observeLiveData() {
        viewModel.repo.observe(viewLifecycleOwner, Observer { repo ->
            repo?.let {
                requireActivity().toolbar.title = repo.name
                txtOwnerName.text = repo.owner.username
                txtIssues.text = "Open Issues: ${repo.openIssueCount}"
                txtStars.text = "Stars: ${repo.starCount}"
                txtOwnerName.text = repo.owner.username
                Glide.with(requireContext()).load(repo.owner.avatarUrl).into(imgAvatar)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        viewModel.repo.value?.let {
            if (it.isFavorite)
                menu.findItem(R.id.item_favorite).setIcon(R.drawable.ic_star_white)
            else
                menu.findItem(R.id.item_favorite).setIcon(R.drawable.ic_star_black)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_favorite -> {
                viewModel.repo.value?.let {
                    viewModel.setFavoriteItem(itemId = it.id, isFavorite = !it.isFavorite)
                    if (it.isFavorite)
                        item.setIcon(R.drawable.ic_star_black)
                    else
                        item.setIcon(R.drawable.ic_star_white)
                }
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }


}
