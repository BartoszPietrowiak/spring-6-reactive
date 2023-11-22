package guru.springframework.spring6reactive.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private Integer id;
    @NotBlank
    @NotNull
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
