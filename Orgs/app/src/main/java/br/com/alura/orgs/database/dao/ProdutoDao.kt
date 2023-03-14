package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.alura.orgs.model.Produto


@Dao
interface ProdutoDao {
    @Query("SELECT * FROM Produto")
    fun buscaTodos() : List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg produto:Produto)

    @Delete
    fun remove(produto: Produto)

    @Update
    fun edita(produto: Produto)

    @Query("SELECT * FROM produto WHERE uid=:uid")
    fun buscaTodosID(uid:Long):Produto?

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    fun ordenaNomeAsc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    fun ordenaNomeDesc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao ASC")
    fun ordenaDescricaoAsc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao DESC")
    fun ordenaDescricaoDesc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    fun ordenaValorAsc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    fun ordenavalorDesc() : List<Produto>

}