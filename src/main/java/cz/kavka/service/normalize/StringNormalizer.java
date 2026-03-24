package cz.kavka.service.normalize;

import java.util.UUID;

public class StringNormalizer {

    private StringNormalizer() {

    }

    public static String getNormalizedString(String originalFileName, boolean useUuid) {
        var name = originalFileName.split("\\.")[0];
        var result = name
                .replaceAll("[^a-zA-Z0-9\\s-]", "")
                .replaceAll("\\s+", "_")
                .toLowerCase()
                .trim();
        if (useUuid) {
            return result + UUID.randomUUID();
        } else {
            return result;
        }


    }
}
