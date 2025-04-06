package com.example.plugins.table.sql

import com.example.model.assortment
import com.example.model.bd_id
import com.example.plugins.table.sql.MySQLDatabaseExampleKotlin.conn
import com.example.plugins.table.sql.MySQLDatabaseExampleKotlin.stmt
import java.sql.SQLException

fun update_assortment(assortId: bd_id, assort: assortment, tableName: String) {
try {
    val num: Int = assortId.bd_id.toInt()
    if (assort.name != "" || assort.description != "") {
        val sql = when {
            assort.name != "" && assort.description != "" -> 
                "UPDATE $tableName SET name = ?, description = ? WHERE id_$tableName = ?"
            assort.name != "" -> 
                "UPDATE $tableName SET name = ? WHERE id_$tableName = ?"
            else -> 
                "UPDATE $tableName SET description = ? WHERE id_$tableName = ?"
        }
        
        val preparedStmt = conn!!.prepareStatement(sql)
        
        when {
            assort.name != "" && assort.description != "" -> {
                preparedStmt.setString(1, assort.name)
                preparedStmt.setString(2, assort.description)
                preparedStmt.setInt(3, num)
            }
            assort.name != "" -> {
                preparedStmt.setString(1, assort.name)
                preparedStmt.setInt(2, num)
            }
            else -> {
                preparedStmt.setString(1, assort.description)
                preparedStmt.setInt(2, num)
            }
        }
        
        preparedStmt.executeUpdate()
    }
} catch (e: Exception) {
    // Обработка ошибок
} finally {
    stmt?.close()
}

fun into_assortment(assort: assortment, tableName: String) {
    try {
        stmt = conn!!.createStatement()
        val sql = """INSERT INTO $tableName (name, description) VALUES ('${assort.name}', '${assort.description}');"""
        stmt!!.executeUpdate(sql)
    }catch (ex: SQLException){
        ex.printStackTrace()
    }

}
