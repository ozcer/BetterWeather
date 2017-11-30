package ozcer.betterweather

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL


class SearchActivity : AppCompatActivity() {

    val searchQueryBase = "http://autocomplete.wunderground.com/aq?query="
    var searchResults = ArrayList<String>(0)
    var cityExtentions = ArrayList<String>(0)
    lateinit var listAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults)
        searchResultLv.adapter = listAdapter
        searchResultLv.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("CITY_EXTENTION", cityExtentions.get(i))
            Log.i("jazz", "passing ${cityExtentions.get(i)}")
            startActivity(intent)
        }
        
        searchFieldEdt.setOnEditorActionListener() {v, actionId, event ->
            // on enter pressed begin city search process
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
            uiThread {
                // empty and repopulate list to display
                searchResults.clear()
                cityExtentions.clear()
                for (i in 0..resultList.length() -1) {
                    val name = resultList.getJSONObject(i).getString("name")
                    val cityExtention = resultList.getJSONObject(i).getString("zmw")
                    searchResults.add(name)
                    cityExtentions.add(cityExtention)
                }
                listAdapter.notifyDataSetChanged()
            }
        }
    }
}
