import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DbUtils{

    private lateinit var con: Connection
    private lateinit var url: String
    private lateinit var user: String
    private lateinit var password: String

    fun getParameters(url: String, user: String, password: String){
        this.url = url
        this.user = user
        this.password = password
    }

    fun connectToJdbcDatabase(): Connection {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            con = DriverManager.getConnection(url, user, password)
        }catch (e: SQLException){
            println("connectToJdbcDatabase message: ${e.message}")
        }

        return con
    }
}

fun main() {
    println("Hello World!")
    DbUtils.getParameters("jdbc:mysql://localhost:3306/newdb", "developer", "Developer123#")



    try {
        val connection = DbUtils.connectToJdbcDatabase()
        println("connection to database: ${connection.catalog}")
    }catch (e: SQLException){
        println("connection to database: ${e.message}")
    }
}

main()