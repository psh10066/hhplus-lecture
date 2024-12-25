# ERD

![erd.png](erd.png)

## DBML

```
Table lecture as "특강" {
  id BIGINT [pk, not null] // 특강 ID
  name VARCHAR(255) [not null] // 특강 이름
  start_time DATETIME [not null] // 특강 시작 시간
  end_time DATETIME [not null] // 특강 종료 시간
  subscription_count INT [not null] // 특강 신청자 수
  lecturer_id BIGINT [not null, ref: > lecturer.id] // 강연자 ID
  created_at DATETIME [not null] // 생성 시각
  updated_at DATETIME [not null] // 수정 시각
}

Table lecturer as "강연자" {
  id BIGINT [pk, not null] // 강연자 ID
  name VARCHAR(255) [not null] // 강연자 이름
  created_at DATETIME [not null] // 생성 시각
  updated_at DATETIME [not null] // 수정 시각
}

Table lecture_subscription as "특강 신청" {
  id BIGINT [pk, not null] // 특강 신청 ID
  lecture_id BIGINT [not null, ref: > lecture.id] // 특강 ID
  user_id BIGINT [not null, ref: > user.id] // 사용자 ID
  created_at DATETIME [not null] // 특강 신청 시각
  updated_at DATETIME [not null] // 수정 시각

  indexes {
    (lecture_id, user_id) [unique] // UNIQUE 제약 조건
  }
}

Table user as "사용자" {
  id BIGINT [pk, not null] // 사용자 ID
  name VARCHAR(255) [not null] // 사용자 이름
  created_at DATETIME [not null] // 생성 시각
  updated_at DATETIME [not null] // 수정 시각
}
```