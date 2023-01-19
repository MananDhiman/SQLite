package com.manandhiman.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.manandhiman.sqlite.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name",
        ).allowMainThreadQueries().build()
        val userDao = db.userDao()

        binding.buttonAddUser.setOnClickListener{
            addNewUser(userDao)
            refreshUsersList(userDao)
        }

        binding.buttonDeleteUser.setOnClickListener { deleteUser(userDao) }

        refreshUsersList(userDao)
    }

    private fun deleteUser(userDao: UserDao) {
        val id = binding.editTextDeleteId.text.toString().toInt()
        userDao.deleteUserById(id)
        refreshUsersList(userDao)
    }

    private fun refreshUsersList(userDao: UserDao) {
        val listUser = userDao.getAll()
        binding.textViewListUsers.text = ""
        //Log.v("list user", listUser[i].toString())
        for (i in listUser.indices) binding.textViewListUsers.text =
            "${binding.textViewListUsers.text}\n${listUser[i].Id} = ${listUser[i]} "
    }

    private fun addNewUser(userDao: UserDao) {
        var firstName: String = binding.editTextFirstName.text.toString().trim()
        var lastName: String = binding.editTextLastName.text.toString().trim()

        val user = User(firstName,lastName)

        userDao.insertAll(user)
    }
}