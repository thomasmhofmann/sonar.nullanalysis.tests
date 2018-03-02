package sonar.nullanalysis.tests;

import org.eclipse.jdt.annotation.Nullable;

@SuppressWarnings({ "squid:S106", "squid:S1192", "squid:S3400" })
public class SomeClass implements SomeInterface {

	public static void main(String[] args) {
		SomeInterface instanceA = new SomeClass();

		String stringNonNull = instanceA.getStringNonNull();
		if (stringNonNull.contains("some")) {
			System.out.println("All should be fine");
		}

		String stringNonNull2 = instanceA.getStringNonNull();
		if (stringNonNull2 == null) {
			System.out.println("Should report condition is always false");
		}
		if (stringNonNull.contains("some")) {
			System.out.println("All should be fine");
		}

		String stringNonNullWithBug = instanceA.getStringNonNullWithBug();
		if (stringNonNullWithBug.contains("some")) {
			System.out.println("All should be fine. Should not report a NPE here.");
		}

		String stringNullable = instanceA.getStringNullable();
		if (stringNullable == null) {
			System.out.println("This null check is valid. Should NOT report condition is always false");
		} else {
			System.out.println("not null");
		}
		if (stringNullable.contains("some")) {
			System.out.println("should report potential NPE");
		}

		String stringNullable2 = instanceA.getStringNullable();
		if (stringNullable2.contains("some")) {
			System.out.println("should report potential NPE");
		}

		String stringNullableNeverNull = instanceA.getStringNullableNeverReturnsNull();
		if (stringNullableNeverNull == null) {
			System.out.println("This null check is valid. Should NOT report condition is always false");
		} else {
			System.out.println("not null");
		}
		if (stringNullableNeverNull.contains("some")) {
			System.out.println("should report potential NPE");
		}
	}

	/**
	 * @return always null. This is ok since this method is marked with @Nullable
	 *         and this should override the package default.
	 */
	public @Nullable String getStringNullable() {
		return null;
	}

	/**
	 * @return never null although this method is marked with @Nullable. The
	 *         analyzer should not care about the implementation and assume null can
	 *         be returned.
	 */
	public @Nullable String getStringNullableNeverReturnsNull() {
		return "notNull";
	}

	/**
	 * @return never null. The package default @NonNull should be applied to this
	 *         method.
	 */
	public String getStringNonNull() {
		return "someValue";
	}

	/**
	 * @return always null. The package default @NonNull should be applied to this
	 *         method. The analyzer should mark this method as buggy. Call site
	 *         should not need to do a null check.
	 */
	public String getStringNonNullWithBug() {
		return null;
	}

}
