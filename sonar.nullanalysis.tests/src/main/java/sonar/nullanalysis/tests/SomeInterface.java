package sonar.nullanalysis.tests;

import org.eclipse.jdt.annotation.Nullable;

public interface SomeInterface {
	public @Nullable String getStringNullable();
	public @Nullable String getStringNullableNeverReturnsNull();
	public String getStringNonNull();
	public String getStringNonNullWithBug();
}