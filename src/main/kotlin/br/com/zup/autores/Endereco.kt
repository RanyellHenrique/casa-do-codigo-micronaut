package br.com.zup.autores

import javax.persistence.Embeddable

@Embeddable
class Endereco(
    enderecoResponse: EnderecoResponse,
    val complemeto: String
) {
    val bairro = enderecoResponse.bairro
    val localidade = enderecoResponse.localidade
    val logradouro = enderecoResponse.logradouro
    val uf = enderecoResponse.uf
}
