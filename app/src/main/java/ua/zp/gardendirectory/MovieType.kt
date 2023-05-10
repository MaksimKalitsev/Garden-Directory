package ua.zp.gardendirectory

enum class MovieType(val endpoint: String) {
    TOP_RATED("top_rated"),
    POPULAR("popular"),
    NOW_PLAYING("now_playing"),
    UPCOMING("upcoming")
}