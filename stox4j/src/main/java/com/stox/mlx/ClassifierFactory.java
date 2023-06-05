package com.stox.mlx;

import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

@Slf4j
public class ClassifierFactory {
	
	public static final ClassifierFactory INSTANCE = new ClassifierFactory();

	private final Map<String, Classifier> cache = new HashMap<>();
	
	private ClassifierFactory() {
	}
	
	@SneakyThrows
	public synchronized Classifier getClassifier(String path) {
		final Classifier cachedClassfier = cache.get(path);
		if(null == cachedClassfier) {
			log.info("Loading classifier {}", path);
			final Classifier classifier = (Classifier) SerializationHelper.read(path);
			log.info("Successfull loaded classifier {}", path);
			cache.put(path, classifier);
			return classifier;
		}
		return  cachedClassfier;
	}
	
}
