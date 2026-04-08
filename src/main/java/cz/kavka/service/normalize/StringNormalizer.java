package cz.kavka.service.normalize;

import java.util.UUID;

public class StringNormalizer {

    private StringNormalizer() {

    }

    /**
     * Static method for creating normalized string. It replaces all white spaces in the middle
     * of the given String and all czech characters contained in given String.
     *
     * @param originalFileName original String
     * @param useUuid          if the UUID.randomUUID() should be used or not
     * @return normalized string, ideal for file name
     */
    public static String getNormalizedString(String originalFileName, boolean useUuid) {
        var name = originalFileName.split("\\.")[0];
        var result = name
                .replaceAll("[^a-zA-Z0-9\\s-]", "")
                .replaceAll("\\s+", "_")
                .toLowerCase()
                .trim();

        return useUuid ? result + UUID.randomUUID() : result;


    }
}
