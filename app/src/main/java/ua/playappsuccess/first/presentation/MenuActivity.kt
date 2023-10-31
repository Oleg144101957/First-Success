package ua.playappsuccess.first.presentation

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import ua.playappsuccess.first.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonsClickListenners()
    }

    private fun setButtonsClickListenners() {

        val intentToThePlayScreen = Intent(this, PlayActivity::class.java)
        val intentToTheRulesScreen = Intent(this, RulesActivity::class.java)
        val intentToTheScoresScreen = Intent(this, ScoresActivity::class.java)

        binding.playBtn.setOnClickListener {
            startActivity(intentToThePlayScreen)
        }

        binding.rulesBtn.setOnClickListener {
            startActivity(intentToTheRulesScreen)
        }

        binding.scoreBtn.setOnClickListener {
            startActivity(intentToTheScoresScreen)
        }

        binding.exitBtn.setOnClickListener {
            finishAffinity()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}