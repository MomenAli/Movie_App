package com.example.engmomenali.movieappmaster;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by MomenAli on 10/16/2017.
 */

public class  MovieJsonUtils {
    






    public static ArrayList getMovies(Context context, String JsonStr) throws JSONException {
        ArrayList<Movie> mMovieList = new ArrayList<Movie>();
        /*
        *   I Need this information for this stage (Stage 1)
        *   original title
            movie poster image thumbnail
            A plot synopsis (called overview in the api)
            user rating (called vote_average in the api)
            release date
        *
        * */


        //definre the parameter of the page
        final String OWM_Results      = "results";
        final String OWM_MESSAGE_CODE = "cod";

        final String OWM_id           = "id";
        final String OWM_Title        = "original_title";
        final String OWM_poster_path  = "poster_path";
        final String OWM_overview     = "overview";
        final String OWM_vote_average = "vote_average";
        final String OWM_release_date = "release_date";

        Log.d("TAG", "getMovies: jsonstr ====== > "+JsonStr);
        JSONObject MoviesJson = new JSONObject(JsonStr);

                /* Is there an error? */
        if (MoviesJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = MoviesJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }


        JSONArray   MovieJsonArray = MoviesJson.getJSONArray(OWM_Results);
        for(int i = 0; i< MovieJsonArray.length();i++){

            Movie MovieTemp = new Movie();


            JSONObject MovieJSONItem = MovieJsonArray.getJSONObject(i);

            MovieTemp.setId(MovieJSONItem.getLong(OWM_id));
            MovieTemp.setTitle(MovieJSONItem.getString(OWM_Title));
            MovieTemp.setPosterPath(MovieJSONItem.getString(OWM_poster_path));
            MovieTemp.setRatings(MovieJSONItem.getDouble(OWM_vote_average));
            MovieTemp.setReleaseDate(MovieJSONItem.getString(OWM_release_date));
            MovieTemp.setoverview(MovieJSONItem.getString(OWM_overview));
            mMovieList.add(MovieTemp);
            Log.d("", "getMovies: " + mMovieList.get(i).getTitle());
        }
        return mMovieList;
    }

}
