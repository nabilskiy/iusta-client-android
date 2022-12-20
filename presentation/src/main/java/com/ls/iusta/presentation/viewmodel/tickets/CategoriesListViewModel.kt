package com.ls.iusta.presentation.viewmodel.tickets

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.TokenUseCase
import com.ls.iusta.domain.interactor.category.CreateTicketUseCase
import com.ls.iusta.domain.interactor.category.GetCategoryListUseCase
import com.ls.iusta.domain.models.category.CategoryUiModel
import com.ls.iusta.domain.models.category.CreateTicketRequest
import com.ls.iusta.domain.models.category.GetCategoryRequest
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val createTicketUseCase: CreateTicketUseCase,
    private val tokenUseCase: TokenUseCase
) : BaseViewModel(contextProvider) {

    private val _category = UiAwareLiveData<CategoryUiModel>()
    val category: LiveData<CategoryUiModel> = _category


    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _category.postValue(CategoryUiModel.Error(exception.message ?: "Error"))
        }

    fun sendRequest(menuId: Int, create: Boolean) {
        _category.postValue(CategoryUiModel.Loading)
        launchCoroutineIO {
            tokenUseCase(Unit).collect {
                if (create){
                    createTicket(
                        CreateTicketRequest(
                            menuId,
                            it
                        )
                    )
                }else{
                    loadCategories(
                        GetCategoryRequest(
                            menuId,
                            it
                        )
                    )
                }
            }

        }
    }

    private suspend fun loadCategories(data: GetCategoryRequest) {
        getCategoryListUseCase(data).collect {
            _category.postValue(CategoryUiModel.LoadCategoriesSuccess(it))
        }
    }

    private suspend fun createTicket(data: CreateTicketRequest) {
        createTicketUseCase(data).collect {
            _category.postValue(CategoryUiModel.CreateTicketSuccess(it))
        }
    }


}