package com.weloftlabs.superagro.interfaces;

import com.weloftlabs.superagro.models.CropDetails;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Shabaz on 31-Jan-16.
 */
public interface CropDetailInterface
{
    @GET("/api/api_get?agg_level_desc=STATE&prodn_practice_desc=ALL%20PRODUCTION%20PRACTICES&class_desc=ALL%20CLASSES&source_desc=SURVEY&sector_desc=CROPS&freq_desc=ANNUAL&year__or=2015&year__or=2014&year__or=2013&year__or=2012&year__or=2011&year__or=2010&year__or=2009&year__or=2008&year__or=2007&year__or=2006&year__or=2005&year__or=2004&year__or=2003&year__or=2002&year__or=2001&year__or=2000&year__or=1999&year__or=1998&year__or=1997&year__or=1996&year__or=1995")
    Observable<CropDetails> getCropDetails(@Query("commodity_desc") String sort, @Query("state_name") String state,@Query("statisticcat_desc") String statisticcat_desc);
    //category = YIELD , AREA HARVESTED, AREA PLANTED, PRICE RECEIVED
}
