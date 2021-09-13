package ru.amk.company_list.repository

import android.app.Activity
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.amk.company_list.R

class HeaderViewState(private val activity: Activity) {

    private val sortByNameTextView: TextView by lazy { activity.findViewById(R.id.sort_by_name_text_view) }
    private val sortBySecidTextView: TextView by lazy { activity.findViewById(R.id.sort_by_secid_text_view) }
    private val nameOrderRightImageButton: ImageButton by lazy { activity.findViewById(R.id.name_order_right_image_button) }
    private val secidOrderRightImageButton: ImageButton by lazy { activity.findViewById(R.id.secid_order_right_image_button) }
    private val nameOrderReverseImageButton: ImageButton by lazy { activity.findViewById(R.id.name_order_reverse_image_button) }
    private val secidOrderReverseImageButton: ImageButton by lazy { activity.findViewById(R.id.secid_order_reverse_image_button) }

    fun nameRight() {
        sortByNameTextView.setTextColor(
            ContextCompat.getColor(
                activity.applicationContext,
                R.color.dark
            )
        )
        sortBySecidTextView.setTextColor(
            ContextCompat.getColor(
                activity.applicationContext,
                R.color.gray600
            )
        )
        nameOrderRightImageButton.setImageResource(R.drawable.arrow_down_select)
        secidOrderRightImageButton.setImageResource(R.drawable.arrow_down_not_select)
        nameOrderReverseImageButton.setImageResource(R.drawable.arrow_up_not_select)
        secidOrderReverseImageButton.setImageResource(R.drawable.arrow_up_not_select)
    }

    fun nameRevers(){
        sortByNameTextView.setTextColor(
            ContextCompat.getColor(activity.applicationContext, R.color.dark)
        )
        sortBySecidTextView.setTextColor(
            ContextCompat.getColor(
                activity.applicationContext,
                R.color.gray600
            )
        )
        nameOrderRightImageButton.setImageResource(R.drawable.arrow_down_not_select)
        secidOrderRightImageButton.setImageResource(R.drawable.arrow_down_not_select)
        nameOrderReverseImageButton.setImageResource(R.drawable.arrow_up_select)
        secidOrderReverseImageButton.setImageResource(R.drawable.arrow_up_not_select)
    }

    fun secIdRight(){
        sortByNameTextView.setTextColor(
            ContextCompat.getColor(
                activity.applicationContext,
                R.color.gray600
            )
        )
        sortBySecidTextView.setTextColor(
            ContextCompat.getColor(
                activity.applicationContext,
                R.color.dark
            )
        )
        nameOrderRightImageButton.setImageResource(R.drawable.arrow_down_not_select)
        secidOrderRightImageButton.setImageResource(R.drawable.arrow_down_select)
        nameOrderReverseImageButton.setImageResource(R.drawable.arrow_up_not_select)
        secidOrderReverseImageButton.setImageResource(R.drawable.arrow_up_not_select)
    }

    fun secIdReverse(){
        sortByNameTextView.setTextColor(
            ContextCompat.getColor(
                activity.applicationContext,
                R.color.gray600
            )
        )
        sortBySecidTextView.setTextColor(
            ContextCompat.getColor(
                activity.applicationContext,
                R.color.dark
            )
        )
        nameOrderRightImageButton.setImageResource(R.drawable.arrow_down_not_select)
        secidOrderRightImageButton.setImageResource(R.drawable.arrow_down_not_select)
        nameOrderReverseImageButton.setImageResource(R.drawable.arrow_up_not_select)
        secidOrderReverseImageButton.setImageResource(R.drawable.arrow_up_select)
    }

}