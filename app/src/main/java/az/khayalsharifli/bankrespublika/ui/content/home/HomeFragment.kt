package az.khayalsharifli.bankrespublika.ui.content.home

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import az.khayalsharifli.bankrespublika.R
import az.khayalsharifli.bankrespublika.base.BaseFragment
import az.khayalsharifli.bankrespublika.databinding.FragmentHomeBinding
import az.khayalsharifli.bankrespublika.ui.adapter.SpinnerAdapter
import az.khayalsharifli.bankrespublika.ui.adapter.SpinnerItem
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val kClass: KClass<HomeViewModel>
        get() = HomeViewModel::class

    private var spinnerList = ArrayList<SpinnerItem>()

    override val bindViews: FragmentHomeBinding.() -> Unit = {

        // Close System Keyboard
        input1.inputType = InputType.TYPE_NULL
        input2.inputType = InputType.TYPE_NULL

        number1.setOnClickListener {
            addNumber("1")
        }
        number2.setOnClickListener {
            addNumber("2")
        }
        number3.setOnClickListener {
            addNumber("3")
        }
        number4.setOnClickListener {
            addNumber("4")
        }
        number5.setOnClickListener {
            addNumber("5")
        }
        number6.setOnClickListener {
            addNumber("6")
        }
        number7.setOnClickListener {
            addNumber("7")
        }
        number8.setOnClickListener {
            addNumber("8")
        }
        number9.setOnClickListener {
            addNumber("9")
        }
        number0.setOnClickListener {
            addNumber("0")
        }
        dot.setOnClickListener {
            addNumber(".")
        }
        clearSymbol.setOnClickListener {
            clearSymbol(input1, input2)
        }

        lifecycleScope.launch {
            viewModel.moneyResponse.collect { list ->
                if (list.isNotEmpty()) {
                    val selectedBank = list[8]
                    spinnerList = arrayListOf(
                        SpinnerItem(
                            currency = selectedBank.cash.eur.code,
                            image = R.drawable.eur_flag,
                            buyRate = selectedBank.cash.eur.buy,
                            sellRate = selectedBank.cash.eur.sell
                        ),
                        SpinnerItem(
                            currency = selectedBank.cash.usd.code,
                            image = R.drawable.usd_flag,
                            buyRate = selectedBank.cash.usd.buy,
                            sellRate = selectedBank.cash.usd.sell
                        ),
                        SpinnerItem(
                            currency = selectedBank.cash.rub.code,
                            image = R.drawable.rub_flag,
                            buyRate = selectedBank.cash.rub.buy,
                            sellRate = selectedBank.cash.rub.sell
                        )
                    )

                    viewModel.selectedCurrencyCodes.value =
                        HomeViewModel.SelectedCurrencyCodes(
                            sellRate = spinnerList[0].sellRate,
                            buyRate = spinnerList[1].buyRate
                        )
                    // Integration Spinner
                    integrationSpinnerSell()
                    integrationSpinnerBuy()
                    spinnerBuy.setSelection(1)
                }
            }
        }

        viewModel.selectedCurrencyCodes.observe(viewLifecycleOwner) { item ->
            buttonConvert.setOnClickListener {
                if (item.sellRate.isNotEmpty() && item.buyRate.isNotEmpty()) {
                    convertedAndSet(item.sellRate, item.buyRate)
                }
            }
        }
    }

    private fun addNumber(number: String) {
        val stringBuilder = StringBuilder()
        if (binding.input1.isFocused) {
            val oldText = binding.input1.text
            stringBuilder.append(oldText).append(number)
            binding.input1.setText(stringBuilder)
        } else if (binding.input2.isFocused) {
            val oldText = binding.input2.text
            stringBuilder.append(oldText).append(number)
            binding.input2.setText(stringBuilder)
        }
    }

    private fun clearSymbol(input1: EditText, input2: EditText) {
        if (input1.isFocused) {
            val oldText = input1.text
            if (input1.length() != 0) input1.setText(oldText.substring(0, input1.length() - 1))
        } else if (input2.isFocused) {
            val oldText = input2.text
            if (input2.length() != 0) input2.setText(oldText.substring(0, input2.length() - 1))
        }
    }

    private fun integrationSpinnerSell() {
        val adapter = SpinnerAdapter(
            requireContext(),
            spinnerList
        )
        binding.spinnerSell.adapter = adapter
        binding.spinnerSell.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val latestModel = viewModel.selectedCurrencyCodes.value
                viewModel.selectedCurrencyCodes.value =
                    latestModel?.copy(sellRate = spinnerList[position].sellRate)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun integrationSpinnerBuy() {
        val adapter = SpinnerAdapter(
            requireContext(),
            spinnerList
        )
        binding.spinnerBuy.adapter = adapter
        binding.spinnerBuy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val latestModel = viewModel.selectedCurrencyCodes.value
                viewModel.selectedCurrencyCodes.value =
                    latestModel?.copy(buyRate = spinnerList[position].buyRate)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun convertedAndSet(sellRate: String, buyRate: String) {
        if (binding.input1.text.isNotEmpty()) {
            val convertedPrice =
                (binding.input1.text.toString()
                    .toFloat() * sellRate.toFloat()) / buyRate.toFloat()
            binding.input2.setText(convertedPrice.toString())
        }
    }
}