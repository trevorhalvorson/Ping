package com.trevorhalvorson.ping.builder

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.google.gson.Gson
import com.trevorhalvorson.ping.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class BuilderActivity : AppCompatActivity(), BuilderContract.View {

    @Inject lateinit var builderPresenter: BuilderContract.Presenter

    override fun setPresenter(presenter: BuilderContract.Presenter) {
        builderPresenter = presenter
    }

    override fun showProgress() {
        progressDialog = ProgressDialog.show(this,
                getString(R.string.builder_progress_dialog_title_text),
                getString(R.string.builder_progress_dialog_message_text), true, false)
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun showError(error: String?) {
        if (error != null) {
            errorDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.error_dialog_title_text))
                    .setMessage(error)
                    .create()
            errorDialog?.show()
        }
    }

    override fun hideError() {
        errorDialog?.dismiss()
    }

    private var progressDialog: ProgressDialog? = null
    private var errorDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder)
        AndroidInjection.inject(this)

        val config = Gson().fromJson(intent.extras.getString("config"),
                BuilderConfig::class.java)

        findViewById<Button>(R.id.start_builder_button).setOnClickListener {
            builderPresenter.startBuilder(config)
        }
    }

}