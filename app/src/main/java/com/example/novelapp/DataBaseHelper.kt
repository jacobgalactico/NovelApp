package com.example.novelapp


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "novelapp.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NOVELS = "novelas"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "titulo"
        const val COLUMN_AUTHOR = "autor"
        const val COLUMN_YEAR = "anio"
        const val COLUMN_SYNOPSIS = "sinopsis"
        const val COLUMN_FAVORITE = "favorita"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NOVELS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_AUTHOR TEXT NOT NULL,
                $COLUMN_YEAR INTEGER,
                $COLUMN_SYNOPSIS TEXT,
                $COLUMN_FAVORITE INTEGER DEFAULT 0
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOVELS")
        onCreate(db)
    }

    // Función para insertar una novela en la base de datos
    fun addNovel(novel: Libro): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, novel.titulo)
            put(COLUMN_AUTHOR, novel.autor)
            put(COLUMN_YEAR, novel.anioPublicacion)
            put(COLUMN_SYNOPSIS, novel.sinopsis)
            put(COLUMN_FAVORITE, if (novel.esFavorita) 1 else 0)
        }
        return db.insert(TABLE_NOVELS, null, values).also { db.close() }
    }

    // Función para obtener todas las novelas
    fun getAllNovels(): List<Libro> {
        val novels = mutableListOf<Libro>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NOVELS, null, null, null, null, null, null)

        cursor?.use {
            while (it.moveToNext()) {
                val titulo = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE))
                val autor = it.getString(it.getColumnIndexOrThrow(COLUMN_AUTHOR))
                val anio = it.getInt(it.getColumnIndexOrThrow(COLUMN_YEAR))
                val sinopsis = it.getString(it.getColumnIndexOrThrow(COLUMN_SYNOPSIS))
                val esFavorita = it.getInt(it.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1
                novels.add(Libro(titulo, autor, anio, sinopsis, esFavorita))
            }
        }
        db.close()
        return novels
    }

    // Función para actualizar una novela
    fun updateNovel(novel: Libro): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, novel.titulo)
            put(COLUMN_AUTHOR, novel.autor)
            put(COLUMN_YEAR, novel.anioPublicacion)
            put(COLUMN_SYNOPSIS, novel.sinopsis)
            put(COLUMN_FAVORITE, if (novel.esFavorita) 1 else 0)
        }
        val rowsUpdated = db.update(TABLE_NOVELS, values, "$COLUMN_TITLE = ?", arrayOf(novel.titulo))
        db.close()
        return rowsUpdated
    }

    // Función para eliminar una novela
    fun deleteNovel(title: String): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_NOVELS, "$COLUMN_TITLE = ?", arrayOf(title))
        db.close()
        return rowsDeleted
    }
}
