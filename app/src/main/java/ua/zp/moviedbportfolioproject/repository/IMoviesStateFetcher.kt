package ua.zp.moviedbportfolioproject.repository

interface IMoviesStateFetcher {
    /**
     * @return map of id and isFavorite key-value pairs
     */
    suspend fun getStateMap(ids: List<Int>): Map<Int, Boolean>
}