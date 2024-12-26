package com.hhplus.lecture.helper.testcontainers

import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

object SharedTestContainer {
    val mysqlContainer: MySQLContainer<*> = MySQLContainer(DockerImageName.parse("mysql:8.0.28")).apply {
        withDatabaseName("testdb")
        withUsername("test")
        withPassword("test")
        withInitScript("db/init.sql")
        start()
    }
}
