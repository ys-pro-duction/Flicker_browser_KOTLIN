package com.ys_production.flickerbrowser

import android.app.SearchManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView

class SearchActivity : BaseClass() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setToolBar(true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_search,menu)
        Log.d("SearchActivity","onCreateOptionsMenu: end")
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)

        searchView.setSearchableInfo(searchableInfo)
        searchView.isIconified = false
        searchView.setOnCloseListener {
            finish()
            true
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()){
                    val sharedPref = getSharedPreferences("Main", MODE_PRIVATE)
                    sharedPref.edit().putString(SEARCH_QUERY,query).apply()
                }
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}