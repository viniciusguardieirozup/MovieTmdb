package com.example.movietmdb.features.description.ui

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.example.movietmdb.R
import com.example.movietmdb.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*


class ActionBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var mBehavior: BottomSheetBehavior<View>
    private lateinit var binding: BottomSheetBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val view =
            View.inflate(context, R.layout.bottom_sheet, null)

        val params =
            bottomSheet.layoutParams as LinearLayout.LayoutParams
        params.height = getScreenHeight()
        bottomSheet.layoutParams = params
        dialog.setContentView(view)
        dialog.setOnShowListener {
            setupBottomSheet(it)
        }
        mBehavior = BottomSheetBehavior.from(view.parent as View)

        return dialog
    }


    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        )
            ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onStart() {
        super.onStart()
        mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().getDisplayMetrics().heightPixels
    }


}