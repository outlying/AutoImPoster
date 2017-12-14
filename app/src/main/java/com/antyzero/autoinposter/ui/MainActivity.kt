package com.antyzero.autoinposter.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.antyzero.autoinposter.R
import com.antyzero.autoinposter.ui.dialog.AgreementDialog

class MainActivity : AppCompatActivity(), AgreementDialog.AgreementResult {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO check persistent state for user agreed to conditions
        AgreementDialog.show(this)

        // TODO Call thi on text message options enabling
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                // inform user about details
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), 123)
            }
        }
    }

    override fun agreedToUserAgreement() {
        // TODO update persistent state indicating that user agreed
    }
}
