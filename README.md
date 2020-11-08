# Android MVP Implementation

## Libraries
- [Groupie](https://github.com/lisawray/groupie)
- [Cicerone](https://github.com/terrakok/Cicerone)
- [Dagger2](https://github.com/google/dagger)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxPaper](https://github.com/pakoito/RxPaper)
- [RxBinding](https://github.com/JakeWharton/RxBinding)
- [Glide](https://github.com/bumptech/glide)

## How does it works?
This is MY MVP Android implementation! I tried to create an example project using MVP + Clean Architecture to work as base to new projects.
There's a BottomNavigation to navigate between flows, the first tab is called "Home", and the second one "Nav".

1. Home
Using Groupie to create my RecyclerView, and RxJava to connect all interactions with components and Use Cases, there is a Paginated List in
this tab. The Presenter will fetch data from [PokéAPI](https://pokeapi.co/), do all paginated list logic, and deal with errors and loading. 
Once some data is fetched, it's possible do navigate to a second screen clicking in to the Pokémon Card.

![alt text](https://i.imgur.com/lTQlMCr.png)

1.1 Pokémon Information Screen
This Screen contains the Pokémon front sprite, name and an option to capture the Pokémon. If the pokémon is caught, it'll appears into the second
tab screen. Just like the previous screen, all interactions are dealed using RxJava.

![alt text](https://i.imgur.com/bgmfQXn.png)

2. Nav
All caught pokémon will be displayed in this screen. Once the user catches a pokémon, it'll be stored into the cache using the RxPaper and this
screen will be updated.

![alt text](https://i.imgur.com/YNeWqoK.png)

## Autor
Bruno Abe

## License
[MIT](https://choosealicense.com/licenses/mit/)
