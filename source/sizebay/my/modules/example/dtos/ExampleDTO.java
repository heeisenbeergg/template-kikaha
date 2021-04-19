package sizebay.my.modules.example.dtos;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "create")
public class ExampleDTO {

	private Long id;
	private String name;

}
