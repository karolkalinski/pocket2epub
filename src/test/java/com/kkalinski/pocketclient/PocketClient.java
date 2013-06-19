package com.kkalinski.pocketclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class PocketClient {

    private static final String CONSUMER_KEY = "15557-ff6d3a1e3ce6adfc2795c2ef";
    private final HttpHost proxy = new HttpHost("192.168.129.20", 8080, "http");

    public void authorize() throws UnsupportedEncodingException {
        CloseableHttpClient closeableHttpClient = getHttpClient();
        final HttpPost post = new HttpPost("https://getpocket.com/v3/oauth/request");
        final List<NameValuePair> nvps = getData();
        nvps.add(new BasicNameValuePair("redirect_uri", "index.html"));
        post.setEntity(new UrlEncodedFormEntity(nvps));
        final String response = execute(closeableHttpClient, post);
        final String code = response.substring(5);
        final String gotoUri = String.format(
            "https://getpocket.com/auth/authorize?request_token=%s&redirect_uri=http://unikat.nazwa.pl/karol/gpx/ladowanie/",
            code);

        final HttpPost postForAccessToken = new HttpPost("https://getpocket.com/v3/oauth/authorize");
        System.out.println(gotoUri);
        nvps.add(new BasicNameValuePair("code", code));
        postForAccessToken.setEntity(new UrlEncodedFormEntity(nvps));
        closeableHttpClient = getHttpClient();
        final String accessTokenResponse = execute(closeableHttpClient, postForAccessToken);
        final PocketAccessToken accessToken = getAccessToken(accessTokenResponse);
        System.out.println(accessToken.getAccessToken());

    }

    private String execute(final CloseableHttpClient httpclient, final HttpPost post) {
        final ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String result = "";
        try (CloseableHttpClient client = httpclient) {
            final String responseBody = httpclient.execute(post, responseHandler);
            System.out.println(responseBody);
            result = responseBody;
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return result;
    }

    private PocketAccessToken getAccessToken(final String accessTokenResponse) {
        final String[] split = accessTokenResponse.split("&");
        final String username = split[1].substring(9);
        final String token = split[0].substring(13);
        return new PocketAccessToken(username, token);
    }

    private List<NameValuePair> getData() {
        final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("consumer_key", CONSUMER_KEY));
        nvps.add(new BasicNameValuePair("access_token", "5dfe40a0-04f3-57c3-56f7-8c8a8f"));
        return nvps;
    }

    private CloseableHttpClient getHttpClient() {
        final RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();
        final CloseableHttpClient httpclient = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .build();
        return httpclient;
    }

    public List<String> retriveDocumentsList() throws UnsupportedEncodingException {
        final CloseableHttpClient httpclient = getHttpClient();

        final List<String> result = new ArrayList<>();
        final HttpPost post = new HttpPost("https://getpocket.com/v3/get");
        final List<NameValuePair> nvps = getData();
        post.setEntity(new UrlEncodedFormEntity(nvps));

        execute(httpclient, post);

        return result;
    }
}
