package com.app.pranavfreshworks.activity

import androidx.viewpager2.widget.ViewPager2
import com.app.pranavfreshworks.R
import com.app.pranavfreshworks.adapter.HomeTabAdapter
import com.app.pranavfreshworks.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : BaseActivity() {

    private lateinit var binder: ActivityHomeBinding

    private val tabNames = arrayOf(
        "Trending",
        "Favourite"
    )

    override fun defineLayoutResource(): Int {
        return R.layout.activity_home
    }

    override fun initializeComponents() {

        binder = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binder.root)

        window.decorView.systemUiVisibility = 0
        setUpTabLayout()
    }

    private fun setUpTabLayout() {

        binder.viewPager.adapter = HomeTabAdapter(this, tabNames.size)
        binder.viewPager.currentItem = 0

        binder.viewPager.registerOnPageChangeCallback(pageChangeCallback)

        TabLayoutMediator(
            binder.tabLayout, binder.viewPager
        ) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        binder.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binder.viewPager.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private val pageChangeCallback: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binder.viewPager.currentItem = position
            }
        }
}