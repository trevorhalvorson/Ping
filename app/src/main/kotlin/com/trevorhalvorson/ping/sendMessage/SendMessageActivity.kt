package com.trevorhalvorson.ping.sendMessage

import android.app.AlertDialog
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.trevorhalvorson.ping.BuildConfig
import com.trevorhalvorson.ping.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_number_pad.*
import javax.inject.Inject

class SendMessageActivity : AppCompatActivity(), SendMessageContract.View, View.OnClickListener {

    @Inject lateinit var sendMessagePresenter: SendMessageContract.Presenter

    override fun setPresenter(presenter: SendMessageContract.Presenter) {
        sendMessagePresenter = presenter
    }

    override fun showProgress() {
        progressDialog = ProgressDialog.show(this, getString(R.string.progress_dialog_title_text),
                getString(R.string.progress_dialog_message_text), true, false)
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun showError(error: String?) {
        if (error != null) {
            errorDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.send_message_error_dialog_title_text))
                    .setMessage(error)
                    .create()
            errorDialog?.show()
        }
    }

    override fun hideError() {
        errorDialog?.dismiss()
    }

    override fun clearInput() {
        number_edit_text.text.clear()
    }

    private var progressDialog: ProgressDialog? = null
    private var errorDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        hideSystemUI()

        val title = BuildConfig.TITLE
        val copy = BuildConfig.COPY
        val message = BuildConfig.MESSAGE
        val logo = BuildConfig.LOGO

        title_text.text = title
        copy_text.text = copy

        if (BuildConfig.DEBUG) {
            logo_image.setImageResource(resources.getIdentifier(logo, "drawable", packageName))
        } else {
            Glide.with(this).load(logo).into(logo_image)
        }

        send_button.setOnClickListener {
            sendMessagePresenter.sendMessage(message)
        }

        number_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                send_button.isEnabled = Patterns.PHONE.matcher(p0).matches() &&
                        !PhoneNumberUtils.isEmergencyNumber(p0.toString())
            }
        })

        num_pad_1.setOnClickListener(this)
        num_pad_2.setOnClickListener(this)
        num_pad_3.setOnClickListener(this)
        num_pad_4.setOnClickListener(this)
        num_pad_5.setOnClickListener(this)
        num_pad_6.setOnClickListener(this)
        num_pad_7.setOnClickListener(this)
        num_pad_8.setOnClickListener(this)
        num_pad_9.setOnClickListener(this)
        num_pad_0.setOnClickListener(this)

        num_pad_del.setOnClickListener {
            if (number_edit_text.text.isNotEmpty()) {
                number_edit_text.text.delete(number_edit_text.text.length - 1,
                        number_edit_text.text.length)
            }
        }
        num_pad_del.setOnLongClickListener {
            clearInput()
            true
        }
    }

    override fun onClick(p0: View?) {
        number_edit_text.text.append((p0 as TextView).text)
    }

    override fun onBackPressed() {
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                .or(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
                .or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                .or(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                .or(View.SYSTEM_UI_FLAG_FULLSCREEN)
                .or(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}
