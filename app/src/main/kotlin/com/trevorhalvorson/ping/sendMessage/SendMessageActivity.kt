package com.trevorhalvorson.ping.sendMessage

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.*
import com.google.android.libraries.remixer.annotation.*
import com.google.android.libraries.remixer.ui.view.RemixerFragment
import com.google.gson.Gson
import com.trevorhalvorson.ping.BuildConfig
import com.trevorhalvorson.ping.R
import com.trevorhalvorson.ping.builder.BuilderActivity
import com.trevorhalvorson.ping.builder.BuilderConfig
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_send_message.*
import kotlinx.android.synthetic.main.layout_number_pad.*
import javax.inject.Inject

class SendMessageActivity : AppCompatActivity(), SendMessageContract.View, View.OnClickListener {

    @Inject lateinit var sendMessagePresenter: SendMessageContract.Presenter

    override fun setPresenter(presenter: SendMessageContract.Presenter) {
        sendMessagePresenter = presenter
    }

    override fun showProgress() {
        progressDialog = ProgressDialog.show(this,
                getString(R.string.message_progress_dialog_title_text),
                getString(R.string.message_progress_dialog_message_text), true, false)
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

    override fun clearInput() {
        number_edit_text.text.clear()
    }

    override fun showPinError() {
        pinEditText?.error = getString(R.string.pin_error_message)
    }

    override fun showAdminView() {
        val view = layoutInflater.inflate(R.layout.dialog_admin, send_message_layout, false)

        val dialog = AlertDialog.Builder(this).setView(view)
                .setTitle(getString(R.string.dialog_admin_title))
                .setNegativeButton(getString(R.string.dialog_admin_negative_button),
                        { dialogInterface, _ ->
                            dialogInterface.cancel()
                            hideConfigButton()
                            adminMode = false
                        })
                .create()

        dialog.show()

        view.findViewById<Button>(R.id.edit_configs_button).setOnClickListener {
            showConfigView()
            dialog.cancel()
        }

        view.findViewById<Button>(R.id.show_builder_view_button).setOnClickListener {
            val builderIntent = Intent(this, BuilderActivity::class.java)
            builderIntent.putExtra("config", Gson().toJson(
                    BuilderConfig(
                            title_text.text.toString(),
                            title_text.textSize.toString(),
                            title_text.currentTextColor.toHex(),
                            imageUrl,
                            main_image.width.toString(),
                            main_image.height.toString(),
                            main_image.scaleType.toString(),
                            copy_text.text.toString(),
                            copy_text.textSize.toString(),
                            copy_text.currentTextColor.toHex(),
                            send_button.text.toString(),
                            send_button.currentTextColor.toHex(),
                            (send_button.background as ColorDrawable).color.toHex(),
                            number_edit_text.currentTextColor.toHex(),
                            (number_text_input_layout.background as ColorDrawable).color
                                    .toHex(),
                            num_pad_0.currentTextColor.toHex(),
                            (layout_number_pad.background as ColorDrawable).color.toHex(),
                            (container_linear_layout.background as ColorDrawable).color
                                    .toHex(),
                            pin,
                            message,
                            BuildConfig.MESSAGING_URL_BASE,
                            BuildConfig.MESSAGING_URL_PATH,
                            BuildConfig.BUILDER_URL_BASE,
                            BuildConfig.BUILDER_URL_PATH
                    )))
            startActivity(builderIntent)
        }
    }

    override fun showConfigView() {
        RemixerFragment.newInstance().showRemixer(supportFragmentManager,
                RemixerFragment.REMIXER_TAG)
    }

    private var progressDialog: ProgressDialog? = null
    private var errorDialog: AlertDialog? = null

    private var message = BuildConfig.MESSAGE
    private var imageUrl = BuildConfig.IMAGE_URL
    private var pin = BuildConfig.PIN
    private var phoneNumber: String? = null

    private var adminMode: Boolean = false
    private var pinEditText: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)
        AndroidInjection.inject(this)

        hideSystemUI()

        send_button.setOnClickListener {
            sendMessagePresenter.sendMessage(phoneNumber!!, message)
        }

