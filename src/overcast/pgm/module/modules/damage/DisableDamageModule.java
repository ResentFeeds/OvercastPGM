package overcast.pgm.module.modules.damage;

import java.util.Set;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;

@ModuleInfo(name = "damage", desc = "disable damage defined damage in the XML")
public class DisableDamageModule extends Module {

	private Set<DamageCause> causes;

	private boolean self;

	private boolean ally;

	private boolean enemy;

	private boolean other;

	public DisableDamageModule(Set<DamageCause> causes) {
		this.causes = causes;
	}

	public Set<DamageCause> getCauses() {
		return this.causes;
	}

	@Override
	public MatchModule createMatchModule(Match match) {
		return new DisableDamageMatchModule(match, this.causes, this.self, this.ally, this.enemy, this.other);
	}

	@Override
	public Class<? extends Builder> builder() {
		return DisableDamageBuilder.class;
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public boolean isSelf() {
		return this.self;
	}

	public void setAlly(boolean ally) {
		this.ally = ally;
	}

	public boolean isAlly() {
		return this.ally;
	}

	public void setEnemy(boolean enemy) {
		this.enemy = enemy;
	}

	public boolean isEnemy() {
		return this.enemy;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public boolean isOther() {
		return this.other;
	}
}
