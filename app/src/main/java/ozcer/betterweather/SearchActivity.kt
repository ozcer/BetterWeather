package ozcer.betterweather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast

class SearchActivity : AppCompatActivity() {

    val searchQueryBase = "http://autocomplete.wunderground.com/aq?query="
    val searchResults = listOf("Vancouver", "New York")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        searchFieldEdt.setOnEditorActionListener() {v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val city = searchFieldEdt.text.toString()
                searchCity(city)
                true
            } else { false }
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults)
        searchResultLv.adapter = adapter

    }

    fun searchCity(city: String){
        
    }
}
