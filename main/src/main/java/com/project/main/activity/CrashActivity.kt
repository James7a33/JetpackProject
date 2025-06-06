package com.project.main.activity

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.frame.base.ui.BaseVBActivity
import com.frame.base.vm.BaseViewModel
import com.frame.common.ext.setOnclick
import com.james.main.databinding.ActivityCrashErrorBinding
import com.tools.toast.ext.toastShort
import com.main.res.R as Rs


/**
 * @Author: james * @Date: 2024/4/7 16:03
 * @Description:
 */
class CrashActivity : BaseVBActivity<BaseViewModel, ActivityCrashErrorBinding>() {

    private val config by lazy { CustomActivityOnCrash.getConfigFromIntent(intent) }
    override fun titleBar(): String = ""
    override fun isTitleBarShow(): Boolean = false

    override fun initView(savedInstanceState: Bundle?) {
        bind.errorActivityMoreInfoButton.visibility =
            if (config!!.isShowErrorDetails) View.VISIBLE else View.GONE
        val defaultErrorActivityDrawableId = config!!.errorDrawable
        if (defaultErrorActivityDrawableId != null) {
            bind.errorActivityImage.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources, defaultErrorActivityDrawableId, theme
                )
            )
        }
    }

    override fun onBindViewClick() {
        setOnclick(
            bind.errorActivityRestartButton,
            bind.errorActivityMoreInfoButton
        ) {
            when (it) {
                bind.errorActivityRestartButton -> {
                    val isRestart =
                        config!!.isShowRestartButton && config!!.restartActivityClass != null
                    bind.errorActivityRestartButton.setText(if (isRestart) Rs.string.error_activity_restart_app else Rs.string.error_activity_close_app)
                    if (isRestart) CustomActivityOnCrash.restartApplication(
                        this@CrashActivity,
                        config!!
                    ) else CustomActivityOnCrash.closeApplication(this@CrashActivity, config!!)
                }

                bind.errorActivityMoreInfoButton -> {
                    val dialog = AlertDialog.Builder(this@CrashActivity)
                        .setTitle(Rs.string.error_activity_error_details_title)
                        .setMessage(
                            CustomActivityOnCrash.getAllErrorDetailsFromIntent(
                                this@CrashActivity,
                                intent
                            )
                        )
                        .setPositiveButton(
                            Rs.string.error_activity_error_details_close,
                            null
                        )
                        .setNeutralButton(
                            Rs.string.error_activity_error_details_copy
                        ) { _: DialogInterface?, _: Int ->
                            copyErrorToClipboard()
                            Rs.string.error_activity_error_details_copy.toastShort()

                        }
                        .show()
                    val textView = dialog.findViewById<View>(android.R.id.message) as TextView
                    textView.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(Rs.dimen.sp_14)
                    )
                }
            }
        }
    }

    private fun copyErrorToClipboard() {
        val errorInformation =
            CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@CrashActivity, intent)
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            getString(Rs.string.error_activity_error_details_clipboard_label),
            errorInformation
        )
        clipboard.setPrimaryClip(clip)
    }
}
