package com.app.pranavfreshworks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.pranavfreshworks.FreshWorksApp
import com.app.pranavfreshworks.R
import com.app.pranavfreshworks.databinding.RowGiphyBinding
import com.app.pranavfreshworks.model.Data
import com.app.pranavfreshworks.utils.Constants
import com.app.pranavfreshworks.utils.ImageLoader
import io.paperdb.Paper

class GiphyAdapter(
    private val list: ArrayList<Data>,
    private val listener: FavClickListener
) :
    RecyclerView.Adapter<GiphyAdapter.ViewHolder>() {

    class ViewHolder(
        private val binder: RowGiphyBinding,
        private val listener: FavClickListener
    ) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(listData: Data) {

            val favList = Paper.book(Constants.PAPER_BOOK_USER)
                .read(Constants.PAPER_KEY_FAV_LIST, ArrayList<Data>())

            if (favList.size > 0) {
                for (i in 0 until favList.size) {
                    if (favList[i].id == listData.id) {
                        binder.ivFav.playAnimation()
                        break
                    } else {
                        binder.ivFav.cancelAnimation()
                        binder.ivFav.progress = 0.0f
                    }
                }
            }

            ImageLoader.loadImage(
                FreshWorksApp.instance.getCurrentActivity(),
                binder.ivGif, listData.images.downsized_medium.url
            )

            binder.ivFav.setOnClickListener {
                if (favList.size > 0) {
                    var isMatch = false
                    for (i in 0 until favList.size) {
                        if (favList[i].id == listData.id) {
                            listener.onFavClick(absoluteAdapterPosition, false)
                            isMatch = true
                            break
                        }
                    }
                    if (!isMatch) {
                        listener.onFavClick(absoluteAdapterPosition, true)
                    }
                } else {
                    listener.onFavClick(absoluteAdapterPosition, true)
                }
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

    interface FavClickListener {
        fun onFavClick(position: Int, isFav: Boolean)
    }
}