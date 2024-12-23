package com.duyvv.kmadoc.util

import android.util.Base64
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.duyvv.kmadoc.data.model.FilterModel
import com.duyvv.kmadoc.data.model.FormModel
import com.duyvv.kmadoc.data.model.UserInfo
import com.duyvv.kmadoc.ui.dialog.DeleteDialog
import com.duyvv.kmadoc.ui.dialog.SuccessDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transform
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

val TAG = "Prediction20244"

fun Any.logd() {
    Log.d(TAG, toString())
}


fun Any.logd(tag: String) {
    Log.d(tag, toString())
}

fun <T : Any> mutableLiveDataOf(value: T? = null): MutableLiveData<T> =
    if (value == null) MutableLiveData()
    else MutableLiveData(value)

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun <T : Any> MutableLiveData<T>.postValue(block: T.() -> Unit) {
    value?.let {
        block(it)
        postValue(value)
    }
}

fun copyFile(sourceLocation: File, targetLocation: File) {
    try {
        val `in`: InputStream = FileInputStream(sourceLocation)
        val out = FileOutputStream(targetLocation)
        val buf = ByteArray(1024)
        var len: Int
        while (`in`.read(buf).also { len = it } > 0) {
            out.write(buf, 0, len)
        }
        `in`.close()
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun <T> List<T>.first(count: Int, block: (T) -> Unit) {
    forEachIndexed { index, t -> if (index < count) block(t) }
}

fun <T> List<T>.first(count: Int): List<T> {
    val result = mutableListOf<T>()
    for (i in 0..count) {
        if (i >= size) {
            break
        } else {
            result.add(get(i))
        }
    }
    return result
}

fun Int.asLikeCount(): String {
    var result = StringBuilder()
    if (this >= 1_000_000) {
        result.append(String.format("%.1f", this / 1_000_000f))
        result.append("M")
    } else {
        if (this in 100_000..999_999) {
            result.append(String.format("%03d", this / 100_000))
            result.append("K")
        }
    }
    return result.toString()
}

fun String.decapitalizeFirstLetter(): String {
    return this.replaceFirstChar {
        if (it.isUpperCase()) it.lowercase() else it.toString()
    }
}

fun <T> flowFromSuspend(block: suspend () -> T): Flow<T> = flow {
    emit(block())
}

fun <T> Flow<Result<T>>.onEachError(action: suspend (Throwable) -> Unit): Flow<T> =
    transform { value ->
        value.onFailure {
            action(it)
        }
        return@transform emit(value)
    }.mapNotNull { it.getOrNull() }

fun String.toUserInfo(): UserInfo {
    val splitToken = this.split(".")
    if (splitToken.size != 3) {
        throw IllegalArgumentException("Invalid JWT token format")
    }
    val payload = splitToken[1]
    val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
    val decodedString = String(decodedBytes)
    val gson = Gson()
    return gson.fromJson(decodedString, UserInfo::class.java)
}

fun FormModel.getContentByFieldName(fieldName: String): String {
    return fields
        .firstOrNull { it.name == fieldName }
        ?.value ?: ""
}

fun FormModel.updateOCRFormData(fieldName: String, newContent: String): FormModel {
    val newFields = fields.toMutableList().map {
        if (it.name == fieldName) {
            it.copy(value = newContent)
        } else {
            it
        }
    }
    return copy(fields = newFields)
}

inline fun <reified T> T?.toJson(): String? {
    return if (this == null) null else Gson().toJson(this)
}

inline fun <reified T> String?.fromJson(): T? {
    return if (this.isNullOrEmpty()) null else {
        val type = object : TypeToken<T>() {}.type
        Gson().fromJson<T>(this, type)
    }
}

fun Fragment.showSuccessDialog(title: String, onDismiss: () -> Unit) {
    SuccessDialog.newInstance(title, onDismiss).show(parentFragmentManager, "")
}

fun Fragment.showConfirmDeleteDialog(onConfirm: () -> Unit) {
    DeleteDialog.newInstance(onConfirm).show(parentFragmentManager, "")
}

inline fun <reified T> Fragment.setFragmentResult(key: String, value: T?) {
    parentFragmentManager.setFragmentResult(
        key,
        bundleOf(
            key to value.toJson()
        )
    )
}

inline fun <reified T> Fragment.onFragmentResult(key: String, crossinline callback: (T?) -> Unit) {
    parentFragmentManager.setFragmentResultListener(key, viewLifecycleOwner) { _, bundle ->
        callback(bundle.getString(key).fromJson())
    }
}

fun List<FilterModel>.isSelectedAll(): Boolean {
    return all { it.isSelected }
}

fun List<FilterModel>.noItemSelected(): Boolean {
    return all { !it.isSelected }
}