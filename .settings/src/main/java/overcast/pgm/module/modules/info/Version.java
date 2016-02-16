package overcast.pgm.module.modules.info;

public class Version {

	private int major;
	private int minor;
	private int patch;

	public Version(int major, int minor, int patch) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}

	public int getMajor() {
		return this.major;
	}

	public int getMinor() {
		return this.minor;
	}

	public int getPatch() {
		return this.patch;
	}

	public static Version parse(String text) throws IllegalArgumentException {
		String[] parts = text.split("\\.", 3);
		if (parts.length >= 3) {
			int major = Integer.parseInt(parts[0]);
			int minor = Integer.parseInt(parts[1]);
			int patch = Integer.parseInt(parts[2]);
			return new Version(major, minor, patch);
		} else {
			throw new IllegalArgumentException(
					"a version must be three parts seperated by periods");
		}
	}

	@Override
	public String toString() {
		return major + "." + minor + "." + patch;
	}

	public boolean isGreater(Version xml) {
		return this.major == xml.major
				&& (this.minor > xml.minor || (this.minor == xml.minor && this.patch > xml.patch));
	}

	public boolean isEqual(Version version) {
		return this.major == version.major && this.minor == version.minor
				&& this.patch == version.patch;
	}
}
