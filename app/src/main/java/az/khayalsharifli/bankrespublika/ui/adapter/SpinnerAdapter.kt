package az.khayalsharifli.bankrespublika.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import az.khayalsharifli.bankrespublika.R

class SpinnerAdapter(context: Context, var listSpinner: List<SpinnerItem>) :
    ArrayAdapter<SpinnerItem>(context, 0, listSpinner) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return view(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return view(position, convertView, parent)
    }

    private fun view(position: Int, convertView: View?, parent: ViewGroup): View {
        val spinnerItem = listSpinner[position]
        val itemView = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.spinner_item, parent, false
        )

        spinnerItem.let {
            val text = itemView.findViewById<TextView>(R.id.text_currency)
            val image = itemView.findViewById<ImageView>(R.id.image_currency)

            text.text = it.currency
            image.setImageResource(it.image)
        }
        return itemView
    }
}

data class SpinnerItem(val currency: String, @DrawableRes val image: Int)