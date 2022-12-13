package com.ls.iusta.ui.qrscanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.ls.iusta.R
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityScannerBinding
import com.ls.iusta.extension.startWithAnimation
import com.ls.iusta.presentation.viewmodel.ScannerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerActivity : BaseActivity<ActivityScannerBinding>() {

    override val viewModel: ScannerViewModel by viewModels()

    override fun getViewBinding() = ActivityScannerBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()
        binding.barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeView.pause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.barcodeView.decodeContinuous(callback)
    }

    override fun initUI() {
        with(binding) {
            nextButton.setOnClickListener {
                val intent = Intent()
                intent.putExtra("ticket_id", ticketIdScanned.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun initViewModel() {

    }

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            checkTicketId(result.text)
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {

        }
    }

    private fun checkTicketId(text: String?) {
        with(binding) {
            ticketIdScanned.setText(text)
        }
    }

    companion object {
        fun startActivity(activity: Activity?, requestCode: Int?) {
            val intent = Intent(activity, ScannerActivity::class.java)
            activity?.startWithAnimation(intent, false, requestCode)
        }
    }
}