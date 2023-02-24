package br.com.alura.orgs.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import br.com.alura.orgs.databinding.ProdutoImagemBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem


class FormularioImageImagemDialog ( private val context:Context){

    fun mostra(
        urlPadrao: String? = null,
        quandoImagemCarregada: (imagem: String) -> Unit
    ) {
        ProdutoImagemBinding.inflate(LayoutInflater.from(context)).apply {
            urlPadrao?.let {
                produtoImagemImageview.tentaCarregarImagem(it)
                produtoImagemUrl.setText(it)

            }
            produtoImagemButtom.setOnClickListener {
                val url = produtoImagemUrl.text.toString()
                produtoImagemImageview.tentaCarregarImagem(url)

            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("confirmar") { _, _ ->
                    val url = produtoImagemUrl.text.toString()
                    quandoImagemCarregada(url)



                }
                .setNegativeButton("cancelar") { _, _ ->

                }
                .show()
        }


    }
}