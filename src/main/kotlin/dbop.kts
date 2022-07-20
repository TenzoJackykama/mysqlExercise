import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

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

data class Students(
    val name: String,
    val surname: String
)

object Query{

    var name: String? = null
    var surname: String? = null
    var countryName: String? = null

    lateinit var stm: Statement


    val table = "CREATE TABLE student " +
            "(student_id INT AUTO_INCREMENT PRIMARY KEY, " +
            "last_name VARCHAR(30), " +
            "first_name VARCHAR(30))"

    val showTableRecord = "SELECT * FROM student"

    val Select = "SELECT first_name, last_name FROM student"

    val addColumn = "ALTER TABLE student ADD country varchar(30)"

    val viewItaly = "CREATE VIEW italian_students AS SELECT * FROM student WHERE country='Italy'"

    val viewGerman = "CREATE VIEW german_students AS SELECT * FROM student WHERE country='Germany'"

    fun italianStudentsSelectView(italianList: ArrayList<Students>, connection: Connection): ArrayList<Students> {
        stm = connection.createStatement()
        var selectResult = stm.executeQuery("SELECT * FROM italian_students")
        while (selectResult.next()){
            italianList.add(Students(selectResult.getString("first_name"), selectResult.getString("last_name")))
        }
        return italianList
    }

    fun germanStudentsSelectView(germanList: ArrayList<Students>, connection: Connection): ArrayList<Students> {
        stm = connection.createStatement()
        var selectResult = stm.executeQuery("SELECT * FROM german_students")
        while (selectResult.next()){
           germanList.add(Students(selectResult.getString("first_name"), selectResult.getString("last_name")))
        }
        return germanList
    }
}

fun main() {
    println("Hello World!")
    DbUtils.getParameters("jdbc:mysql://localhost:3306/newdb", "root", "eciw#ViniSp123")
    val germanArray = ArrayList<Students>()
    val italyArray = ArrayList<Students>()

    try {
        println("Fill records...")

        val connection = DbUtils.connectToJdbcDatabase()
       // val stm = connection.createStatement()

        //stm.executeUpdate(Query.viewItaly)
        //stm.executeUpdate(Query.viewGerman)

        println(Query.italianStudentsSelectView(italyArray, connection).toString())
        println(Query.germanStudentsSelectView( germanArray, connection).toString())


    }catch (e: SQLException){
        println("connection to database: ${e.message}")
    }

}

main()