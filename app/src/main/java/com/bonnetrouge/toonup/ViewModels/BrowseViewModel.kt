package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import javax.inject.Inject

class BrowseViewModel @Inject constructor(val videoRepository: VideoRepository): ViewModel()