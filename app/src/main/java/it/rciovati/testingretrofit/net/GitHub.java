package it.rciovati.testingretrofit.net;

import java.util.List;

import it.rciovati.testingretrofit.model.Issue;
import retrofit.http.GET;

public interface GitHub {

    @GET("/repos/square/retrofit/issues")
    List<Issue> listRetrofitIssues();
}
