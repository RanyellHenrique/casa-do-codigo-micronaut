package br.com.zup.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastraAutoresControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @field:Inject
    lateinit var cepClient: CepClient

    lateinit var enderecoResponse: EnderecoResponse

    @BeforeEach
    fun setup() {
        enderecoResponse = EnderecoResponse("QC 06 CJ 02", "Samambaia", "Brasília", "DF")

    }

    @Test
    internal fun `deve criar um autor`() {
        val novoAutorRequest = NovoAutorRequest("Bobtest","bob@email.com", "autor 1", "72310412", "casa 09")
        Mockito.`when`(cepClient.consulta(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("/autores", novoAutorRequest)
        val response = client.toBlocking().exchange(request, Any::class.java)
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.matches("/autores/\\d*".toRegex()))
    }

    @Test
    internal fun `deve retornar 400 quando algum atributo for invalido`() {
        val novoAutorRequest = NovoAutorRequest("","bob@email.com", "autor 1", "72310412", "casa 09")


        val request = HttpRequest.POST("/autores", novoAutorRequest)

        assertThrows <HttpClientResponseException> {
            client.toBlocking().exchange(request, Any::class.java)
        }.let {
            assertEquals(HttpStatus.BAD_REQUEST, it.status)
        }
    }


    @MockBean(CepClient::class)
    fun cepMock(): CepClient {
        return Mockito.mock(CepClient::class.java)
    }

}