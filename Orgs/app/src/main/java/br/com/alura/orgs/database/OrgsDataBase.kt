package br.com.alura.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.model.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OrgsDataBase:RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {
        fun instancia(context:Context):OrgsDataBase {
            return Room.databaseBuilder(
                context,
                OrgsDataBase::class.java,
                "Orgs.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}