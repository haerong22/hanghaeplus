## [1주차 과제] TDD 로 개발하기

### ℹ️ 과제 필수 사항

- Nest.js 의 경우 Typescript , Spring 의 경우 Kotlin / Java 중 하나로 작성합니다.
    - 프로젝트에 첨부된 설정 파일은 수정하지 않도록 합니다.
- 테스트 케이스의 작성 및 작성 이유를 주석으로 작성하도록 합니다.
- 프로젝트 내의 주석을 참고하여 필요한 기능을 작성해주세요.
- 분산 환경은 고려하지 않습니다.

### ❓ [과제] `point` 패키지의 TODO 와 테스트코드를 작성해주세요.

**요구 사항**

- PATCH  `/point/{id}/charge` : 포인트를 충전한다.
- PATCH `/point/{id}/use` : 포인트를 사용한다.
- GET `/point/{id}` : 포인트를 조회한다.
- GET `/point/{id}/histories` : 포인트 내역을 조회한다.
- 잔고가 부족할 경우, 포인트 사용은 실패하여야 합니다.
- 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 합니다.

### 구현 시 고려한 점

#### 구현
- layered architecture
  - controller -> service -> domain <- repository
  - 각 계층은 단방향으로 의존
  - 역방향으로 의존하지 않기 위해 다음 계층으로 데이터 전달 시 dto 전환하여 전달
    - dto 전환이 빈번하게 일어나는데 오버헤드는 없는지?

- 검증을 시도하는 구간은?
  - 컨트롤러에서 입력데이터에 대한 검증
  - 도메인 레이어에서 비즈니스 적인 예외사항 검증
  - DB에서 요구사항에 맞는 데이터 검증
  - 중복되는 경우에도 검증을 다 하는 것이 맞는가?
    - ex) point 충전/사용 시 양수만 입력 가능 한 경우를 검증 할 때 컨트롤러에서 클라이언트가 입력한 데이터에 대한 검증이 완료된 후 도메인 레이어로 전달이 되는데 도메인 레이어에서도 검증이 필요한가? 

- 동시성 
  - 분산 환경은 고려하지 않으므로 ReentrantLock 활용하여 구현
    - 제공된 메모리 데이터베이스는 동시성이 고려되지 않은 상황
      - userId 별 Lock을 구현 하였으나 `PointHistoryTable`의 `cursor` 값을 고려하여 포인트 충전/사용 시 같은 lock 을 사용 하도록 변경

- 트랜잭션
  - 포인트 충전/사용 시 `PointHistory` 생성, `UserPoint` 변경의 두 동작이 하나의 트랜잭션으로 구현 되어야 한다.
  - `PointHistory` 생성 성공 -> `UserPoint` 변경 성공
  - `PointHistory` 생성 성공 -> `UserPoint` 변경 실패 -> `PointHistory` 롤백
  - 제공된 메모리 데이터베이스의 변경이 제한 되므로 롤백 구현 X

#### 테스트 코드 작성
- 실패 케이스에 더 집중한 테스트 케이스 작성
- `LocaldateTime.now()`, `System.currentTimeMillis()` 와 같은 데이터를 검증하는지?
- 테스트를 위한 given 데이터 생성을 공통으로 사용해도 되는가?
  - 공통으로 사용하면 해당 테스트의 흐름을 한 눈에 보기 어려워 지는 상황

- 서비스 단위 테스트
  - 외부 조건의 영향을 받지 않고 비즈니스 로직에만 집중 할 수 있도록 Stub객체 활용
  - mocking vs stubbing

- 서비스 통합 테스트
  - `@SpringBootTest` 활용하여 실제 빈을 등록한 후 테스트
  - 검증 구간에서 테이블의 데이터를 조회하여 데이터 검증
    - DB에서 데이터를 조회하여 검증? or 검증하려는 메소드의 응답 값을 검증? 아니면 둘 다? 
  - given 데이터 생성 시 검증하려는 메소드 이외의 다른 메소드를 사용해서 생성 해도 되는가?
    - ex) 포인트 사용을 테스트 할 경우 포인트가 충전 된 조건을 만들기 위해 포인트 충전하는 메소드를 사용 or 디비에 직접 데이터를 insert

- 컨트롤러 테스트 
  - `MockMvc`를 이용한 테스트
  - 입력 값에 대한 검증만을 수행
    - status code, 에러 메시지 검증
  - 서비스 통합 테스트를 이미 진행 하였는데 컨트롤러도 `@SpringBootTest`를 해야할 지?