package com.ls.iusta.presentation.viewmodel.tickets

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.category.CreateTicketUseCase
import com.ls.iusta.domain.interactor.category.GetCategoryListUseCase
import com.ls.iusta.domain.models.category.CategoryUiModel
import com.ls.iusta.domain.models.category.CreateTicketRequest
import com.ls.iusta.domain.models.category.GetCategoryRequest
import com.ls.iusta.domain.models.tickets.AttachmentFile
import com.ls.iusta.presentation.R
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.SingleLiveEvent
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
    private val createTicketUseCase: CreateTicketUseCase
) : BaseViewModel(contextProvider) {

    private val _category = UiAwareLiveData<CategoryUiModel>()
    val category: LiveData<CategoryUiModel> = _category

    private val orderNoteAttachmentSizeLiveData =
        SingleLiveEvent<Long>()
    val orderNoteAttachmentSize: LiveData<Long>
        get() = orderNoteAttachmentSizeLiveData



    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _category.postValue(CategoryUiModel.Error(exception.message ?: "Error"))
        }

    fun getCategories(menuId: Int) {
        _category.postValue(CategoryUiModel.Loading)
        launchCoroutineIO {
                loadCategories(
                    GetCategoryRequest(
                        menuId
                    )
                )
        }
    }

    fun sendTicket(attachments: List<AttachmentFile>, categoryId: Int, note: String?) {
        _category.postValue(CategoryUiModel.Loading)
        launchCoroutineIO {
                createTicket(
                    CreateTicketRequest(
                        attachments,
                        categoryId,
                        note
                    )
                )
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


    private fun checkAttachmentSize(): Boolean {
        var isValid = true
        var errorMessage: String? = null
        val attachmentSize = orderNoteAttachmentSize.value ?: 0L
        if (attachmentSize > 10485760L) {
            isValid = false
         //   errorMessage =  applicationOrder.resources.getString(R.string.fragment_order_info_note_attachment_large)
        }
       // errorOrderLiveData.postValue(errorMessage)
        return isValid
    }


    fun orderNoteAttachmentResize(newSize: Long) {
        val attachmentSize = orderNoteAttachmentSize.value ?: 0L
        orderNoteAttachmentSizeLiveData.postValue(attachmentSize.plus(newSize))
    }

    fun orderNoteAttachmentSizeClear() {
        orderNoteAttachmentSizeLiveData.postValue(0L)
    }


}