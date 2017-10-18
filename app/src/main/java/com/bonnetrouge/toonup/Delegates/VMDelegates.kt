package com.bonnetrouge.toonup.Delegates

import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.ViewModels.BrowseVMDataHolder
import io.reactivex.Observable

sealed class VMDelegate {
    abstract fun requestAllMedia(videoRepository: VideoRepository): Observable<List<BasicSeriesInfo>>
    abstract fun requestPopularMedia(videoRepository: VideoRepository): Observable<List<BasicSeriesInfo>>
    abstract fun rawData(dataHolder: BrowseVMDataHolder): MutableList<BasicSeriesInfo>?
    abstract fun formattedData(dataHolder: BrowseVMDataHolder): MutableList<BannerModel>?
    abstract fun isDataEmpty(dateHolder: BrowseVMDataHolder): Boolean
}

class CartoonsVMDelegate : VMDelegate() {
    override fun requestAllMedia(videoRepository: VideoRepository) = videoRepository.getAllCartoons()
    override fun requestPopularMedia(videoRepository: VideoRepository) = videoRepository.getPopularCartoons()
    override fun rawData(dataHolder: BrowseVMDataHolder) = dataHolder.rawCartoons
    override fun formattedData(dataHolder: BrowseVMDataHolder) = dataHolder.cartoons
    override fun isDataEmpty(dateHolder: BrowseVMDataHolder) = dateHolder.cartoons == null
}

class MoviesVMDelegate : VMDelegate() {
    override fun requestAllMedia(videoRepository: VideoRepository) = videoRepository.getAllMovies()
    override fun requestPopularMedia(videoRepository: VideoRepository) = videoRepository.getPopularMovies()
    override fun rawData(dataHolder: BrowseVMDataHolder) = dataHolder.rawMovies
    override fun formattedData(dataHolder: BrowseVMDataHolder) = dataHolder.movies
    override fun isDataEmpty(dateHolder: BrowseVMDataHolder) = dateHolder.movies == null
}

class AnimeVMDelegate : VMDelegate() {
    override fun requestAllMedia(videoRepository: VideoRepository) = videoRepository.getAllAnime()
    override fun requestPopularMedia(videoRepository: VideoRepository) = videoRepository.getPopularAnime()
    override fun rawData(dataHolder: BrowseVMDataHolder) = dataHolder.rawAnime
    override fun formattedData(dataHolder: BrowseVMDataHolder) = dataHolder.anime
    override fun isDataEmpty(dateHolder: BrowseVMDataHolder) = dateHolder.anime == null
}
