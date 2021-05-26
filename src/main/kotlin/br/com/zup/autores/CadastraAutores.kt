package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutores(val autorRepository: AutorRepository) {

    @Post
    fun cadastraNovoAutor(@Body @Valid request: NovoAutorRequest): HttpResponse<Void> {
        val novoAutor = request.paraAutor()
        autorRepository.save(novoAutor)
        return HttpResponse.ok()
    }
}