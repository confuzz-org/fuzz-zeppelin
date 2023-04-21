package org.apache.zeppelin.conf;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import edu.illinois.confuzz.internal.ConfigTracker;
import edu.illinois.confuzz.internal.ConfuzzGenerator;

import java.io.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigurationGenerator extends Generator<Integer> {
    private static Map<String, String> generatedConf = null;
    private static String setMethodName = "generatorSet";
    private static Class<?>[] setParameterTypes = {String.class, String.class};
    /**
     * Constructor for Configuration Generator
     */
    public ConfigurationGenerator() throws IOException {
        super(Integer.class);
    }

    public static Map<String, String> getGeneratedConf() {
        if (generatedConf == null) {
            return null;
        }
        return Collections.unmodifiableMap(generatedConf);
    }

    /**
     * This method is invoked to generate a Configuration object
     * @param random
     * @param generationStatus
     * @return
     */
    @Override
    public Integer generate(SourceOfRandomness random, GenerationStatus generationStatus) {
        generatedConf = new LinkedHashMap<>();
        try {
            for (Map.Entry<String, Object> entry: ConfuzzGenerator.generate(random).entrySet()) {
                generatedConf.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            ZeppelinConfiguration.reset();
            return 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}