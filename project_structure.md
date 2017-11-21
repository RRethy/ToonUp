# Project Overview #

* BrowseActivity
  * First non-splash screen shown
  * The background changes colour by using gradients fading in and out
    * Some have said it's ugly, I agree, but I still think it's cool
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
  * Fullscreen and keeps the screen on
  * Gets the id of the episode that was clicked on and all the other ids from the
  episodes (for auto-play), calls the background to get a bunch of links back, then passes the first one to exoplayer
  * If it fails then show the user a bottom sheet that offers them choices of links to choose from
    * Might change this, originally I had a large picture idea for this, but I've changed my mind a few times, still undecided

