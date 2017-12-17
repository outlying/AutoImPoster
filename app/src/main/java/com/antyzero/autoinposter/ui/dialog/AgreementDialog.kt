package com.antyzero.autoinposter.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatDialogFragment
import com.antyzero.autoinposter.domain.dsl.TAG


class AgreementDialog : AppCompatDialogFragment() {

    private lateinit var agreementResult: AgreementResult

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        attachAgreementResult(activity)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        attachAgreementResult(activity)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return AlertDialog.Builder(activity)
                .setPositiveButton("I agree", { dialog, _ ->
                    agreementResult.agreedToUserAgreement()
                    dialog.cancel()
                })
                .setNegativeButton("Disagree", { _, _ -> activity!!.finish() })
                .create()
    }

    /**
     * Get AgreementResult from context
     *
     * @param context for casting, however null value is allowed it will throw exception immediately
     */
    private fun attachAgreementResult(context: Context?) {
        if (context == null) {
            throw IllegalStateException("Attaching null object is not allowed")
        }

        try {
            agreementResult = context as AgreementResult
        } catch (e: Exception) {
            throw IllegalStateException("${context.javaClass.simpleName} have to implement AgreementResult interface")
        }
    }

    companion object {

        fun show(fragmentActivity: FragmentActivity) {
            show(fragmentActivity.supportFragmentManager)
        }

        private fun show(fragmentManager: android.support.v4.app.FragmentManager) {
            AgreementDialog().show(fragmentManager, TAG)
        }
    }

    /**
     * Callback for user positive response
     */
    interface AgreementResult {

        fun agreedToUserAgreement()
    }
}