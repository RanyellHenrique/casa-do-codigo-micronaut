package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutoresController(
    val autorRepository: AutorRepository,
    val cepClient: CepClient
) {

    @Post
    @Transactional
    fun cadastraNovoAutor(@Body @Valid request: NovoAutorRequest): HttpResponse<Any> {
        val consulta = cepClient.consulta(request.cep).body()
        val novoAutor = request.paraAutor(consulta!!)
        val autor = autorRepository.save(novoAutor)
        val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))
        return HttpResponse.created(uri)
    }
}