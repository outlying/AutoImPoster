package com.antyzero.autoinposter.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import com.antyzero.autoinposter.dsl.TAG


class AgreementDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        return AlertDialog.Builder(activity)
                .setPositiveButton("I agree", { dialog, _ -> dialog.cancel() })
                .setNegativeButton("Disagree", { _, _ -> activity!!.finish() })
                .create()
    }

    companion object {

        fun show(fragmentActivity: FragmentActivity) {
            show(fragmentActivity.supportFragmentManager)
        }

        private fun show(fragmentManager: android.support.v4.app.FragmentManager) {
            AgreementDialog().show(fragmentManager, TAG)
        }
    }
}