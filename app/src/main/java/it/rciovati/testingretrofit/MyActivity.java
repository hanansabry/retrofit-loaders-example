package it.rciovati.testingretrofit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.rciovati.testingretrofit.loader.Callback;
import it.rciovati.testingretrofit.loader.RetrofitLoader;
import it.rciovati.testingretrofit.loader.RetrofitLoaderManager;
import it.rciovati.testingretrofit.model.Issue;
import it.rciovati.testingretrofit.net.GitHub;

public class MyActivity extends Activity implements Callback<List<Issue>> {

    @Inject
    GitHub gitHubService;

    @Override
    public void onFailure(Exception ex) {

        setProgressBarIndeterminateVisibility(false);

        Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(List<Issue> result) {

        Log.d("IssuesLoader", "onSuccess");

        displayResults(result);
    }

    private void displayResults(List<Issue> issues) {

        List<String> strings = toStringList(issues);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);

        ListView viewById = (ListView) findViewById(R.id.listView);
        viewById.setAdapter(adapter);
    }

    private static List<String> toStringList(List<Issue> issues) {

        ArrayList<String> strings = new ArrayList<String>(issues.size());

        for (Issue issue : issues) {

            strings.add(issue.getTitle());
        }

        return strings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ((App) getApplication()).inject(this);

        setContentView(R.layout.activity_my);

        IssuesLoader loader = new IssuesLoader(this, gitHubService);

        RetrofitLoaderManager.init(getLoaderManager(), 0, loader, this);
    }

    static class IssuesLoader extends RetrofitLoader<List<Issue>, GitHub> {

        public IssuesLoader(Context context, GitHub service) {

            super(context, service);
        }

        @Override
        public List<Issue> call(GitHub service) {

            Log.d("IssuesLoader", "call");

            return service.listRetrofitIssues();
        }
    }
}
