package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import javax.transaction.Transactional

@Controller("/autores")
class DeletaAutoresController(val autoresRepository: AutorRepository) {


    @Delete("/{id}")
    @Transactional
    fun deleta(@PathVariable id: Long): HttpResponse<Any> {
        val possivelAutor = autoresRepository.findById(id)
        if(possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }
        autoresRepository.deleteById(id)
        return HttpResponse.ok()
    }
}