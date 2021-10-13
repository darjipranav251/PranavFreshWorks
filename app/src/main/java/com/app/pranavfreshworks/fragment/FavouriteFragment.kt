package com.app.pranavfreshworks.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.pranavfreshworks.R
import com.app.pranavfreshworks.adapter.FavGifAdapter
import com.app.pranavfreshworks.databinding.FragmentFavouriteBinding
import com.app.pranavfreshworks.model.Data
import com.app.pranavfreshworks.utils.Constants
import io.paperdb.Paper

class FavouriteFragment : BaseFragment(), FavGifAdapter.UnFavClickListener {

    private lateinit var binder: FragmentFavouriteBinding
    private var favListData = ArrayList<Data>()

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_favourite
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder =
            DataBindingUtil.inflate(inflater, defineLayoutResource(), container, false)
        return binder.root
    }

    override fun initializeComponent(view: View?) {
    }

    override fun onResume() {
        super.onResume()

        initializeData()
        setAdapter(favListData)
    }

    private fun initializeData() {
        favListData = Paper.book(Constants.PAPER_BOOK_USER)
            .read(Constants.PAPER_KEY_FAV_LIST, ArrayList<Data>())

        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binder.rvList.layoutManager = mLayoutManager
    }

    private fun setAdapter(list: ArrayList<Data>) {
        val adapter = FavGifAdapter(list, this)
        binder.rvList.adapter = adapter
    }

    override fun onUnFavClick(position: Int, isFav: Boolean) {
        if (favListData.size > 0) {
            favListData.remove(favListData[position])
            Paper.book(Constants.PAPER_BOOK_USER)
                .write(Constants.PAPER_KEY_FAV_LIST, favListData)
            binder.rvList.adapter!!.notifyDataSetChanged()
        }
    }
}