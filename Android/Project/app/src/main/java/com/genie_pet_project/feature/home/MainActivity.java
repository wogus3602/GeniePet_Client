package com.genie_pet_project.feature.home;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.genie_pet_project.base.mvp.MvpActivity;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView,
        SearchView.OnQueryTextListener {
    private String query = "Programming";
    private List<Item> list;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addOnItemTouchListener(selectItemOnRecyclerView());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(this, 2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter.loadData(query);
    }

    private RecyclerItemClickListener selectItemOnRecyclerView() {
        return new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.getItem(list.get(position), activity);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                presenter.getItem(list.get(position), activity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setIconifiedByDefault(true);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            searchView.setSearchableInfo(info);
        }
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void getDataSuccess(Books books) {
        this.list = books.getItems();
        recyclerView.setAdapter(new BookAdapter(list, R.layout.item,
                getApplicationContext()));
    }

    @Override
    public void getDataFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToDetail(Intent intent) {
        startActivity(intent);
    }

    public void refresh(View view) {
        presenter.loadData(query);
    }

    private void getData(String query) {
        if (query != null) {
            this.query = query;
            presenter.loadData(query);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        getData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        presenter.stop();
        getData(newText);
        return false;
    }
}