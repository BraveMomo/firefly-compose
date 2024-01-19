package com.iamnaran.firefly.ui.dashboard.home

import androidx.lifecycle.viewModelScope
import com.iamnaran.firefly.data.remote.Resource
import com.iamnaran.firefly.domain.usecase.GetLoginStatusUseCase
import com.iamnaran.firefly.domain.usecase.GetProductUseCase
import com.iamnaran.firefly.ui.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getLoginStatusUseCase: GetLoginStatusUseCase
) : BaseViewModel() {


    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    private val _loginStatus = MutableStateFlow(false)
    val loginStatus: StateFlow<Boolean> = _loginStatus

    init {
        getLoggedInStatus()
    }

    private fun getLoggedInStatus() {
        viewModelScope.launch {
            getLoginStatusUseCase().onEach {
                _loginStatus.value = it;
                if (it){
                    getProducts()
                }
            }.launchIn(this)
        }
    }

    private fun getProducts() {

        viewModelScope.launch {
            getProductUseCase().onEach { productResource ->
                when (productResource) {
                    is Resource.Success -> {
                        _homeState.value = HomeState(productResource.data!!)
                    }
                    else -> {
                    }
                }
            }.launchIn(this)
        }
    }

}