package overcast.pgm.module;

import java.util.ArrayList;
import java.util.Collections;

public class ModuleCollection<M extends Module> extends ArrayList<M> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4056571027520358092L;

	public ModuleCollection() {

	}

	@SafeVarargs
	public ModuleCollection(M... modules) {
		Collections.addAll(this, modules);
	}

	@SuppressWarnings("unchecked")
	public <T extends Module> T getModule(Class<T> clazz) {
		for (Module module : this) {
			if (clazz.isInstance(module)) {
				return ((T) module);
			}
		}
		return null;
	}

	public boolean isModuleLoaded(Class<? extends Module> clazz) {
		return getModule(clazz) != null ? true : false;
	}

	/**
	 * Used to get all modules of a certain type
	 *
	 * @param clazz
	 *            Class which represents the modules
	 * @param <T>
	 *            Module type to be filtered
	 * @return A new module
	 */
	@SuppressWarnings("unchecked")
	public <T extends Module> ModuleCollection<T> getModules(Class<T> clazz) {
		ModuleCollection<T> results = new ModuleCollection<T>();
		for (Module module : this) {
			if (clazz.isInstance(module))
				results.add((T) module);
		}
		return results;
	}

	/**
	 * @return Random module from this
	 */
	@SuppressWarnings("unchecked")
	public M getRandom() {
		ModuleCollection<?> copy = (ModuleCollection<?>) this.clone();
		Collections.shuffle(copy);
		return (M) copy.get(0);

	}
}
