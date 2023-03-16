package com.allcode.githubuserapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.allcode.githubuserapp.R
import com.allcode.githubuserapp.Utils.Companion.setAndVisible
import com.allcode.githubuserapp.Utils.Companion.setImageGlide
import com.allcode.githubuserapp.adapter.SectionPagerAdapter
import com.allcode.githubuserapp.databinding.ActivityDetailUserBinding
import com.allcode.githubuserapp.model.User
import com.allcode.githubuserapp.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
    private var username: String? = null
    private var profileUrl: String? = null

    private val detailViewModel by viewModels<DetailViewModel>()

    private lateinit var binding : ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        username = intent.extras?.getString(EXTRA_DETAIL)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }



        val sectionPagerAdapter = SectionPagerAdapter(this,username!!)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs,binding.viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()

        detailViewModel.user.observe(this){
            if(it != null){
                parseUserDetail(it)
            }

        }
        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }
        detailViewModel.isError.observe(this){
            if (it) errorOccurred()
        }
        detailViewModel.callCounter.observe(this){ counter ->
            if (counter < 1) detailViewModel.getUserDetail(username!!)

        }


    }

    private fun errorOccurred() {
        Toast.makeText(this@DetailUserActivity, "An Error is Occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.loading.visibility = View.VISIBLE
        else binding.loading.visibility = View.GONE
    }

    private fun parseUserDetail(user: User) {
        binding.apply {
            tvUsername.text = user.login
            tvName.text = user.name
            numberOfFollower.text = user.followers.toString()
            numberOfFollowing.text = user.following.toString()
            numberOfRepository.text = user.publicRepos.toString()

            tvCompany.setAndVisible(user.company)
            imageDetail.setImageGlide(this@DetailUserActivity,user.avatarUrl)


        }
    }

}