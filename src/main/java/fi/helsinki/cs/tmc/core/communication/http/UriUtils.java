package fi.helsinki.cs.tmc.core.communication.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UriUtils {
    public static String withQueryParam(String uri, String name, String value) {
        return withQueryParam(URI.create(uri), name, value).toString();
    }

    public static URI withQueryParam(URI uri, String name, String value) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(uri, "UTF-8");
        Iterator<NameValuePair> iterator = pairs.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(name)) {
                iterator.remove();
                break;
            }
        }
        List<NameValuePair> newPairs = new ArrayList<>(pairs.size() + 1);
        newPairs.addAll(pairs);

        newPairs.add(new BasicNameValuePair(name, value));
        String newQuery = URLEncodedUtils.format(newPairs, "UTF-8");
        try {
            return new URI(
                    uri.getScheme(),
                    uri.getUserInfo(),
                    uri.getHost(),
                    uri.getPort(),
                    uri.getPath(),
                    newQuery,
                    uri.getFragment());
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
