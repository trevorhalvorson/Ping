package com.trevorhalvorson.ping.sendMessage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.trevorhalvorson.ping.BuildConfig
import com.trevorhalvorson.ping.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class SendMessageActivity : AppCompatActivity(), SendMessageContract.View {

    @Inject lateinit var sendMessagePresenter: SendMessageContract.Presenter

    override fun setPresenter(presenter: SendMessageContract.Presenter) {
        sendMessagePresenter = presenter
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showInputState(valid: Boolean) {
        send_button.isEnabled = valid
    }

    override fun clearInput() {
        number_edit_text.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        val title = BuildConfig.TITLE
        val copy = BuildConfig.COPY
        val message = BuildConfig.MESSAGE
        val logo = BuildConfig.LOGO
        val url = BuildConfig.URL

        title_text.text = title
        copy_text.text = copy
        logo_image.setImageResource(resources.getIdentifier(logo, "drawable", packageName))

        number_edit_text.requestFocus()
        number_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendMessagePresenter.updateInputState(p0)
            }
        })

        send_button.setOnClickListener {
            sendMessagePresenter.sendMessage()
        }
    }

    override fun onBackPressed() {
    }
}
