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

    val apiUrl: String = "http://api.wunderground.com/api/71cdf3c3260ce0f2/conditions/q/zmw:00000.176.71508.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateBtn.setOnClickListener {
            doAsync {
                val Toronto: JSONObject = JSONObject(URL(apiUrl).readText())
                val currentObservation = Toronto.getJSONObject("current_observation")
                val temp = currentObservation.getDouble("temp_c")
                val msg = "Toronto is currently ${temp.toString()}C"
                uiThread {
                    textTxt.setText(msg)
                }
            }
        }

        searchBtn.setOnClickListener {
            val i = Intent(this, SearchActivity::class.java)
            startActivity(i)
        }
    }
}
