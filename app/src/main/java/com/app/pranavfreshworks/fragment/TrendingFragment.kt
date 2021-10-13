package com.app.pranavfreshworks.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.pranavfreshworks.R
import com.app.pranavfreshworks.adapter.GiphyAdapter
import com.app.pranavfreshworks.databinding.FragmentTrendingBinding
import com.app.pranavfreshworks.model.Data
import com.app.pranavfreshworks.model.GiphyResponseModel
import com.app.pranavfreshworks.utils.ApiUrl
import com.app.pranavfreshworks.utils.Constants
import com.app.pranavfreshworks.viewModel.GiphyViewModel
import io.paperdb.Paper

class TrendingFragment : BaseFragment(), GiphyAdapter.FavClickListener {

    private lateinit var binder: FragmentTrendingBinding
    private val viewModel by lazy { GiphyViewModel() }
    private var body = HashMap<String, String>()
    private var allList = ArrayList<Data>()

    private var isLoadMore = false
    private var isSearchQuery = false

    private var pageLimit = 20
    private var pageOffset = 0

    lateinit var mLayoutManager: GridLayoutManager
    private lateinit var adapter: GiphyAdapter
    private var searchKeyword = ""

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_trending
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder =
            DataBindingUtil.inflate(inflater, defineLayoutResource(), container, false)
        return binder.root
    }

    /**
     * initializeComponent which initialize required property and variables
     */
    override fun initializeComponent(view: View?) {

        initRecyclerView()
        setAdapter(allList)
        initScrollView()
        initSearchView()
        setUpViewModel()
        callTrendingApi(isProgress = true)
    }

    override fun onResume() {
        super.onResume()
        if (binder.rvList.adapter != null) {
            binder.rvList.adapter!!.notifyDataSetChanged()
        }
    }

    /**
     * initRecyclerView set it's layout manager
     */
    private fun initRecyclerView() {
        mLayoutManager = GridLayoutManager(requireContext(), 1)
        binder.rvList.layoutManager = mLayoutManager
    }

    /**
     * initScrollView check the last index of list if so call load more function to
     * load more data from service
     */
    private fun initScrollView() {

        binder.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoadMore) {
                    if (allList.size > 0) {
                        if (mLayoutManager.findLastCompletelyVisibleItemPosition() == allList.size - 1) {
                            loadMoreData()
                        }
                    }
                }
            }
        })
    }

    /**
     * initSearchView check for search gif and call it's corresponding method
     */
    private fun initSearchView() {
        binder.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQuery: String): Boolean {
                binder.searchView.clearFocus()

                allList.clear()
                isSearchQuery = true
                pageOffset = 0
                searchKeyword = searchQuery
                callSearchApi(searchQuery, isShowProgress = true)

                return false
            }

            override fun onQueryTextChange(searchQuery: String): Boolean {
                if (searchQuery.isEmpty()) {
                    allList.clear()
                    searchKeyword = ""
                    isSearchQuery = false
                    pageOffset = 0
                    callTrendingApi(isProgress = true)
                }
                return false
            }
        })

        binder.searchView.setOnCloseListener {
            true
        }
    }

    /**
     * setAdapter pass the list of gif data to set in recyclerview
     */
    private fun setAdapter(list: ArrayList<Data>) {
        adapter = GiphyAdapter(list, this)
        binder.rvList.adapter = adapter
    }

    /**
     * setUpViewModel basic setup for viewmodel
     * here using one response for both search and trending gif and handle accordingly
     */
    private fun setUpViewModel() {
        setUpBaseViewModel(viewModel)
        viewModel.liveDataGiphyResponse.observe(this, giphyResponse)
        viewModel.liveDataSearchGiphyResponse.observe(this, giphyResponse)
    }

    /**
     * callTrendingApi pass the isProgress param depends on to show progress or not
     * if it's load more then do not need to show so pass false here
     * and use other value like api key, limit and offset
     */
    private fun callTrendingApi(isProgress: Boolean) {
        body = HashMap()
        body[ApiUrl.API_KEY] = Constants.API_KEY
        body[ApiUrl.LIMIT] = pageLimit.toString()
        body[ApiUrl.OFFSET] = pageOffset.toString()

        viewModel.getTrendingList(body, isProgress)
    }


    /**
     * callSearchApi pass the isProgress param depends on to show progress or not
     * if it's load more then do not need to show so pass false here
     * and use other value like api key, limit and offset and it will return search based on
     * search text
     */
    private fun callSearchApi(search: String, isShowProgress: Boolean) {
        body = HashMap()
        body[ApiUrl.API_KEY] = Constants.API_KEY
        body[ApiUrl.LIMIT] = pageLimit.toString()
        body[ApiUrl.OFFSET] = pageOffset.toString()
        body[ApiUrl.SEARCH] = search

        viewModel.searchList(body, isShowProgress)
    }

    /**
     * Here only using single giphyResponse which handle both trending response as well as
     * Search list result
     * Based on API call it will set span count to 1 and 2 accordingly
     */
    private val giphyResponse = Observer<GiphyResponseModel> {
        if (it.data.isNotEmpty()) {
            if (isSearchQuery) {
                mLayoutManager.spanCount = 1
            } else {
                mLayoutManager.spanCount = 2
            }

            allList.addAll(allList.size, it.data)
            binder.rvList.adapter!!.notifyDataSetChanged()

            isLoadMore = false

            binder.progressBar.visibility = View.GONE
        }
    }

    /**
     * loadMoreData function to provide load more data functionality
     */
    private fun loadMoreData() {
        isLoadMore = true
        binder.progressBar.visibility = View.VISIBLE
        pageOffset += pageLimit

        if (isSearchQuery) {
            callSearchApi(searchKeyword, isShowProgress = false)
        } else {
            callTrendingApi(isProgress = false)
        }
    }

    /**
     * Here pass 2 params
     * position : the position which is selected for fav or unFav
     * isFav : boolean value for fav or not
     */
    override fun onFavClick(position: Int, isFav: Boolean) {

        val favListData = Paper.book(Constants.PAPER_BOOK_USER)
            .read(Constants.PAPER_KEY_FAV_LIST, ArrayList<Data>())

        if (favListData != null) {
            if (isFav) {
                favListData.add(allList[position])

                Paper.book(Constants.PAPER_BOOK_USER)
                    .write(Constants.PAPER_KEY_FAV_LIST, favListData)
            } else {
                favListData.remove(allList[position])
                Paper.book(Constants.PAPER_BOOK_USER)
                    .write(Constants.PAPER_KEY_FAV_LIST, favListData)
            }
        }

        binder.rvList.adapter!!.notifyDataSetChanged()
    }
}