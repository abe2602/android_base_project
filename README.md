# Android MVVM with Kotlin Flow and LiveData Implementation

## Libraries
- [Groupie](https://github.com/lisawray/groupie)
- [Cicerone](https://github.com/terrakok/Cicerone)
- [Dagger2](https://github.com/google/dagger)
- [Kotlin Flow](https://developer.android.com/kotlin/flow)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Glide](https://github.com/bumptech/glide)

## What's happening?
This is MY MVVM Android implementation! I tried to create an example project using MVVM + Clean Architecture to work as base to new projects.
There's a BottomNavigation to navigate between flows, the first tab is called "Home", and the second one "Nav".

1. Home
Using Groupie to create my RecyclerView, and Kotlin Flow to connect all interactions with components and Use Cases, there is a Paginated List in
this tab. The Presenter will fetch data from [PokéAPI](https://pokeapi.co/), do all paginated list logic, and deal with errors and loading. 
Once some data is fetched, it's possible do navigate to a second screen clicking in to the Pokémon Card.
<p align="center">
  <img src="https://i.imgur.com/lTQlMCr.png">
</p>

1.1 Pokémon Information Screen
This Screen contains the Pokémon front sprite, name and an option to capture the Pokémon. If the pokémon is caught, it'll appears into the second
tab screen. Just like the previous screen, all interactions are dealed using RxJava.

<p align="center">
  <img src="https://i.imgur.com/bgmfQXn.png">
</p>

2. Nav
All caught pokémon will be displayed in this screen. Once the user catches a pokémon, it'll be stored into the cache using the RxPaper and this
screen will be updated.

<p align="center">
  <img src="https://i.imgur.com/YNeWqoK.png">
</p>

## Want to understand more about this project?
If you want to understand how the navigation, dagger, or groupie works, you can check some of my repositories!
- [Groupie Example](https://github.com/abe2602/groupie_example)
- [Navigation with Cicerone and Dagger2](https://github.com/abe2602/cicerone_dagger)
- [Simple Dagger2 usage](https://github.com/abe2602/dagger2_example)

Thank you all, folks!

## Author
Bruno Abe

## License
[MIT](https://choosealicense.com/licenses/mit/)
