package io.hhplus.cleanarchitecture.api.lecture.usecase.unit

import io.hhplus.cleanarchitecture.api.lecture.stub.LectureRepositoryStub
import io.hhplus.cleanarchitecture.api.lecture.usecase.LectureListUseCase
import io.hhplus.cleanarchitecture.domain.lecture.LectureReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LectureListUseCaseTest {

    @Test
    fun `강의 리스트를 조회한다`() {
        // given
        val lectureRepository = LectureRepositoryStub()
        val lectureReader = LectureReader(lectureRepository)

        val useCase = LectureListUseCase(lectureReader)

        // when
        val result = useCase()

        // then
        assertThat(result).isNotNull
    }
}