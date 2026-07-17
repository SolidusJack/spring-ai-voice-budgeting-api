package solidusjack.budgeting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record GastoRequest(
    @NotBlank(message = "A categoria do gasto não pode ser vazia.")
    @Size(min = 3, max = 50, message = "A categoria deve ter entre 3 e 50 caracteres.")
    String categoria,

    @Positive(message = "O valor do gasto deve ser maior que zero.")
    double valor
) {}