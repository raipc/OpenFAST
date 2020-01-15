package org.openfast;

import java.util.HashMap;
import java.util.Map;

import org.openfast.template.Group;

public class GCFriendlyGlobalDictionary implements Dictionary {
	protected final Map<QName, MutableContainer> table = new HashMap<>();

	@Override
	public ScalarValue lookup(Group template, QName key, QName applicationType) {
		final MutableContainer mutableContainer = table.get(key);
		return mutableContainer == null ? ScalarValue.UNDEFINED : mutableContainer.value;
	}

	@Override
	public void store(Group group, QName applicationType, QName key, ScalarValue value) {
		table.computeIfAbsent(key, k -> new MutableContainer()).value = value;
	}

	@Override
	public void reset() {
		table.values().forEach(container -> container.value = ScalarValue.UNDEFINED);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dictionary: GCFriendly Global:\n");
		for (Map.Entry<QName, MutableContainer> entry : table.entrySet()) {
			if (entry.getValue().value != ScalarValue.UNDEFINED) {
				builder.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
			}
		}
		return builder.toString();
	}

	private static final class MutableContainer {
		private ScalarValue value = ScalarValue.UNDEFINED;
	}
}
