package com.app.pranavfreshworks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.pranavfreshworks.FreshWorksApp
import com.app.pranavfreshworks.R
import com.app.pranavfreshworks.databinding.RowGiphyBinding
import com.app.pranavfreshworks.model.Data
import com.app.pranavfreshworks.utils.ImageLoader

class FavGifAdapter(
    private val list: ArrayList<Data>,
    private val listener: UnFavClickListener
) :
    RecyclerView.Adapter<FavGifAdapter.ViewHolder>() {

    class ViewHolder(
        private val binder: RowGiphyBinding,
        private val listener: UnFavClickListener
    ) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(listData: Data) {

            binder.ivFav.playAnimation()

            ImageLoader.loadImage(
                FreshWorksApp.instance.getCurrentActivity(),
                binder.ivGif, listData.images.downsized_medium.url
            )

            binder.ivFav.setOnClickListener {
                listener.onUnFavClick(absoluteAdapterPosition, true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RowGiphyBinding>(
            inflater,
            R.layout.row_giphy,
            parent,
            false
        )
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface UnFavClickListener {
        fun onUnFavClick(position: Int, isFav: Boolean)
    }
}