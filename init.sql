CREATE TABLE `lecture` (
  `id` BIGINT PRIMARY KEY NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `subscription_count` INT NOT NULL,
  `lecturer_id` BIGINT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL
);

CREATE TABLE `lecturer` (
  `id` BIGINT PRIMARY KEY NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL
);

CREATE TABLE `lecture_subscription` (
  `id` BIGINT PRIMARY KEY NOT NULL,
  `lecture_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  CONSTRAINT lecture_subscription_unique_0
      UNIQUE (lecture_id, user_id)
);

CREATE TABLE `user` (
  `id` BIGINT PRIMARY KEY NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL
);
