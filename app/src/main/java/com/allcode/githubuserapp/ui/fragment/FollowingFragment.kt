package com.allcode.githubuserapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.allcode.githubuserapp.R
import com.allcode.githubuserapp.adapter.SectionPagerAdapter
import com.allcode.githubuserapp.adapter.UserAdapter
import com.allcode.githubuserapp.databinding.FragmentFollowingBinding
import com.allcode.githubuserapp.model.SimpleUser
import com.allcode.githubuserapp.ui.activity.DetailUserActivity
import com.allcode.githubuserapp.viewmodel.FollowingViewModel


class FollowingFragment : Fragment() {

    private lateinit var binding : FragmentFollowingBinding
    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater,container,false)
        followingViewModel.following.observe(viewLifecycleOwner){following->
            if (following == null){
                val username = arguments?.getString(SectionPagerAdapter.ARGS_USERNAME) ?: ""
                followingViewModel.getFollowing(username)
            }else{
                showFollowing(following)
            }

        }
        followingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        return binding.root
    }

    private fun showFollowing(users: ArrayList<SimpleUser>) {
        if (users.size > 0) {
            val linearLayoutManager = LinearLayoutManager(activity)
            val listAdapter = UserAdapter(users)

            binding.rvUser.apply {
                layoutManager = linearLayoutManager
                adapter = listAdapter
                setHasFixedSize(true)
            }

            listAdapter.setOnClickCallback(object :
                UserAdapter.OnItemClickCallback {
                override fun onItemClicked(user: SimpleUser) {
                    goToDetailUser(user)
                }

            })
        } else binding.tvStatus.visibility = View.VISIBLE
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.loading.visibility = View.VISIBLE
        else binding.loading.visibility = View.GONE
    }
    private fun goToDetailUser(user: SimpleUser) {
        Intent(activity, DetailUserActivity::class.java).apply {
            putExtra(DetailUserActivity.EXTRA_DETAIL, user.login)
        }.also {
            startActivity(it)
        }
    }




}