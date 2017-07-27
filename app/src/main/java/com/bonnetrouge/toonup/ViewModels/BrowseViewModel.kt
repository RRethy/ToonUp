package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import javax.inject.Inject

class BrowseViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel()