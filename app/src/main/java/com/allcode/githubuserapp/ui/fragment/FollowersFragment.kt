package com.allcode.githubuserapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.allcode.githubuserapp.adapter.SectionPagerAdapter.Companion.ARGS_USERNAME
import com.allcode.githubuserapp.adapter.UserAdapter
import com.allcode.githubuserapp.databinding.FragmentFollowersBinding
import com.allcode.githubuserapp.model.SimpleUser
import com.allcode.githubuserapp.ui.activity.DetailUserActivity
import com.allcode.githubuserapp.viewmodel.FollowersViewModel


class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding

    private val followersViewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFollowersBinding.inflate(layoutInflater,container,false)

        followersViewModel.followers.observe(viewLifecycleOwner){followers ->
            if (followers == null){
                val username = arguments?.getString(ARGS_USERNAME) ?: ""
                followersViewModel.getUserFollowers(username)
            }else{
                showFollowers(followers)
            }
        }
        followersViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        return binding.root
    }
    private fun showFollowers(users: ArrayList<SimpleUser>) {
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