package io.github.sampletabnav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopLevelScreen(
    val navGraphRoute: String,
    val startDestinationRoute: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Profile : TopLevelScreen("profileGraph", "profile", R.string.profile, Icons.Filled.Favorite)
    object FriendsList : TopLevelScreen("friendsGraph", "friendsList", R.string.friends_list, Icons.Filled.Person)
}