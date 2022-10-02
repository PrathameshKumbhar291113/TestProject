package com.example.testproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testproject.adapter.UserShowCaseAdapter
import com.example.testproject.api.ApiService
import com.example.testproject.data.Data
import com.example.testproject.databinding.ActivityInboxBinding
import kotlinx.coroutines.launch
import splitties.activities.start
import splitties.snackbar.snack
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class InboxActivity : AppCompatActivity() {
    private lateinit var adapter : UserShowCaseAdapter
    private lateinit var binding: ActivityInboxBinding
    private var dataList: List<Data> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityInboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        binding.userRecyclerView.adapter = adapter //Initialization OF adapter

        lifecycleScope.launch{
            getUsers()
        }

        binding.nextButton.setOnClickListener {
            binding.pageShowTextView.text = "Page 2/2"
            lifecycleScope.launch{
                getNextPage()
            }
        }

        binding.previousButton.setOnClickListener {
            binding.pageShowTextView.text = "Page 1/2"
            lifecycleScope.launch{
                getPrevPage()
            }
        }

        binding.searchBtn.setOnClickListener {
            searchedText()
        }

    }

    private fun searchedText() {
        val inputSearchText = binding.searchBar.text.toString().trim().lowercase()
        var newDataList : ArrayList<Data> = ArrayList()

       dataList.forEach{data ->
           if (inputSearchText.contains(data.first_name!!.lowercase()) ||
               inputSearchText.contains(data.last_name!!.lowercase())
               ){
               newDataList.add(data)
           }
       }
        if (newDataList.isEmpty()){
            Toast.makeText(this,"NO USER FOUND",Toast.LENGTH_SHORT).show()
        }
        Timber.d( "newDataList: $newDataList")

        adapter.differ.submitList(newDataList)
    }

    suspend fun getUsers() {
        try {
            val users = ApiService.restService.getUsers()
            if (!users.data.isNullOrEmpty()){
                dataList = users.data as List<Data>

                adapter.differ.submitList(dataList)
            }else{
                binding.root.snack("No users are there")
            }
        }catch (e: Exception){
            with(binding.root){
                when(e) {
                    is ConnectException -> snack("No Internet Connection Available!")
                    is UnknownHostException -> snack("You connection was reset")
                    is SocketTimeoutException -> snack("Failed to get Users. Try Again")
                    else -> snack("Something went wrong, Please Try Again")
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = UserShowCaseAdapter { data ->
            start<UserDetailActivity> {
                putExtra("userDetails", data)
            }
            binding.userRecyclerView.adapter = adapter
        }
    }

    suspend fun getNextPage(){
        try{
            val nxtPg = ApiService.restService.getNextPage()
            if (nxtPg.page==2){
                dataList = nxtPg.data as List<Data>
                adapter.differ.submitList(dataList)
            }else{
                Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show()
            }

        }catch (e: Exception){
            with(binding.root){
                when(e) {
                    is ConnectException -> snack("No Internet Connection Available!")
                    is UnknownHostException -> snack("You connection was reset")
                    is SocketTimeoutException -> snack("Failed to get Users. Try Again")
                    else -> snack("Something went wrong, Please Try Again")
                }
            }

        }
    }

    suspend fun getPrevPage(){
        try{
            val prevPg = ApiService.restService.getPrevPage()
            if (prevPg.page==1){
                dataList = prevPg.data as List<Data>
                adapter.differ.submitList(dataList)
            }else{
                Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show()
            }

        }catch (e: Exception){
            with(binding.root){
                when(e) {
                    is ConnectException -> snack("No Internet Connection Available!")
                    is UnknownHostException -> snack("You connection was reset")
                    is SocketTimeoutException -> snack("Failed to get Users. Try Again")
                    else -> snack("Something went wrong, Please Try Again")
                }
            }

        }
    }
}