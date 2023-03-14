package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.OrgsDataBase
import br.com.alura.orgs.databinding.ActivityListaProdutosBinding
import br.com.alura.orgs.extensions.CHAVE_PRODUTO_ID
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {

    lateinit var produto: Produto
    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy { ActivityListaProdutosBinding.inflate(layoutInflater) }
    private val produtoDao by lazy {
        OrgsDataBase.instancia(this).produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(produtoDao.buscaTodos())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ordenador_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            abreFormularioProduto()
        }
    }

    private fun abreFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {

        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItemListener = {
            val intent = Intent(this, DetalhesProdutoActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO_ID, it.uid)
            }
            startActivity(intent)

        }

        adapter.quandoClicaEmRemover = {
           produtoDao.remove(it)
            adapter.atualiza(produtoDao.buscaTodos())
        }
        adapter.quandoClicaEmEditar = {
            Intent(this, FormularioProdutoActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO_ID, it.uid)
                startActivity(this)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ordenador_menu_nome_AZ ->{
                adapter.atualiza(produtoDao.ordenaNomeAsc())
            }
            R.id.ordenador_menu_nome_ZA ->{
                adapter.atualiza(produtoDao.ordenaNomeDesc())
            }
            R.id.ordenador_menu_descricao_AZ ->{
                adapter.atualiza(produtoDao.ordenaDescricaoAsc())
            }
            R.id.ordenador_menu_descricao_ZA ->{
                adapter.atualiza(produtoDao.ordenaDescricaoDesc())
            }
            R.id.ordenador_menu_valor_asce ->{
                adapter.atualiza(produtoDao.ordenaValorAsc())
            }
            R.id.ordenador_menu_valor_desc ->{
                adapter.atualiza(produtoDao.ordenavalorDesc())
            }
            R.id.ordenador_menu_desordenado ->{
                adapter.atualiza(produtoDao.buscaTodos())
            }

        }
        return super.onOptionsItemSelected(item)
    }

}