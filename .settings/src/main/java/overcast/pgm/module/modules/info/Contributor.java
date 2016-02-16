package overcast.pgm.module.modules.info;

import java.util.UUID;

public class Contributor {

	private UUID uuid;
	private String contribution;

	public Contributor(UUID uuid, String contribution) {
		this.uuid = uuid;
		this.contribution = contribution;
	}

	public Contributor(UUID uuid) {
		this(uuid, null);
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public String getContribution() {
		return this.contribution;
	}

	public boolean hasContribution() {
		return this.getContribution() != null;
	}
}
