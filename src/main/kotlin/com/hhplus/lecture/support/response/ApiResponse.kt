package com.hhplus.lecture.support.response

data class ApiResponse<T> private constructor(
    val result: ResultType,
    val data: T? = null,
    val error: String? = null,
) {
    companion object {
        fun success(): ApiResponse<Unit> {
            return ApiResponse(ResultType.SUCCESS, null, null)
        }

        fun <S> success(data: S): ApiResponse<S> {
            return ApiResponse(ResultType.SUCCESS, data, null)
        }

        fun error(errorMessage: String? = null): ApiResponse<Unit> {
            return ApiResponse(ResultType.ERROR, null, errorMessage)
        }
    }
}