        number_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
            }

            override fun beforeTextChanged(number: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(number: CharSequence, p1: Int, p2: Int, p3: Int) {
                val isValid = Patterns.PHONE.matcher(number).matches() &&
                        !PhoneNumberUtils.isEmergencyNumber(number.toString());
                send_button.isEnabled = isValid

                if (isValid) phoneNumber = number.toString()
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

        var blankClickCount = 0
        num_pad_blank.setOnClickListener {
            if (++blankClickCount == 7) {
                showConfigButton()
                blankClickCount = 0
            }
        }

        config_button.setOnClickListener {
            if (adminMode) {
                showAdminView()
            } else {
                val view = layoutInflater.inflate(R.layout.dialog_pin, send_message_layout, false)

                pinEditText = view.findViewById(R.id.pin_edit_text)

                val dialog = AlertDialog.Builder(this).setView(view)
                        .setTitle(getString(R.string.dialog_pin_title))
                        .setNegativeButton(getString(R.string.dialog_pin_negative_button),
                                { dialogInterface, _ ->
                                    dialogInterface.cancel()
                                    hideConfigButton()
                                })
                        .create()

                dialog.show()

                view.findViewById<Button>(R.id.submit_pin_button).setOnClickListener {
                    if (sendMessagePresenter.submitPin(pinEditText?.text.toString())) {
                        adminMode = true
                        dialog.cancel()
                    }
                }
            }
        }

        RemixerBinder.bind(this)
    }

    private fun showConfigButton() {
        num_pad_blank.visibility = GONE
        config_button.visibility = VISIBLE
    }

    private fun hideConfigButton() {
        config_button.visibility = GONE
        num_pad_blank.visibility = VISIBLE
    }

    override fun onClick(view: View?) {
        number_edit_text.text.append((view as TextView).text)
    }

    override fun onBackPressed() {
        // prevent user from exiting app via the back button
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

    // Remixer UI controls

    @StringVariableMethod(title = "Title Text", initialValue = BuildConfig.TITLE_TEXT)
    fun setTitleText(text: String?) {
        if (text != null) {
            title_text.text = text
        }
    }

    @RangeVariableMethod(title = "Title Text Size", initialValue = BuildConfig.TITLE_TEXT_SIZE,
            minValue = 12F, maxValue = 120F)
    fun setTitleTextSize(textSize: Float?) {
        if (textSize != null) {
            title_text.setTextSize(COMPLEX_UNIT_PX, textSize)
        }
    }

    @ColorListVariableMethod(title = "Title Text Color",
            initialValue = BuildConfig.TITLE_TEXT_COLOR, limitedToValues = intArrayOf(
            0xFF000000.toInt(),
            0xFFE91E63.toInt(),
            0xFF9C27B0.toInt(),
            0xFF673AB7.toInt(),
            0xFF3F51B5.toInt(),
            0xFF2196F3.toInt(),
            0xFF03A9F4.toInt(),
            0xFF00BCD4.toInt(),
            0xFF009688.toInt(),
            0xFF4CAF50.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFFEB3B.toInt(),
            0xFFFFC107.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFF9800.toInt(),
            0xFFFF5722.toInt(),
            0xFF9E9E9E.toInt(),
            0xFF607D8B.toInt(),
            0xFF424242.toInt(),
            0xFF37474F.toInt(),
            0xFF212121.toInt(),
            0xFF263238.toInt(),
            0xFFFFFFFF.toInt()
    ))
    fun setTitleTextColor(color: Int?) {
        if (color != null) {
            title_text.setTextColor(color)
        }
    }

    @StringVariableMethod(title = "Image URL", initialValue = BuildConfig.IMAGE_URL)
    fun setMainImageUrl(url: String?) {
        if (!TextUtils.isEmpty(url)) {
            imageUrl = url!!
            Glide.with(this).load(imageUrl).apply(centerCropTransform()).into(main_image)
        }
    }

    @RangeVariableMethod(title = "Image Width", initialValue = BuildConfig.IMAGE_WIDTH,
            minValue = 0F, maxValue = 1200F)
    fun setMainImageWidth(width: Float?) {
        if (width != null) {
            main_image.layoutParams.width = width.toInt()
            main_image.requestLayout()
            Glide.with(this).clear(main_image)
            Glide.with(this).load(imageUrl).into(main_image)
        }
    }

    @RangeVariableMethod(title = "Image Height", initialValue = BuildConfig.IMAGE_HEIGHT,
            minValue = 0F, maxValue = 1200F)
    fun setMainImageHeight(height: Float?) {
        if (height != null) {
            main_image.layoutParams.height = height.toInt()
            main_image.requestLayout()
            Glide.with(this).clear(main_image)
            Glide.with(this).load(imageUrl).into(main_image)
        }
    }

    @StringListVariableMethod(title = "Image Scale Type",
            initialValue = BuildConfig.IMAGE_SCALE_TYPE, limitedToValues = arrayOf(
            "CENTER",
            "CENTER_CROP",
            "CENTER_INSIDE",
            "FIT_CENTER",
            "FIT_XY"
    ))
    fun setMainImageScaleType(type: String?) {
        val scaleType: ImageView.ScaleType? = when (type) {
            "CENTER" -> ImageView.ScaleType.CENTER
            "CENTER_CROP" -> ImageView.ScaleType.CENTER_CROP
            "CENTER_INSIDE" -> ImageView.ScaleType.CENTER_INSIDE
            "FIT_CENTER" -> ImageView.ScaleType.FIT_CENTER
            "FIT_XY" -> ImageView.ScaleType.FIT_XY
            else -> {
                ImageView.ScaleType.CENTER
            }
        }
        main_image.scaleType = scaleType
        main_image.requestLayout()
        Glide.with(this).clear(main_image)
        Glide.with(this).load(imageUrl).into(main_image)
    }

    @StringVariableMethod(title = "Copy Text", initialValue = BuildConfig.COPY_TEXT)
    fun setCopyText(text: String?) {
        if (text != null) {
            copy_text.text = text
        }
    }

    @RangeVariableMethod(title = "Copy Text Size", initialValue = BuildConfig.COPY_TEXT_SIZE,
            minValue = 12F, maxValue = 120F)
    fun setCopyTextSize(textSize: Float?) {
        if (textSize != null) {
            copy_text.setTextSize(COMPLEX_UNIT_PX, textSize)
        }
    }

    @ColorListVariableMethod(title = "Copy Text Color", initialValue = BuildConfig.COPY_TEXT_COLOR,
            limitedToValues = intArrayOf(
                    0xFF000000.toInt(),
                    0xFFE91E63.toInt(),
                    0xFF9C27B0.toInt(),
                    0xFF673AB7.toInt(),
                    0xFF3F51B5.toInt(),
                    0xFF2196F3.toInt(),
                    0xFF03A9F4.toInt(),
                    0xFF00BCD4.toInt(),
                    0xFF009688.toInt(),
                    0xFF4CAF50.toInt(),
                    0xFF8BC34A.toInt(),
                    0xFFFFEB3B.toInt(),
                    0xFFFFC107.toInt(),
                    0xFF8BC34A.toInt(),
                    0xFFFF9800.toInt(),
                    0xFFFF5722.toInt(),
                    0xFF9E9E9E.toInt(),
                    0xFF607D8B.toInt(),
                    0xFF424242.toInt(),
                    0xFF37474F.toInt(),
                    0xFF212121.toInt(),
                    0xFF263238.toInt(),
                    0xFFFFFFFF.toInt()
            ))
    fun setCopyTextColor(color: Int?) {
        if (color != null) {
            copy_text.setTextColor(color)
        }
    }

    @StringVariableMethod(title = "Send Button Text", initialValue = BuildConfig.SEND_BUTTON_TEXT)
    fun setSendButtonText(text: String?) {
        if (text != null) {
            send_button.text = text
        }
    }

    @ColorListVariableMethod(title = "Send Button Text Color",
            initialValue = BuildConfig.SEND_BUTTON_TEXT_COLOR, limitedToValues = intArrayOf(
            0xFF000000.toInt(),
            0xFFE91E63.toInt(),
            0xFF9C27B0.toInt(),
            0xFF673AB7.toInt(),
            0xFF3F51B5.toInt(),
            0xFF2196F3.toInt(),
            0xFF03A9F4.toInt(),
            0xFF00BCD4.toInt(),
            0xFF009688.toInt(),
            0xFF4CAF50.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFFEB3B.toInt(),
            0xFFFFC107.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFF9800.toInt(),
            0xFFFF5722.toInt(),
            0xFF9E9E9E.toInt(),
            0xFF607D8B.toInt(),
            0xFF424242.toInt(),
            0xFF37474F.toInt(),
            0xFF212121.toInt(),
            0xFF263238.toInt(),
            0xFFFFFFFF.toInt()
    ))
    fun setSendButtonTextColor(color: Int?) {
        if (color != null) {
            send_button.setTextColor(color)
        }
    }

    @ColorListVariableMethod(title = "Send Message Button Color",
            initialValue = BuildConfig.SEND_BUTTON_BACKGROUND_COLOR, limitedToValues = intArrayOf(
            0xFFCCCCCC.toInt(),
            0xFF000000.toInt(),
            0xFFE91E63.toInt(),
            0xFF9C27B0.toInt(),
            0xFF673AB7.toInt(),
            0xFF3F51B5.toInt(),
            0xFF2196F3.toInt(),
            0xFF03A9F4.toInt(),
            0xFF00BCD4.toInt(),
            0xFF009688.toInt(),
            0xFF4CAF50.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFFEB3B.toInt(),
            0xFFFFC107.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFF9800.toInt(),
            0xFFFF5722.toInt(),
            0xFF9E9E9E.toInt(),
            0xFF607D8B.toInt(),
            0xFF424242.toInt(),
            0xFF37474F.toInt(),
            0xFF212121.toInt(),
            0xFF263238.toInt(),
            0xFFFFFFFF.toInt()
    ))
    fun setSendButtonBackgroundColor(color: Int?) {
        if (color != null) {
            send_button.setBackgroundColor(color)
        }
    }

    @ColorListVariableMethod(title = "Phone Number Background Color",
            initialValue = BuildConfig.PHONE_INPUT_BACKGROUND_COLOR, limitedToValues = intArrayOf(
            0xFF000000.toInt(),
            0xFFE91E63.toInt(),
            0xFF9C27B0.toInt(),
            0xFF673AB7.toInt(),
            0xFF3F51B5.toInt(),
            0xFF2196F3.toInt(),
            0xFF03A9F4.toInt(),
            0xFF00BCD4.toInt(),
            0xFF009688.toInt(),
            0xFF4CAF50.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFFEB3B.toInt(),
            0xFFFFC107.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFF9800.toInt(),
            0xFFFF5722.toInt(),
            0xFF9E9E9E.toInt(),
            0xFF607D8B.toInt(),
            0xFF424242.toInt(),
            0xFF37474F.toInt(),
            0xFF212121.toInt(),
            0xFF263238.toInt(),
            0xFFFFFFFF.toInt()
    ))
    fun setPhoneNumberBackgroundTextColor(color: Int?) {
        if (color != null) {
            number_text_input_layout.setBackgroundColor(color)
        }
    }

    @ColorListVariableMethod(title = "Phone Number Input Text Color",
            initialValue = BuildConfig.PHONE_INPUT_TEXT_COLOR, limitedToValues = intArrayOf(
            0xFF000000.toInt(),
            0xFFE91E63.toInt(),
            0xFF9C27B0.toInt(),
            0xFF673AB7.toInt(),
            0xFF3F51B5.toInt(),
            0xFF2196F3.toInt(),
            0xFF03A9F4.toInt(),
            0xFF00BCD4.toInt(),
            0xFF009688.toInt(),
            0xFF4CAF50.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFFEB3B.toInt(),
            0xFFFFC107.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFF9800.toInt(),
            0xFFFF5722.toInt(),
            0xFF9E9E9E.toInt(),
            0xFF607D8B.toInt(),
            0xFF424242.toInt(),
            0xFF37474F.toInt(),
            0xFF212121.toInt(),
            0xFF263238.toInt(),
            0xFFFFFFFF.toInt()
    ))
    fun setPhoneNumberInputTextColor(color: Int?) {
        if (color != null) {
            number_edit_text.setTextColor(color)
        }
    }

    @ColorListVariableMethod(title = "Number Pad Text Color",
            initialValue = BuildConfig.NUM_PAD_TEXT_COLOR, limitedToValues = intArrayOf(
            0xFF000000.toInt(),
            0xFFE91E63.toInt(),
            0xFF9C27B0.toInt(),
            0xFF673AB7.toInt(),
            0xFF3F51B5.toInt(),
            0xFF2196F3.toInt(),
            0xFF03A9F4.toInt(),
            0xFF00BCD4.toInt(),
            0xFF009688.toInt(),
            0xFF4CAF50.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFFEB3B.toInt(),
            0xFFFFC107.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFF9800.toInt(),
            0xFFFF5722.toInt(),
            0xFF9E9E9E.toInt(),
            0xFF607D8B.toInt(),
            0xFF424242.toInt(),
            0xFF37474F.toInt(),
            0xFF212121.toInt(),
            0xFF263238.toInt(),
            0xFFFFFFFF.toInt()
    ))
    fun setPhoneNumberPadTextColor(color: Int?) {
        if (color != null) {
            num_pad_1.setTextColor(color)
            num_pad_2.setTextColor(color)
            num_pad_3.setTextColor(color)
            num_pad_4.setTextColor(color)
            num_pad_5.setTextColor(color)
            num_pad_6.setTextColor(color)
            num_pad_7.setTextColor(color)
            num_pad_8.setTextColor(color)
            num_pad_9.setTextColor(color)
            num_pad_0.setTextColor(color)
            num_pad_del.setTextColor(color)
        }
    }

    @ColorListVariableMethod(title = "Number Pad Background Color",
            initialValue = BuildConfig.NUM_PAD_BACKGROUND_COLOR, limitedToValues = intArrayOf(
            0xFFFFFFFF.toInt(),
            0xFFE91E63.toInt(),
            0xFF9C27B0.toInt(),
            0xFF673AB7.toInt(),
            0xFF3F51B5.toInt(),
            0xFF2196F3.toInt(),
            0xFF03A9F4.toInt(),
            0xFF00BCD4.toInt(),
            0xFF009688.toInt(),
            0xFF4CAF50.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFFEB3B.toInt(),
            0xFFFFC107.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFF9800.toInt(),
            0xFFFF5722.toInt(),
            0xFF9E9E9E.toInt(),
            0xFF607D8B.toInt(),
            0xFF424242.toInt(),
            0xFF37474F.toInt(),
            0xFF212121.toInt(),
            0xFF263238.toInt(),
            0xFF000000.toInt()
    ))
    fun setPhoneNumberPadBackgroundColor(color: Int?) {
        if (color != null && layout_number_pad != null) {
            layout_number_pad.setBackgroundColor(color)
        }
    }

    @ColorListVariableMethod(title = "Main Background Color",
            initialValue = BuildConfig.BACKGROUND_COLOR, limitedToValues = intArrayOf(
            0xFFFFFFFF.toInt(),
            0xFFE91E63.toInt(),
            0xFF9C27B0.toInt(),
            0xFF673AB7.toInt(),
            0xFF3F51B5.toInt(),
            0xFF2196F3.toInt(),
            0xFF03A9F4.toInt(),
            0xFF00BCD4.toInt(),
            0xFF009688.toInt(),
            0xFF4CAF50.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFFEB3B.toInt(),
            0xFFFFC107.toInt(),
            0xFF8BC34A.toInt(),
            0xFFFF9800.toInt(),
            0xFFFF5722.toInt(),
            0xFF9E9E9E.toInt(),
            0xFF607D8B.toInt(),
            0xFF424242.toInt(),
            0xFF37474F.toInt(),
            0xFF212121.toInt(),
            0xFF263238.toInt(),
            0xFF000000.toInt()
    ))
    fun setBackgroundColor(color: Int?) {
        if (color != null) {
            container_linear_layout.setBackgroundColor(color)
        }
    }

    @StringVariableMethod(title = "Message", initialValue = BuildConfig.MESSAGE)
    fun setMessageText(s: String?) {
        if (s != null) {
            message = s
        }
    }

    @StringVariableMethod(title = "PIN", initialValue = BuildConfig.PIN)
    fun setPin(s: String?) {
        if (s != null) {
            pin = s
        }
    }
}

fun Int.toHex(): String {
    return "0xFF" + String.format("%06X", 0xFFFFFF and this)
}