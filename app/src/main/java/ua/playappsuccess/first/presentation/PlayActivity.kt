package ua.playappsuccess.first.presentation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ua.playappsuccess.first.R
import ua.playappsuccess.first.databinding.ActivityPlayBinding
import kotlin.random.Random

class PlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayBinding

    private var xOffset = 0F
    private val scores = MutableStateFlow(0)
    private val time = MutableStateFlow(20)
    private var screenWidth: Float = 0F
    private var halfOfTheScreenWidth: Float = 0F
    private var gameMode: GameMode = GameMode.PLAYING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenWidth = resources.displayMetrics.widthPixels.toFloat()
        halfOfTheScreenWidth = screenWidth / 2F

        setButtonsClickListenners()
        begingTimerCountDown()
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            scores.collect{
                binding.scoresTV.text = "Scores: ${scores.value}"

                if (it%3 == 0 && it>2){
                    showCompliments()
                }
            }
        }

        lifecycleScope.launch {
            time.collect{
                binding.timeTV.text = "Time: $it"
                if (it<10){
                    binding.timeTV.setTextColor(Color.RED)
                }

                if (it == 0){
                    gameMode = GameMode.FINISHED
                    binding.wishTV.text = "Game over"
                    binding.exitGame.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun begingTimerCountDown() {
        lifecycleScope.launch {
            for (i in 0..19){
                delay(1000)
                val currentTime = time.value
                time.emit(currentTime-1)
            }
        }
    }

    private fun showCompliments() {
        binding.wishTV.setTextColor(Color.WHITE)
        binding.wishTV.text = getRandomCompliment()

        val scaleX = ObjectAnimator.ofFloat(binding.wishTV, "scaleX", 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(binding.wishTV, "scaleY", 1f, 0f)
        val alpha = ObjectAnimator.ofFloat(binding.wishTV, "alpha", 1f, 0f)

        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY, alpha)
        set.duration = 3000 // Set your desired duration

        set.start()

    }

    private fun setButtonsClickListenners() {
        binding.fire.setOnClickListener {
            if (gameMode == GameMode.PLAYING){
                shut(binding.fire)
            }
        }

        binding.right.setOnClickListener {
            val newOffset = xOffset + halfOfTheScreenWidth / 5
            if (newOffset in -(halfOfTheScreenWidth+35F)..(halfOfTheScreenWidth-35F)) {
                xOffset = newOffset
                binding.fire.translationX = xOffset
            }
        }

        binding.left.setOnClickListener {
            val newOffset = xOffset - halfOfTheScreenWidth / 5
            if (newOffset in (-halfOfTheScreenWidth+35F)..(halfOfTheScreenWidth-35F)) {
                xOffset = newOffset
                binding.fire.translationX = xOffset
            }
        }

        binding.exitGame.setOnClickListener {
            val intentToTheMenu = Intent(this, MenuActivity::class.java)
            startActivity(intentToTheMenu)
        }
    }

    private fun shut(imageView: ImageView) {
        imageView.post {
            val yPositionOfFireCard = binding.fire.y
            binding.fire.animate()
                .translationY(-yPositionOfFireCard)
                .setDuration(700)
                .withEndAction {

                    val targetRect = Rect()
                    binding.target.getGlobalVisibleRect(targetRect)

                    val fireRect = Rect()
                    binding.fire.getGlobalVisibleRect(fireRect)

                    if (fireRect.intersect(targetRect)) {
                        //int
                        animateExplosion(binding.target)

                        lifecycleScope.launch {
                            val currentScores = scores.first()
                            scores.emit(currentScores+1)

                            delay(1000)
                            binding.target.alpha = 1F
                            binding.target.scaleX = 1F
                            binding.target.scaleY = 1F
                            binding.target.translationX = getRandomXPosition()
                            binding.target.setImageResource(getRandomImageRes())
                        }
                    }

                    binding.fire.translationY = 0F
                }
                .start()
        }
    }

    private fun animateExplosion(imageView: ImageView) {
        val scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0f)
        val alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f)

        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY, alpha)
        set.duration = 800 // Set your desired duration

        set.start()
    }

    private fun getRandomXPosition(): Float {
        return Random.nextFloat() * (halfOfTheScreenWidth * 2F) - (halfOfTheScreenWidth+35F)
    }

    private fun getRandomImageRes(): Int {
        val listOfImages = listOf(
            R.drawable.card2,
            R.drawable.card33
        )

        return listOfImages[Random.nextInt(listOfImages.size)]
    }

    private fun getRandomCompliment(): String{
        val listOfCompliments = listOf(
            "Дуже гарно",
            "Продовжуй грати",
            "Файно",
            "Ти кльовий",
            "Супер !!!"
        )
        return listOfCompliments[Random.nextInt(listOfCompliments.size)]
    }
}




enum class GameMode{
    PLAYING, FINISHED
}