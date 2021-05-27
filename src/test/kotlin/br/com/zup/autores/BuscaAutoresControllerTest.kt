package br.com.zup.autores

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.inject.Inject

@MicronautTest
internal class BuscaAutoresControllerTest {

    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var autor: Autor

    @BeforeEach
    internal fun setup() {
        val enderecoResponse = EnderecoResponse(
            "QR 502 Conjunto 12",
            "Samambaia Sul (Samambaia)",
            "Brasília",
            "DF"
        )
        val endereco = Endereco(
            enderecoResponse,
            "casa 4"
        )
        autor = Autor(
            "ramon",
            "ramon@email.com",
            "autor8",
            endereco
        )

        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown() {
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um autor`() {
        val response = client.toBlocking().exchange("/autores?email=${autor.email}", AutorResponse::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.descricao, response.body()!!.descricao)
        assertEquals(autor.email, response.body()!!.email)
    }

    @Test
    internal fun `deve retornar status code 404 quando o email informado nao existir`() {
        assertThrows<HttpClientResponseException>{
            client.toBlocking().exchange("/autores?email=emailInexistente", Any::class.java)
        }.let {
            assertEquals(HttpStatus.NOT_FOUND, it.status)
        }
    }

    @Test
    internal fun `deve retornar uma lista de autores quando nenhum email for informado na url`() {
        val response = client.toBlocking().exchange("/autores", List::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertEquals(response.body().size, 1)
        assertNotNull(response.body())
    }
}