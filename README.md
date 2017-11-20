# ToonUp

TODO: Add toonup icon

Android App that lets you Choose from Thousands of Cartoons/Movies/Animes to Watch for Free

## Table of Contents

## Screenshots

TODO

## Features

## Project Overview

* BrowseActivity
  * First non-splash screen shown
  * The background changes colour by using gradients fading in and out
    * Some have said it's ugly, others agree, but I still think it's cool
  * Gets info the list of tv shows/movies/animes
  * All data is held in BrowseViewModel
  * Contains CategoryChooserFragment which is the screen where you choose
  either Tv Shows, Movies, or Animes
  * Contains a single fragment that gets the correct data from the view model,
  and re-populates the recyclerview
    * The fragment receives a delegate depending on which category is
    currently selected, the fragment simply asks the delegate for the data,
    and the category specific delegate then passes another delegate to the
    viewmodel which knows how to get the correct data from it
    * The fragments show the lists of media in a netflix banner style view
      * Doesn't have the netflix horizontal scrolling (SnapHelper) since I
      don't think that feels nice, only maybe the playstore pulls it off
  * Also contains a search fragment which fuzzy search
    * Uses a search delegate to search the correct category info from the
    viewmodel
  * Delegates are useful since most of the code would be repeated with the
  only difference being that it is pulling the info from a different api call,
  and then a different variable when the data gets cached
* DetailActivity
  * Collapsible toolbar that has a nice activity transition
  * The information I get back from the api I'm using is pretty bad to say the
  least, but it's better than nothing. So I show that at the top as an item in
  the recyclerview
  * I just popular the recyclerview with the info I get back from the api,
* PlayerActivity
  * TODO

## Libraries Used

* RxJava2
* Dagger2
* Exoplayer2
* Support Libraries
* ViewModel from Android Architecture Components
* Retrofit
* Glide
* Moshi

## Future Plans

These are possible future features that I am looking to implement. However,
currently I am working on an array of other projects and am in school so these
are not high priorities.
* Add user sign-in
  * Add user specific favorites category
  * Maintain last watched position so user can leave, and come back to the
  same spot
* Create custom MediaSource/TrackSelector for Exoplayer that can handle link
failures and retry with the next link on its own. This would also need to be
able to handle multiple possible links for muliple parts.
* Save info to room/realm db for a nicer offline experience

## FAQ
