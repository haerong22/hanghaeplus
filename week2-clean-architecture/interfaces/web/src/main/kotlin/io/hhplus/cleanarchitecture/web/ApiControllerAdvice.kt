package io.hhplus.cleanarchitecture.web

import io.hhplus.cleanarchitecture.domain.common.BadRequestException
import io.hhplus.cleanarchitecture.domain.common.CustomException
import io.hhplus.cleanarchitecture.domain.lecture.LectureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {
    val log: Logger get() = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(
        LectureException::class
    )
    fun handleCustomException(e: CustomException): CommonResponse<Any> {
        log.warn("CustomException : {}", e.msg)
        return CommonResponse.error(e.errorCode, e.msg)
    }

    @ExceptionHandler(
        BadRequestException::class
    )
    fun handleBadRequestException(e: CustomException): CommonResponse<Any> {
        log.warn("CustomException : {}", e.msg)
        return CommonResponse.error(e.errorCode, e.msg)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.message)
    }
}