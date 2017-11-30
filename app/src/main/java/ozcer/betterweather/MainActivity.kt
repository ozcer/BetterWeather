package ozcer.betterweather

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    val apiBase = "http://api.wunderground.com/api/71cdf3c3260ce0f2/conditions/q/zmw:"
    val torontoExtention = "00000.176.71508.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val locExt = intent.getStringExtra("locationExtention")
        if (locExt == null){
            showTemperature(torontoExtention)
        } else {
            showTemperature(apiBase+locExt)
        }


        searchBtn.setOnClickListener {
            val i = Intent(this, SearchActivity::class.java)
            startActivity(i)
        }
    }

    fun showTemperature(locExt: String) {
        doAsync {
            val city: JSONObject = JSONObject(URL(apiBase+locExt).readText())
            val currentObservation = city.getJSONObject("current_observation")
            val displayLocation = currentObservation.getJSONObject("display_location")
            val locationName = displayLocation.getString("full")
            val temp = currentObservation.getDouble("temp_c")
            val msg = "$locationName is currently ${temp.toString()}C"
            uiThread {
                textTxt.setText(msg)
            }
        }
    }
}
