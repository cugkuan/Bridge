package top.brightk.feature1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TestViewModel:ViewModel() {

    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    // 递增计数
    fun increment() {
        _count.update { it + 1 }
    }
}