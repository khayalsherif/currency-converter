package az.khayalsharifli.bankrespublika.ui.content.home

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import az.khayalsharifli.bankrespublika.base.BaseFragment
import az.khayalsharifli.bankrespublika.data.local.LocalDtoItem
import az.khayalsharifli.bankrespublika.databinding.FragmentHomeBinding
import az.khayalsharifli.bankrespublika.ui.adapter.SpinnerAdapter
import az.khayalsharifli.bankrespublika.ui.factory.SpinnerFactory
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val kClass: KClass<HomeViewModel>
        get() = HomeViewModel::class

    override val bindViews: FragmentHomeBinding.() -> Unit = {

        // Close System Keyboard
        input1.inputType = InputType.TYPE_NULL
        input2.inputType = InputType.TYPE_NULL

        // Integration Spinner
        integrationSpinner(spinner)
        integrationSpinner(spinner2)
        spinner2.setSelection(1)

        number1.setOnClickListener {
            addNumberForInput("1", input1, input2)
        }
        number2.setOnClickListener {
            addNumberForInput("2", input1, input2)
        }
        number3.setOnClickListener {
            addNumberForInput("3", input1, input2)
        }
        number4.setOnClickListener {
            addNumberForInput("4", input1, input2)
        }
        number5.setOnClickListener {
            addNumberForInput("5", input1, input2)
        }
        number6.setOnClickListener {
            addNumberForInput("6", input1, input2)
        }
        number7.setOnClickListener {
            addNumberForInput("7", input1, input2)
        }
        number8.setOnClickListener {
            addNumberForInput("8", input1, input2)
        }
        number9.setOnClickListener {
            addNumberForInput("9", input1, input2)
        }
        number0.setOnClickListener {
            addNumberForInput("0", input1, input2)
        }
        dot.setOnClickListener {
            addNumberForInput(".", input1, input2)
        }
        clearSymbol.setOnClickListener {
            clearNumberForInput(input1, input2)
        }

        lifecycleScope.launch {
            viewModel.moneyResponse.collect { list ->
                //Item 9 ([8]) is chosen randomly because there is no bank option in the design
                buttonConvert.setOnClickListener {
                    if (list.isNotEmpty()) {
                        coverMoney(input1, input2, spinner, spinner2, list[8])
                    }
                }
            }
        }
    }

    private fun addNumberForInput(number: String, input1: EditText, input2: EditText) {
        val stringBuilder = StringBuilder()
        if (input1.isFocused) {
            val oldText = input1.text
            stringBuilder.append(oldText).append(number)
            input1.setText(stringBuilder)
        } else {
            val oldText = input2.text
            stringBuilder.append(oldText).append(number)
            input2.setText(stringBuilder)
        }
    }

    private fun clearNumberForInput(input1: EditText, input2: EditText) {
        if (input1.isFocused) {
            val oldText = input1.text
            if (input1.length() != 0) input1.setText(oldText.substring(0, input1.length() - 1))
        } else if (input2.isFocused) {
            val oldText = input2.text
            if (input2.length() != 0) input2.setText(oldText.substring(0, input2.length() - 1))
        }
    }

    private fun integrationSpinner(spinner: Spinner) {
        val adapter = SpinnerAdapter(
            requireContext(),
            SpinnerFactory.getSpinnerList()
        )
        spinner.adapter = adapter
    }

    private fun coverMoney(
        input1: EditText,
        input2: TextView,
        spinner: Spinner,
        spinner2: Spinner,
        localDtoItem: LocalDtoItem
    ) {
        if (input1.text.isNotEmpty() && input1.isFocused) {
            //Then this buy price
            if (spinner.selectedItemPosition == 0 && spinner2.selectedItemPosition == 1) {
                //Eur To Usd
                val coveredPrice =
                    input1.text.toString().toFloat() * localDtoItem.cash.usd.buy.toFloat()
                input2.text = coveredPrice.toString()
            } else if (spinner.selectedItemPosition == 0 && spinner2.selectedItemPosition == 2) {
                //Eur To Rub
                val coveredPrice =
                    input1.text.toString().toFloat() * localDtoItem.cash.rub.buy.toFloat()
                input2.text = coveredPrice.toString()
            } else if (spinner.selectedItemPosition == 1 && spinner2.selectedItemPosition == 0) {
                //Usd To Eur
                val coveredPrice =
                    input1.text.toString().toFloat() * localDtoItem.cash.eur.buy.toFloat()
                input2.text = coveredPrice.toString()
            } else if (spinner.selectedItemPosition == 1 && spinner2.selectedItemPosition == 2) {
                //Usd To Rub
                val coveredPrice =
                    input1.text.toString().toFloat() * localDtoItem.cash.rub.buy.toFloat()
                input2.text = coveredPrice.toString()
            } else if (spinner.selectedItemPosition == 2 && spinner2.selectedItemPosition == 0) {
                //Rub To Eur
                val coveredPrice =
                    input1.text.toString().toFloat() * localDtoItem.cash.eur.buy.toFloat()
                input2.text = coveredPrice.toString()
            } else if (spinner.selectedItemPosition == 2 && spinner2.selectedItemPosition == 1) {
                //Rub To Usd
                val coveredPrice =
                    input1.text.toString().toFloat() * localDtoItem.cash.usd.buy.toFloat()
                input2.text = coveredPrice.toString()
            } else if (spinner.selectedItemPosition == spinner2.selectedItemPosition) {
                Toast.makeText(
                    requireContext(),
                    "You have selected the same currency",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

    }
}