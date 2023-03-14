package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.OrgsDataBase
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.CHAVE_PRODUTO_ID
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {

    private var produtoid: Long = 0L
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    private val produtodao by lazy {
        OrgsDataBase.instancia(this).produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProdutoNoBanco()
    }

    private fun buscaProdutoNoBanco() {
        produto = produtodao.buscaTodosID(produtoid)
        produto?.let {
            preencheCampos(it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun tentaCarregarProduto() {
        produtoid = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            detalhesProdutoImageview.tentaCarregarImagem(produtoCarregado.imagem)
            detalhesProdutoNome.text = produtoCarregado.nome
            detahesProdutoDescricao.text = produtoCarregado.descricao
            detalhesProdutoValor.text = produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(CHAVE_PRODUTO_ID, produtoid)
                    startActivity(this)
                }
            }
            R.id.menu_detalhes_produto_remover -> {
                produto?.let {
                    produtodao.remove(it)
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}