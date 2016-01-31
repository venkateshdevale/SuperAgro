package com.weloftlabs.superagro.interfaces;

import com.weloftlabs.superagro.models.CropsList;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Shabaz on 30-Jan-16.
 */
public interface CropsInterface
{
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter
    //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=d3fa04813f90375f6e05bac82b0c6ba9
    @GET("/3/discover/movie")
    Observable<CropsList> getCropsList();

}
