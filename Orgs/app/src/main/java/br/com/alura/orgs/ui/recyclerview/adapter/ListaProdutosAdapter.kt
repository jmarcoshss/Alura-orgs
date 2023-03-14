package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto> = emptyList(),
    var quandoClicaNoItemListener:(produto: Produto) -> Unit = {},
    var quandoClicaEmRemover: (produtos: Produto) -> Unit = {},
    var quandoClicaEmEditar: (produtos: Produto) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ViewHolder(private val binding:ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    quandoClicaNoItemListener(produto)
                }
            }

            itemView.setOnLongClickListener{
                if (::produto.isInitialized) {
                    showPopup(it)
                }
                true
            }
        }

        fun vincula(produto: Produto) {

            this.produto = produto
            val nome = binding.produtoItemNome
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.produtoItemValor
            val valorEmMoedaBrasileira = produto.valor.formataParaMoedaBrasileira()
            valor.text = valorEmMoedaBrasileira

            val visibilidade = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.produtoItemImageView.visibility = visibilidade

            binding.produtoItemImageView.tentaCarregarImagem(produto.imagem)

        }

        fun showPopup(v: View) {
            PopupMenu(context, v).apply {
                setOnMenuItemClickListener(this@ViewHolder)
                inflate(R.menu.menu_detalhes_produto)
                show()
            }
        }
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.menu_detalhes_produto_editar -> {
                    quandoClicaEmEditar(produto)
                    true
                }
                R.id.menu_detalhes_produto_remover -> {
                    quandoClicaEmRemover(produto)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)

    }

    override fun getItemCount(): Int = produtos.size

    fun atualiza(produtos:List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}
