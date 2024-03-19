package io.hhplus.tdd.point.interfaces.rest

import com.fasterxml.jackson.databind.ObjectMapper
import io.hhplus.tdd.point.application.PointService
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PointController::class)
class PointControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var pointService: PointService

    @Test
    fun `포인트를 충전한다`() {
        // given
        val amount = 10_000L

        // then
        mockMvc.perform(
            patch("/point/1/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amount))
        )
            .andDo(print())
            .andExpect(status().isOk)
    }

    @ParameterizedTest
    @CsvSource("-1,10000", "0,10000")
    fun `포인트를 충전 시 유저 아이디는 양수 이다`(userId: Long, amount: Long) {
        // given

        // then
        mockMvc.perform(
            patch("/point/${userId}/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amount))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("잘못된 userId 입니다."))
    }

    @ParameterizedTest
    @CsvSource("1,0", "1,-10000")
    fun `포인트를 충전은 1 이상 가능하다`(userId: Long, amount: Long) {
        // given

        // then
        mockMvc.perform(
            patch("/point/${userId}/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amount))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("포인트는 1 이상 충전 가능합니다."))
    }

    @Test
    fun `포인트를 사용한다`() {
        // given
        val amount = 10_000L

        // then
        mockMvc.perform(
            patch("/point/1/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amount))
        )
            .andDo(print())
            .andExpect(status().isOk)
    }

    @ParameterizedTest
    @CsvSource("-1,10000", "0,10000")
    fun `포인트 사용 시 유저 아이디는 양수 이다`(userId: Long, amount: Long) {
        // given

        // then
        mockMvc.perform(
            patch("/point/${userId}/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amount))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("잘못된 userId 입니다."))
    }

    @ParameterizedTest
    @CsvSource("1,0", "1,-10000")
    fun `포인트 사용은 1 이상 가능하다`(userId: Long, amount: Long) {
        // given

        // then
        mockMvc.perform(
            patch("/point/${userId}/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amount))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("포인트는 1 이상 충전 가능합니다."))
    }

    @Test
    fun `포인트를 조회한다`() {
        // given
        val id = 1L

        // then
        mockMvc.perform(
            get("/point/${id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `포인트 조회 시 유저 아이디는 양수 이다`(userId: Long) {
        // given

        // then
        mockMvc.perform(
            get("/point/${userId}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("잘못된 userId 입니다."))
    }

}