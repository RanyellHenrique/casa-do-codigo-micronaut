package br.com.zup.compartilhado

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Target(FIELD, CONSTRUCTOR)
@Retention(RUNTIME)
@Constraint(validatedBy = [CepValidator::class])
annotation class Cep(
    val message: String = "Cep com formato inválido"
)

@Singleton
class CepValidator : ConstraintValidator<Cep, String> {
    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<Cep>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(value == null) {
            return true
        }
        return value.matches("^\\d{5}-?\\d{3}".toRegex())
    }

}
