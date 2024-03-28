## [ 2주차 과제 ] 특강 신청 서비스

<details>
<summary>과제 상세</summary>

### Description

- `특강 신청 서비스`를 구현해 봅니다.
- 항해 플러스 토요일 특강을 신청할 수 있는 서비스를 개발합니다.
- 특강 신청 및 신청자 목록 관리를 RDBMS를 이용해 관리할 방법을 고민합니다.

### Requirements

- 아래 2가지 API 를 구현합니다.
    - 특강 신청 API
    - 특강 신청 여부 조회 API
- 각 기능 및 제약 사항에 대해 단위 테스트를 반드시 하나 이상 작성하도록 합니다.
- 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성하도록 합니다.
- 동시성 이슈를 고려하여 구현합니다.

### API Specs

1️⃣ **(핵심)** 특강 신청 **API**

- 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
- 동일한 신청자는 한 번의 수강 신청만 성공할 수 있습니다.
- 특강은 `4월 20일 토요일 1시` 에 열리며, 선착순 30명만 신청 가능합니다.
- 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.

2️⃣ **(기본)** 특강 신청 완료 여부 조회 API

- 특정 userId 로 특강 신청 완료 여부를 조회하는 API 를 작성합니다.
- 특강 신청에 성공한 사용자는 성공했음을, 특강 등록자 명단에 없는 사용자는 실패했음을 반환합니다.

---

### 심화 과제

3️⃣ **(선택) 특강 선택 API**

- 단 한번의 특강을 위한 것이 아닌 날짜별로 특강이 존재할 수 있는 범용적인 서비스로 변화시켜 봅니다.
- 이를 수용하기 위해, 특강 엔티티의 경우 기존의 설계에서 변경되어야 합니다.
- 특강의 정원은 30명으로 고정이며, 사용자는 각 특강에 신청하기전 목록을 조회해볼 수 있어야 합니다.

>
> 💡 **KEY POINT**

- 정확하게 30 명의 사용자에게만 특강을 제공할 방법을 고민해 봅니다.
- 같은 사용자에게 여러 번의 특강 슬롯이 제공되지 않도록 제한할 방법을 고민해 봅니다.

</details>

---

## ERD

![erd](docs/erd.png)

## API

### 공통 응답

| Name    | Type     | Description |
|:--------|:---------|:------------|
| code    | `int`    | 응답 코드       |
| message | `String` | 응답 메시지      |
| data    | `object` | 응답 결과       |

```json
{
  "code": 0,
  "message": "string",
  "data": {}
}
```

### 응답코드

| Code   | Description |
|:-------|:------------|
| `0`    | 성공          |
| `400`  | 잘못된 요청 값    |
| `1404` | 강의 없음       |
| `1000` | 강의 신청 마감    |
| `1001` | 강의 신청 기간 아님 |
| `1002` | 이미 신청 완료    |

### API 명세

| METHOD | URI                                 | 기능          |
|--------|-------------------------------------|-------------|
| POST   | `/api/lectures/{lectures_id}/apply` | 강의 신청       |
| GET    | `/api/lectures/{lectures_id}/apply` | 강의 신청 여부 확인 |
| GET    | `/api/lectures`                     | 강의 리스트 조회   |

### 강의 신청

#### URL

```http request
POST /api/lectures/{lectures_id}/apply
host: 127.0.0.1:8080
```

#### Request Body

| Name   | Type   | Description | Required |
|:-------|:-------|:------------|:---------|
| userId | `Long` | 유저 아이디      | O        |

```json
{
  "userId": 1
}
```

#### Response Body(성공) - 201 CREATED

```json
{
  "code": 0,
  "message": "success",
  "body": null
}
```

### 강의 신청 여부 조회

#### URL

```http request
GET /api/lectures/{lectures_id}/apply
host: 127.0.0.1:8080
```

#### Request Param

| Name   | Type   | Description | Required |
|:-------|:-------|:------------|:---------|
| userId | `long` | 유저 아이디      | O        |

#### Response Body(성공) - 200 OK

| Name | Type      | Description |
|:-----|:----------|:------------|
| body | `boolean` | 신청 여부       |

```json
{
  "code": 0,
  "message": "success",
  "body": true
}
```

### 강의 리스트 조회

#### URL

```http request
GET /api/lectures/{lectures_id}
host: 127.0.0.1:8080
```

#### Response Body(성공) - 200 OK

| Name    | Type     | Description |
|:--------|:---------|:------------|
| id      | `long`   | 강의 식별 ID    |
| name    | `String` | 강의명         |
| quota   | `int`    | 강의 신청 정원    |
| startAt | `date`   | 강의 신청일      |

```json
{
  "code": 0,
  "message": "success",
  "body": [
    {
      "id": 1,
      "name": "강의1",
      "quota": 30,
      "startAt": "2024-03-28T14:59:07.247055"
    },
    {
      "id": 2,
      "name": "강의2",
      "quota": 30,
      "startAt": "2024-03-28T14:59:07.248197"
    }
  ]
}
```