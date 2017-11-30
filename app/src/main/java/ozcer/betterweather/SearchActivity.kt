package ozcer.betterweather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import android.widget.BaseAdapter



class SearchActivity : AppCompatActivity() {

    val searchQueryBase = "http://autocomplete.wunderground.com/aq?query="
    var searchResults = ArrayList<String>(0)
    lateinit var listAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults)
        searchResultLv.adapter = listAdapter

        searchFieldEdt.setOnEditorActionListener() {v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val city = searchFieldEdt.text.toString()
                searchCity(city)
                true
            } else { false }
        }

    }

    fun searchCity(city: String){
        doAsync {
            val returnString = URL(searchQueryBase+city).readText()
            val resultList = JSONObject(returnString).getJSONArray("RESULTS")
            //Log.i("doa", "jsonarray: $resultList")
            uiThread {
                searchResults.clear()
                for (i in 0..resultList.length() -1) {
                    val entry = resultList.getJSONObject(i).getString("name")
                    searchResults.add(entry)
                }
                listAdapter.notifyDataSetChanged()
            }
        }
    }
}
