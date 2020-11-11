package manlan

val MOVIE_ID_COL = "movie_id"
val TITLE_COL = "title"
val RELEASE_YEAR_COL = "release_year"

val UUID_TYPE = "UUID"
val TEXT_TYPE = "TEXT"
val INT_TYPE = "INT"


fun createMoviesTable(): String {
    return "CREATE TABLE movies (" +
            "$MOVIE_ID_COL $UUID_TYPE, " +
            "$TITLE_COL $TEXT_TYPE, " +
            "$RELEASE_YEAR_COL $INT_TYPE, " +
            "PRIMARY KEY (($MOVIE_ID_COL))" +
            ");"
}


