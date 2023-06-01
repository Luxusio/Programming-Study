import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    expect fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {

}