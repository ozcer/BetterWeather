package ozcer.betterweather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast

class SearchActivity : AppCompatActivity() {

    val searchQueryBase = "http://autocomplete.wunderground.com/aq?query="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchFieldEdt.setOnEditorActionListener() {v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val city = searchFieldEdt.text.toString()
                searchCity(city)
                true
            } else {
                false
            }

        }
    }

    fun searchCity(city: String){
        longToast("u pressed enter")
    }
}
