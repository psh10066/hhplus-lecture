# ERD

![erd.png](erd.png)

## DBML

```
Table lecture as "특강" {
  id BIGINT [pk, not null] // 특강 ID
  name VARCHAR(255) [not null] // 특강 이름
  speaker_id BIGINT [not null, ref: > speaker.id] // 강연자 ID
  limit INT [not null] // 특강 정원
  start_time DATETIME [not null] // 특강 시작 시간
  end_time DATETIME [not null] // 특강 종료 시간
  created_at DATETIME [not null] // 생성 시각
  updated_at DATETIME [not null] // 수정 시각
}

Table speaker as "강연자" {
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
}

Table user as "사용자" {
  id BIGINT [pk, not null] // 사용자 ID
  name VARCHAR(255) [not null] // 사용자 이름
  created_at DATETIME [not null] // 생성 시각
  updated_at DATETIME [not null] // 수정 시각
}
```