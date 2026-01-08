package kg.attractor.quiz.dto.create;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    @NotBlank(message = "Поле с именем пользователя не должно быть пустым!")
    private String userName;

    @NotBlank(message = "Поле email не может быть пустым!")
    @Email(message = "Поле должно быть указанно в формате qwe@.qwe")
    private String email;

    @NotBlank(message = "Поле пароль не должно быть пустым!")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Пароль должен содержать хотя бы одну заглавную букву и одну цифру!")
    private String password;

    @NotNull(message = "Тип аккаунта не может быть пустым!")
    @Min(value = 1, message = "Доступный тип аккаунта: 1-User")
    @Max(value = 1, message = "Доступный тип аккаунта: 1-User")
    private Integer role;
}
