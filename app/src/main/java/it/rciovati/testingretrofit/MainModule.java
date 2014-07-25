package it.rciovati.testingretrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.rciovati.testingretrofit.net.GitHub;
import retrofit.RestAdapter;

@Module(
        library = true,
        injects = {MyActivity.class})
public class MainModule {

    @Provides
    @Singleton
    GitHub buildGitHubRestClient() {

        RestAdapter adapter =
                new RestAdapter.Builder().setEndpoint("https://api.github.com").build();

        return adapter.create(GitHub.class);
    }
}


