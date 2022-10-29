package az.khayalsharifli.bankrespublika.ui.factory

import az.khayalsharifli.bankrespublika.R
import az.khayalsharifli.bankrespublika.ui.adapter.SpinnerItem

object SpinnerFactory {
    fun getSpinnerList(): ArrayList<SpinnerItem> = arrayListOf(
        SpinnerItem("Eur", R.drawable.eur_flag),
        SpinnerItem("Usd", R.drawable.usd_flag),
        SpinnerItem("Rub", R.drawable.rub_flag),
    )
}