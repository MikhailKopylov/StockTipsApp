package ru.amk.company_list.list

import android.app.Activity
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ru.amk.company_list.R

class HeaderViewState(private val activity: Activity) {

    private val sortByNameTextView: TextView by lazy { activity.findViewById(R.id.sort_by_name_text_view) }
    private val sortBySecidTextView: TextView by lazy { activity.findViewById(R.id.sort_by_secid_text_view) }
    private val sortByPriceTextView: TextView by lazy { activity.findViewById(R.id.sort_by_price_text_view) }
    private val nameRightImageButton: ImageButton by lazy { activity.findViewById(R.id.name_right_order_image_button) }
    private val nameReverseImageButton: ImageButton by lazy { activity.findViewById(R.id.name_reverse_order_image_button) }
    private val secIdRightImageButton: ImageButton by lazy { activity.findViewById(R.id.sec_id_right_order_image_button) }
    private val secIdReverseImageButton: ImageButton by lazy { activity.findViewById(R.id.sec_id_reverse_order_image_button) }
    private val priceRightImageButton: ImageButton by lazy { activity.findViewById(R.id.price_right_order_image_button) }
    private val priceReverseImageButton: ImageButton by lazy { activity.findViewById(R.id.price_reverse_order_image_button) }
    private val favoriteUpImageView: ImageView by lazy { activity.findViewById(R.id.favorite_up_image_view) }
    private val favoriteUpCheckBox: CheckBox by lazy { activity.findViewById(R.id.favorite_to_up_switch) }

    fun nameRight() {
        sortByNameTextView.setTextColor(setColor(R.color.dark))
        sortBySecidTextView.setTextColor(setColor(R.color.gray600))
        sortByPriceTextView.setTextColor(setColor(R.color.gray600))
        nameRightImageButton.setImageResource(R.drawable.select_right)
        nameReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        secIdRightImageButton.setImageResource(R.drawable.not_select_right)
        secIdReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        priceRightImageButton.setImageResource(R.drawable.not_select_right)
        priceReverseImageButton.setImageResource(R.drawable.not_select_reverse)
    }

    fun nameRevers() {
        sortByNameTextView.setTextColor(setColor(R.color.dark))
        sortBySecidTextView.setTextColor(setColor(R.color.gray600))
        sortByPriceTextView.setTextColor(setColor(R.color.gray600))
        nameRightImageButton.setImageResource(R.drawable.not_select_right)
        nameReverseImageButton.setImageResource(R.drawable.select_reverse)
        secIdRightImageButton.setImageResource(R.drawable.not_select_right)
        secIdReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        priceRightImageButton.setImageResource(R.drawable.not_select_right)
        priceReverseImageButton.setImageResource(R.drawable.not_select_reverse)
    }

    fun secIdRight() {
        sortByNameTextView.setTextColor(setColor(R.color.gray600))
        sortBySecidTextView.setTextColor(setColor(R.color.dark))
        sortByPriceTextView.setTextColor(setColor(R.color.gray600))
        nameRightImageButton.setImageResource(R.drawable.not_select_right)
        nameReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        secIdRightImageButton.setImageResource(R.drawable.select_right)
        secIdReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        priceRightImageButton.setImageResource(R.drawable.not_select_right)
        priceReverseImageButton.setImageResource(R.drawable.not_select_reverse)
    }

    fun secIdReverse() {
        sortByNameTextView.setTextColor(setColor(R.color.gray600))
        sortBySecidTextView.setTextColor(setColor(R.color.dark))
        sortByPriceTextView.setTextColor(setColor(R.color.gray600))
        nameRightImageButton.setImageResource(R.drawable.not_select_right)
        nameReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        secIdRightImageButton.setImageResource(R.drawable.not_select_right)
        secIdReverseImageButton.setImageResource(R.drawable.select_reverse)
        priceRightImageButton.setImageResource(R.drawable.not_select_right)
        priceReverseImageButton.setImageResource(R.drawable.not_select_reverse)
    }

    fun priceRight() {
        sortByNameTextView.setTextColor(setColor(R.color.gray600))
        sortBySecidTextView.setTextColor(setColor(R.color.gray600))
        sortByPriceTextView.setTextColor(setColor(R.color.dark))
        nameRightImageButton.setImageResource(R.drawable.not_select_right)
        nameReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        secIdRightImageButton.setImageResource(R.drawable.not_select_right)
        secIdReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        priceRightImageButton.setImageResource(R.drawable.select_right)
        priceReverseImageButton.setImageResource(R.drawable.not_select_reverse)
    }

    fun priceReverse(){
        sortByNameTextView.setTextColor(setColor(R.color.gray600))
        sortBySecidTextView.setTextColor(setColor(R.color.gray600))
        sortByPriceTextView.setTextColor(setColor(R.color.dark))
        nameRightImageButton.setImageResource(R.drawable.not_select_right)
        nameReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        secIdRightImageButton.setImageResource(R.drawable.not_select_right)
        secIdReverseImageButton.setImageResource(R.drawable.not_select_reverse)
        priceRightImageButton.setImageResource(R.drawable.not_select_right)
        priceReverseImageButton.setImageResource(R.drawable.select_reverse)

    }

    private fun setColor(@ColorRes id: Int) = ContextCompat.getColor(
        activity.applicationContext,
        id
    )


    fun selectFavoriteUp() {
        if (favoriteUpCheckBox.isChecked) {
            favoriteUpImageView.setImageResource(R.drawable.arrow_up_select)
        } else {
            favoriteUpImageView.setImageResource(R.drawable.arrow_up_not_select)
        }
    }

}