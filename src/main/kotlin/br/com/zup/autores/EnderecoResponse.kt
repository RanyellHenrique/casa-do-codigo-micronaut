package br.com.zup.autores

data class EnderecoResponse(
    val logradouro: String,
    val bairro: String,
    val localidade: String,
    val uf: String
) {
}
