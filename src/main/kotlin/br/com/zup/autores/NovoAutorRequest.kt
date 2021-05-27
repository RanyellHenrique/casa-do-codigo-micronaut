package br.com.zup.autores

import br.com.zup.compartilhado.Cep
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class NovoAutorRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank @field:Size(max = 400) val descricao: String,
    @field:NotBlank @field:Cep val cep: String,
    @field:NotBlank val complemento: String
) {
    fun paraAutor(endereco: EnderecoResponse) : Autor{
        return Autor(nome, email, descricao , Endereco(endereco, complemento))
    }

}
