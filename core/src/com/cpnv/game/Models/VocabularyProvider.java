package com.cpnv.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import org.omg.CORBA.Any;

public class VocabularyProvider {
    private String vocUrlPrefix;
    protected Net.HttpRequest request;
    private JsonReader json;
    private JsonValue jsonValue;

    public VocabularyProvider() {
        request = new Net.HttpRequest(Net.HttpMethods.GET);
        vocUrlPrefix = "http://voxerver.mycpnv.ch/api/v1/";
        json = new JsonReader();
    }

    private void sendRequest() {
        Gdx.app.log("ANGRY", "FUCK SHIT MAN");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String result = httpResponse.getResultAsString();
                Gdx.app.log("ANGRY", result);

            }

            @Override
            public void failed(Throwable throwable) {
                Gdx.app.error("ANGRY", "Error",throwable);
            }

            @Override
            public void cancelled() {

            }
        });
    }

    public void getLanguages() {
        request.setUrl(vocUrlPrefix + "languages");
        sendRequest();

    }

    public void getVoc(Integer vocId) {
        request.setUrl(vocUrlPrefix + "voc/" + vocId);

    }

    public void getVocs() {
        request.setUrl(vocUrlPrefix + "vocs");

    }

    public void getVocs(Integer lang1, Integer lang2) {
        request.setUrl(vocUrlPrefix + "vocs/" + lang1 + "/" +  lang2);

    }

    public void getFullVocs() {
        request.setUrl(vocUrlPrefix + "fullvocs");

    }

    public void getAssignments(Integer studentToken) {
        request.setUrl(vocUrlPrefix + "assignments" + studentToken);

    }


}
