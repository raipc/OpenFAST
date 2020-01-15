package org.openfast;

import java.util.HashMap;
import java.util.Map;

public class Dictionaries {
	private final Dictionary globalDictionary;
	private final TemplateDictionary templateDictionary = new TemplateDictionary();
	private final ApplicationTypeDictionary typeDictionary = new ApplicationTypeDictionary();
	private final Map<String, Dictionary> dictionaries = new HashMap<>();

	public Dictionaries(Dictionary dictionary) {
		this.globalDictionary = dictionary;
	}

	public Dictionaries() {
		this.globalDictionary = new GlobalDictionary();
	}

	public Dictionary getDictionary(String name) {
		if (Dictionary.GLOBAL.equals(name)) {
			return globalDictionary;
		} else if (Dictionary.TEMPLATE.equals(name)) {
			return templateDictionary;
		} else if (Dictionary.TYPE.equals(name)) {
			return typeDictionary;
		}
		return dictionaries.computeIfAbsent(name, dict -> new GlobalDictionary());
	}

	public void reset() {
		globalDictionary.reset();
		templateDictionary.reset();
		typeDictionary.reset();
		dictionaries.values().forEach(Dictionary::reset);
	}
}
