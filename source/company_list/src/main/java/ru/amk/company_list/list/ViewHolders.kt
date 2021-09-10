package ru.amk.company_list.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(position: Int)
}


class EmptyViewHolder(itemView: View) :
    BaseViewHolder(itemView) {
    override fun onBind(position: Int) {

    }

}