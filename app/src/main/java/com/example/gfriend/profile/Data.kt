package com.example.gfriend.profile

data class ProfilePageInfo(
    val profGameId: Long,
    val profGameFriendCode: String,
    val recFlag: Boolean,
    val intro:String
)

data class SearchPageInfo(
    val searchGameId: Long,
    val searchFriendCode: String
)

data class SearchGameFilter(
    val FilterGameId: Long
)

