package sizebay.my.modules.modeling.infra.database.kikahaJdbi.entities;

import kikaha.jdbi.serializers.Column;
import kikaha.jdbi.serializers.Entity;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "create")
public class ModelingImportationErrorByCSV {

	@Column("mic_id")
	private long id;

	@NonNull
	@Column("mic_tenant_id")
	private Long tenantId;

	@NonNull
	@Column("mic_message")
	private String message;

	@Column("created_at")
	private Date createdAt;

}
