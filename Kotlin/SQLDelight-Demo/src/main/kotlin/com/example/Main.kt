package com.example

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.sqldelight.hockey.data.HockeyPlayer
import com.example.sqldelight.hockey.data.PlayerQueries
import java.sql.SQLException

fun main(args: Array<String>) {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    Database.Schema.create(driver)

    // In reality the database and driver above should be created a single time
    // and passed around using your favourite dependency injection/service
    // locator/singleton pattern.
    val database = Database(driver)

    val playerQueries: PlayerQueries = database.playerQueries

    println(playerQueries.selectAll().executeAsList())
    // Prints [HockeyPlayer(15, "Ryan Getzlaf")]

    playerQueries.insert(player_number = 10, full_name = "Corey Perry")
    println(playerQueries.selectAll().executeAsList())
    // Prints [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]

    // If primary key successfully created, then insertFullPlayerObject will throw exception.
    try {
        val player = HockeyPlayer(10, "Ronald McDonald")
        playerQueries.insertFullPlayerObject(player)
    } catch (e: SQLException) {
        println("Exception successfully thrown by Primary Key constraint: ${e.message}")
    }
}