package ru.amk.company_list.item

import android.view.View
import android.widget.TextView
import ru.amk.company_list.R

class ItemViewImpl(rootView: View) : ItemView {

    private var companyNameTextView: TextView = rootView.findViewById(R.id.company_name_textview)
    private var secIdTextView: TextView = rootView.findViewById(R.id.sec_id_textview)

    override fun setCompanyName(name: String) {
        companyNameTextView.text = name
    }

    override fun setCompanySetId(secId: String) {
        secIdTextView.text = secId
    }
}