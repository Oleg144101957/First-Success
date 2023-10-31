package ua.playappsuccess.first.presentation

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.ValueCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ua.playappsuccess.first.databinding.ActivityScoresBinding
import ua.playappsuccess.first.presentation.customUI.FileChooserInterface
import ua.playappsuccess.first.presentation.customUI.ScoresWebView

class ScoresActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScoresBinding

    private lateinit var chooseCallback: ValueCallback<Array<Uri?>>
    private val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){
        chooseCallback.onReceiveValue(it.toTypedArray())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scoresWebView = ScoresWebView(this, object : FileChooserInterface{
            override fun onFileCallback(parameters: ValueCallback<Array<Uri?>>) {
                chooseCallback = parameters
            }
        })

        scoresWebView.initCustomScoresContainer(getContent, binding.root)

        binding.root.addView(scoresWebView)

        scoresWebView.loadUrl("https://telegra.ph/Here-will-be-the-points-of-all-players-10-26")

    }


}