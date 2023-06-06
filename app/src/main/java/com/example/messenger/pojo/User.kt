package com.example.messenger.pojo

data class User(val id: String = "",
                val name: String = "",
                val lastname: String = "",
                val age: Int = -1,
                val online: Boolean? = null)
