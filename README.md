# ToonUp #

<img src="https://github.com/RRethy/ToonUp/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" title="ToonUp">

Android App that lets you Choose from Thousands of Cartoons/Movies/Animes to Watch for Free

[Download the APK!](https://drive.google.com/file/d/1vsO91fP9cIyu-CSgJmiDikxxunAzHhj0/view)

## Table of Contents ##

  * [Check it out!](#check-it-out)
  * [Libraries used](#libraries-used)
  * [Features](#features)
  * [Future Plans](#future-plans)
  * [FAQ](#faq)

## Check it out! ##

<img src="https://github.com/RRethy/GifHost/blob/master/browsing_gif.gif" width="250" height="444" title="gif1"> <img src="https://github.com/RRethy/GifHost/blob/master/watching_gif.gif" width="250" height="444" title="gif2">

<img src="https://user-images.githubusercontent.com/21000943/34424673-501ea672-ebf3-11e7-8e85-452f8f6d229d.png" width="250" height="444" title="banners_ss_toonup"> <img src="https://user-images.githubusercontent.com/21000943/34424678-57a4683c-ebf3-11e7-813e-f6a78d78448d.png" width="250" height="444" title="detail_1_ss_toonup"> <img src="https://user-images.githubusercontent.com/21000943/34424680-5895dda2-ebf3-11e7-9c06-a29d046917b9.png" width="250" height="444" title="detail_expanded_ss_toonup">
<img src="https://user-images.githubusercontent.com/21000943/34424681-5b17ade4-ebf3-11e7-8843-19c95f01bab5.png" width="250" height="444" title="main_chooser_ss_toonup"> <img src="https://user-images.githubusercontent.com/21000943/34424682-5ca6a61a-ebf3-11e7-94fb-e8f9106b5960.png" width="250" height="444" title="search_ss_toonup">
<img src="https://user-images.githubusercontent.com/21000943/34424683-5ddbc38a-ebf3-11e7-8230-182108695a3e.png" width="444" height="250" title="video_playing_ss_toonup">

## Libraries Used ##

* RxJava2
* Dagger2
* Exoplayer2
* Support Libraries
* ViewModel from Android Architecture Components
* Retrofit
* Glide
* Moshi

## Features ##

* Watch TV shows, movies, or anime for free with no ads
* Fuzzy searching
* Instagram style background that transitions
* Clean browsing UI with Netflix style banners
* Large selection of videos to choose from
* Auto plays the next episode
  * Auto tries all available links

## Future Plans ##

These are possible future features that I am looking to implement. However,
currently I am working on an array of other projects and am in school so these
are not high priorities.

* Add UI while watching video to switch to new episode
* Add user sign-in
  * Add user specific favorites category
* Save info to room/realm db for a nicer offline experience
  * Add a favorites category
  * Also save the spot that they were previous watching

## FAQ ##

Not really FAQ, more like question I assume people would frequently ask

**Why does it take so long to load the banners initially?**
> It is not my api and I don't have any control on how the data is formatted when I get it. I am doing two simultaneous calls, combining those observables, then parsing the data by genre so it can be shown in the nice banners, this cannot be significantly optimized unless I get to deciede the format of the data I get back.

**Can I get this on the play store?**
> No, it breaks terms of service.

**Why use so many delegates with browsing and searching?**
> I process all the info I get from the api the same way and display it the same way, only difference is the api call and what variable caches the data. Delegates are perfect for that type of scenario.

**Why are some shows categorized by season while others are not**
> It's not my backend so that's just how they store stuff. Typically large shows like "Family Guy" are split into seasons while smaller ones are not.

**Where do you get the videos from?**
> Somewhere... Over... The... Rainbow. I get it from a 3rd party site where I reverse engineered their api. If you want that api, try looking in the generate method in RetrofitGenerator.kt.

**Who are the characters in the logo?**
> From left to right: Bugs Bunny, Spongebob Squarepants, Roger (Dressed as Stewie Griffin), Stewie Griffin, Louise Belcher, Batman, Philip J. Fry, Bojack Horseman, Beavis, Rick Sanchez. Also, the background is the looney tunes logo and the toonup writing was done in the looneytunes font (using gimp). And yes, they are in a popcorn bag. I felt that would give the logo a very minimalist look ;).
