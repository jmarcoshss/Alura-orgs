package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.OrgsDataBase
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.extensions.CHAVE_PRODUTO_ID
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.dialog.FormularioImageImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao by lazy {
        val db = OrgsDataBase.instancia(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        title = "Cadastrar Produto"
        configuraDialog()
        editar()


    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        produtoDao.buscaTodosID(produtoId)?.let {
            title = "Editar Produto"
            preencheCampos(it)
        }
    }

    private fun editar() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)


    }

    private fun preencheCampos(produtoCarregado: Produto) {

        url = produtoCarregado.imagem
        binding.activityFormularioProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
        binding.activityFormularioProdutoNome.setText(produtoCarregado.nome)
        binding.activityFormularioProdutoDescricao.setText(produtoCarregado.descricao)
        binding.activityFormularioProdutoValor.setText(produtoCarregado.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
//            if (produtoId > 0) {
//                produtoDao.edita(produtoNovo)
//            } else {
//                produtoDao.salva(produtoNovo)
//            }
            produtoDao.salva(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            uid = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }

    private fun configuraDialog() {
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImageImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
            }
        }
    }

}