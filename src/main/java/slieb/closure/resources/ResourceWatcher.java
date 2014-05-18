package slieb.closure.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ResourceWatcher {

    private final ResourceProvider provider;

    private final Map<Resource, String> hashMap = new HashMap<>();

    private final MessageDigest digest;

    public ResourceWatcher(ResourceProvider provider) throws Exception {
        this.provider = provider;
        this.digest = MessageDigest.getInstance("MD5");
    }

    public Boolean scan() throws IOException {
        return syncMap(hashMap, getHashMap(provider.getResources()));
    }

    private String getResourceMD5(Resource resource) throws IOException {
        digest.reset();
        try (BufferedReader bufferedReader = new BufferedReader(resource.getReader())) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                digest.update(line.getBytes());
            }
            bufferedReader.close();
        }
        return new BigInteger(1, digest.digest()).toString(16);
    }

    private Map<Resource, String> getHashMap(Iterable<Resource> resources) throws IOException {
        final Map<Resource, String> hashMap = new HashMap<>();
        for (Resource resource : resources) {
            hashMap.put(resource, getResourceMD5(resource));
        }
        return hashMap;
    }


    private boolean syncMap(final Map<Resource, String> oldMap,
                            final Map<Resource, String> newMap) {
        boolean hasChanged = false;

        Set<Resource> newResources = newMap.keySet();

        Iterator<Resource> oldIterator = oldMap.keySet().iterator();
        while (oldIterator.hasNext()) {
            Resource oldResource = oldIterator.next();
            if (!newResources.contains(oldResource)) {
                oldIterator.remove();
                hasChanged = true;
            }
        }

        for (Map.Entry<Resource, String> entry : newMap.entrySet()) {
            Resource resource = entry.getKey();
            String value = entry.getValue();
            if (!oldMap.containsKey(resource) || !value.equals(oldMap.get(resource))) {
                oldMap.put(resource, value);
                hasChanged = true;
            }
        }


        return hasChanged;
    }
}
