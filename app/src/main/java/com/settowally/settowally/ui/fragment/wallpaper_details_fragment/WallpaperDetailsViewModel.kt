package com.settowally.settowally.ui.fragment.wallpaper_details_fragment

import android.app.WallpaperManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.settowally.settowally.data.model.WallpaperType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class WallpaperDetailsViewModel @Inject constructor() : ViewModel() {

    fun setWallpaper(uri: String, wallpaperType: WallpaperType, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            uri.let { uri ->
                val wallpaperManager = WallpaperManager.getInstance(context)
                val stream = URL(uri).openStream()

                try {
                    when (wallpaperType) {
                        WallpaperType.HOME_SCREEN -> wallpaperManager.setStream(
                            stream, null, false, WallpaperManager.FLAG_SYSTEM
                        )

                        WallpaperType.LOCK_SCREEN -> wallpaperManager.setStream(
                            stream, null, false, WallpaperManager.FLAG_LOCK
                        )

                        WallpaperType.HOME_AND_LOCK_SCREEN -> wallpaperManager.setStream(
                            stream,
                            null,
                            false,
                            WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                        )

                        else -> {
                            wallpaperManager.setStream(stream)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Failed to set wallpaper", Toast.LENGTH_SHORT)
                        .show()
                } finally {
                    try {
                        stream.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}