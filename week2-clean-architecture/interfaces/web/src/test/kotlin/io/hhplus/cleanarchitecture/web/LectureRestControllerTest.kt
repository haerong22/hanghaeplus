package io.hhplus.cleanarchitecture.web

import io.hhplus.cleanarchitecture.domain.lecture.LectureService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(LectureRestController::class)
class LectureRestControllerTest: WebTestSupport() {

    @MockBean
    lateinit var lectureService: LectureService

    @Test
    fun `수강 신청 성공`() {
        // given
        val request = LectureApplyRequest(1L)

        // then
        mockMvc.perform(
            post("/api/lectures/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `수강 신청 시 userId는 양수이다`(userId: Long) {
        // given
        val request = LectureApplyRequest(userId)

        // then
        mockMvc.perform(
            post("/api/lectures/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("userId 는 양수 값 입니다."))
    }
}