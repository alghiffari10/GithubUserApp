package com.allcode.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allcode.githubuserapp.R
import com.allcode.githubuserapp.Utils.Companion.setImageGlide
import com.allcode.githubuserapp.databinding.ItemRowUserBinding
import com.allcode.githubuserapp.model.SimpleUser
import com.allcode.githubuserapp.model.User
import com.bumptech.glide.Glide

class UserAdapter(private val listUsers: List<SimpleUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallBack : OnItemClickCallback

    fun setOnClickCallback(onItemClickCallback: Any){
        this.onItemClickCallBack = onItemClickCallback as OnItemClickCallback
    }

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUsers[position]
        holder.binding.apply {
            tvUsername.text = user.login
            imageUser.setImageGlide(holder.itemView.context,user.avatarUrl)
        }

        holder.itemView.setOnClickListener { onItemClickCallBack.onItemClicked(user) }


    }
    override fun getItemCount(): Int = listUsers.size

    interface OnItemClickCallback{
        fun onItemClicked(user:SimpleUser)
    }

}