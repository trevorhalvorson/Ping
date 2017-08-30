package com.trevorhalvorson.ping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = BuildConfig.TITLE
        val copy = BuildConfig.COPY
        val message = BuildConfig.MESSAGE
        val logo = BuildConfig.LOGO
        val url = BuildConfig.URL

        val titleText = findViewById<TextView>(R.id.title_text)
        val copyText = findViewById<TextView>(R.id.copy_text)
        val logoImage = findViewById<ImageView>(R.id.logo_image)
        val numberEditText = findViewById<EditText>(R.id.number_edit_text)
        val sendButton = findViewById<Button>(R.id.send_button)

        titleText.text = title
        copyText.text = copy
        logoImage.setImageResource(resources.getIdentifier(logo, "drawable", packageName))

        numberEditText.requestFocus()
        numberEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendButton.isEnabled = Patterns.PHONE.matcher(p0).matches()
            }
        })

        sendButton.setOnClickListener {
            Log.i(TAG, numberEditText.text.toString())
        }
    }

    override fun onBackPressed() {
    }
}
