еpackage com.example.lumi

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_SPEECH = 100
    private val REQUEST_PERMISSION_CODE = 200
    private lateinit var greetingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        greetingText = findViewById(R.id.lumiGreeting)
        val btnMusic = findViewById<Button>(R.id.btnMusic)
        val btnNotes = findViewById<Button>(R.id.btnNotes)
        val btnCalculator = findViewById<Button>(R.id.btnCalculator)

        // Обработка кнопок
        btnMusic.setOnClickListener {
            Toast.makeText(this, "Открываю музыку...", Toast.LENGTH_SHORT).show()
            // В будущем: запуск плеера
        }

        btnNotes.setOnClickListener {
    val intent = Intent(this, NotesActivity::class.java)
    startActivity(intent)
          // В будущем: запуск заметок
        }

        btnCalculator.setOnClickListener {
    val intent = Intent(this, CalculatorActivity::class.java)
    startActivity(intent)
}
            
        
        // Проверка разрешения на микрофон
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_PERMISSION_CODE)
        } else {
            startVoiceRecognition()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startVoiceRecognition()
        } else {
            Toast.makeText(this, "Нужно разрешение для микрофона", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH)
        } catch (e: Exception) {
            Toast.makeText(this, "Голосовой ввод недоступен", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH && resultCode == Activity.RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = result?.get(0)?.lowercase(Locale.getDefault()) ?: ""

            if ("люми" in spokenText) {
                greetingText.text = "Слушаю!"
            } else {
                greetingText.text = "Скажите «Люми»"
            }

            // Повторный запуск голосового ввода
            startVoiceRecognition()
        }
    }
}
