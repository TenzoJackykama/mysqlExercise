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
    DbUtils.getParameters("jdbc:mysql://localhost:3306/newdb", "root", "eciw#ViniSp123")
    var name: String = "fgvsdagv"
    var surname: String = "SGVVDSFG"

    /*val table = "CREATE TABLE student " +
            "(student_id INT AUTO_INCREMENT PRIMARY KEY, " +
            "last_name VARCHAR(30), " +
            "first_name VARCHAR(30));";*/



    val showTableRecord = "SELECT * FROM student;"

    try {
        val connection = DbUtils.connectToJdbcDatabase()
        println("connection to database: ${connection.catalog}")
        val stm = connection.createStatement()
        val dbmControl = connection.metaData
        val existentTable = dbmControl.getTables(null, null, "student", null)
        //stm.executeUpdate(table)
        val resultAllTable = stm.executeQuery( "SHOW TABLES;")
        println("check name all tables")

        while (resultAllTable.next()){
            println(resultAllTable.getString(1))
        }
        if (existentTable.next()){
            println("esiste")
        }else{
            println("non esiste")
        }
        println("Created table in given database...")



    }catch (e: SQLException){
        println("connection to database: ${e.message}")
    }

    try {
        println("Fill records...")

        val connection = DbUtils.connectToJdbcDatabase()
        val stm = connection.createStatement()
        for (i in 1..3){
            name = readLine()!!.toString()
            surname = readLine()!!.toString()
            val insertUserQuery = "INSERT INTO student (last_name, first_name) \n" +
                    "VALUES ('${surname}', '${name}');"
            stm.executeUpdate(insertUserQuery)
        }

        stm.executeQuery(showTableRecord)
    }catch (e: SQLException){
        println("connection to database: ${e.message}")
    }

}

main()