package az.khayalsharifli.bankrespublika.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import az.khayalsharifli.bankrespublika.R
import az.khayalsharifli.bankrespublika.domain.error.NetworkError
import az.khayalsharifli.bankrespublika.domain.error.ServerError
import az.khayalsharifli.bankrespublika.tools.NavigationCommand
import az.khayalsharifli.bankrespublika.tools.SingleLiveEvent
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    private val _commonEffect = SingleLiveEvent<BaseEffect>()
    val commonEffect: LiveData<BaseEffect>
        get() = _commonEffect

    protected fun <P, R, U : BaseSyncUseCase<P, R>> U.launch(
        param: P,
        block: Block<R> = {},
    ) {
        viewModelScope.launch {
            val request = BaseSyncUseCase.Request<R>().apply(block)

            val proxy: Block<R> = {
                onStart = {
                    request.onStart?.invoke()
                }
                onSuccess = {
                    request.onSuccess(it)
                }
                onCancel = {
                    request.onCancel?.invoke(it)
                }
                onTerminate = {
                    request.onTerminate
                }
                onError = {
                    request.onError?.invoke(it) ?: handleError(it)
                }
            }
            execute(param, proxy)
        }
    }

    fun navigate(directions: NavDirections, extras: Navigator.Extras? = null) {
        navigationCommands.postValue(NavigationCommand.To(directions, extras))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }

    private fun handleError(t: Throwable) {
        when (t) {
            is ServerError.ServerIsDown -> _commonEffect.postValue(BackEndError())
            is ServerError.Unexpected -> _commonEffect.postValue(MessageError(R.string.error_unexpected))
            is NetworkError -> _commonEffect.postValue(NoInternet())
            else -> _commonEffect.postValue(UnknownError(cause = t))
        }
    }

}