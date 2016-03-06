package overcast.pgm.module.modules.projectiles.custom;

import org.bukkit.event.block.Action;

public enum ActionType {

	LEFT(new Action[] { Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK }), RIGHT(
			new Action[] { Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK }), BOTH(new Action[] {
					Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK });

	private Action[] actions;

	ActionType(Action[] actions) {
		this.actions = actions;
	}

	public Action[] getActions() {
		return this.actions;
	}

	public static Action getAction(ActionType type, int i) {
		for (ActionType types : values()) {
			if (type.name().equals(type.name())) {
				for (int c = 0; c < types.getActions().length; c++) {
					Action a = types.getActions()[i];
					if (a != null && c == i) {
						return a;
					}
				}
			}
		}
		return null;
	}
}
