package com.enesdokuz.gitrepoapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enesdokuz.gitrepoapp.R
import com.enesdokuz.gitrepoapp.model.Repo
import com.enesdokuz.gitrepoapp.ui.home.listener.ListClickListener
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter(private val list: ArrayList<Repo>, val listener: ListClickListener) :
    RecyclerView.Adapter<RepoAdapter.Holder>() {

    class Holder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_repo, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val context = holder.view.context
        holder.view.txtNameItem.text = list[position].name
        if (list[position].isFavorite) {
            Glide.with(context).load(context.getDrawable(R.drawable.ic_star_black))
                .into(holder.view.imgFavoriteItem)
        } else {
            Glide.with(context).load(context.getDrawable(R.drawable.ic_star_white))
                .into(holder.view.imgFavoriteItem)
        }

        holder.view.setOnClickListener {
            listener.onClickedItem(list[position])
        }

        holder.view.imgFavoriteItem.setOnClickListener {
            listener.onClickedFavorite(position, list[position].id, !list[position].isFavorite)
        }

    }

    fun update(newList: List<Repo>) {
        this.list.clear()
        this.list.addAll(newList)
        notifyDataSetChanged()
    }

    fun setFavorite(pos: Int) {
        list[pos].isFavorite = !list[pos].isFavorite
        notifyItemChanged(pos)
    }

}