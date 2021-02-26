# GallerIQUII 📱
A very simple gallery app written as a code challenge

## Features 🧰
GallerIQUII lets you download and visualize photos from a predefined endpoint. Just write what you
want to see: GallerIQUII will search it for you while you're typing!
Once images are found (notice that videos are not supported yet), they're displayed in a gallery
view and you can tap on gallery items in order to visualize better the specific image, zomming in
and out as you please. Additionally, from this screen you can reach informations about the specific
image such as name, author, creation date and so on.

## Architecture 👷‍♀️
GallerIQUII was built having in mind the
[Android Architecture Components](https://developer.android.com/topic/libraries/architecture/).

In particular, it leverages on the MVVM pattern by connecting
[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)s
with [Repositor](https://developer.android.com/jetpack/guide#connect-viewmodel-repository)ies and
[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)s,
making the app robust w.r.t code modularity, reusability, data freshness, testability and errors:
mostly the kind of best practices described
[here](https://developer.android.com/jetpack/guide#best-practices).

Also, the app was completely written in Kotlin, moslty because we love its expressive, concise, and
powerful syntax. Besides, coming to the Kotlin side gives you unlimited powers like
[Coroutines](https://developer.android.com/kotlin/coroutines),
a great way to implement search-while-typing feature!

## Dependencies 🛠️
GallerIQUII depends on a very short list of libraries, which are very spread and useful:

[GLIDE](https://github.com/bumptech/glide) → Dowload and cache images from the internet

[RETROFIT2](https://github.com/square/retrofit) → Perform and handle REST calls

[GSON](https://github.com/google/gson) → Json serialization/deserialization

## Requirements 📜
GallerIQUII requires to have at least Android 5.0 Lollpop (which maps to Api level 21)
