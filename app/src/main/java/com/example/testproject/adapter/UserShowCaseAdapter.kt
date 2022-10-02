package com.example.testproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testproject.data.Data
import com.example.testproject.databinding.ItemUsersCardBinding

class UserShowCaseAdapter(
    val onClick: (Data) -> Unit
): RecyclerView.Adapter<UserShowCaseAdapter.UserViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            ItemUsersCardBinding
                .inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(
        holder: UserViewHolder, position: Int
    ) =holder.bind(differ.currentList[position])

    override fun getItemCount(): Int  = differ.currentList.size


    inner class UserViewHolder(
        val binding: ItemUsersCardBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data){
            var userImage = data.avatar.toString()
            binding.userImg.load(userImage)
            binding.userDetailConstLayout.setOnClickListener {
                onClick(data)
            }
            binding.userFirstName.text = data.first_name.toString()
            binding.userLastName.text = data.last_name.toString()
        }

    }
}