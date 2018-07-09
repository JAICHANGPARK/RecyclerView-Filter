package com.dreamwalker.recyclerviewfilter.Retrofit;

import com.dreamwalker.recyclerviewfilter.Model.Item;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IMyAPI {
    @GET("photos")
    Observable<List<Item>> loadData();
}
