package com.allcode.githubuserapp.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.allcode.githubuserapp.R
import com.allcode.githubuserapp.adapter.UserAdapter
import com.allcode.githubuserapp.databinding.ActivityMainBinding
import com.allcode.githubuserapp.model.SimpleUser
import com.allcode.githubuserapp.ui.activity.DetailUserActivity.Companion.EXTRA_DETAIL
import com.allcode.githubuserapp.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"

    }



    private lateinit var binding :ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel.simpleUser.observe(this){
            showSearchingResult(it)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.isError.observe(this){ error ->
            if (error) errorOccurred()
        }

    }

    private fun errorOccurred() {
        Toast.makeText(this@MainActivity, "An Error is Occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query ?: "")
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
    private fun showSearchingResult(user: ArrayList<SimpleUser>) {

        val listUserAdapter = UserAdapter(user)

        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listUserAdapter
            setHasFixedSize(true)
        }

        listUserAdapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(user : SimpleUser) {
                goToDetail(user)
            }
        })


    }

    private fun goToDetail(user : SimpleUser) {
        Intent(this@MainActivity,DetailUserActivity::class.java).apply {
            putExtra(EXTRA_DETAIL,user.login)
        }.also {
            startActivity(it)
        }

    }


}


