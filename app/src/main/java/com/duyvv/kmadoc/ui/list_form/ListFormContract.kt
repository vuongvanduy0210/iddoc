package com.duyvv.kmadoc.ui.list_form

import com.duyvv.kmadoc.base.mvi.MviIntent
import com.duyvv.kmadoc.base.mvi.MviState
import com.duyvv.kmadoc.data.model.FilterModel

class ListFormContract {
    sealed class ListFormState : MviState {
        data object RefreshItemLoading : ListFormState()
        data object RefreshItemLoaded : ListFormState()
        data object LoadMoreItemLoading : ListFormState()
        data object LoadMoreItemLoaded : ListFormState()
    }

    sealed class ListFormIntent : MviIntent {
        data object FirstLoadItem : ListFormIntent()
        data object RefreshItem : ListFormIntent()
        data object LoadMoreItem : ListFormIntent()
        data class SearchItem(val keySearch: String) : ListFormIntent()
        data class FilterType(val listType: List<FilterModel>, val listStatus: List<FilterModel>) :
            ListFormIntent()

        data class FilterTime(val startDate: String, val endDate: String) : ListFormIntent()
    }
}