package com.cpnv.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.cpnv.game.Models.Voc.Assignment;
import com.cpnv.game.Models.Voc.Language;
import com.cpnv.game.Models.Voc.Vocabulary;

import java.util.ArrayList;

public class VocabularyProvider {
    private String vocUrlPrefix;
    protected Net.HttpRequest request;
    private Json json;
    private JsonReader jsonReader;
    protected String result;

    // Datas
    private ArrayList<Language> languages;
    private ArrayList<Vocabulary> vocs;
    private ArrayList<Assignment> assignments;

    public VocabularyProvider() {
        request = new Net.HttpRequest(Net.HttpMethods.GET);
        vocUrlPrefix = "http://voxerver.mycpnv.ch/api/v1/";
        json = new Json();
        jsonReader = new JsonReader();

        languages = new ArrayList<Language>();
        vocs = new ArrayList<Vocabulary>();
        assignments = new ArrayList<Assignment>();
    }

    public void sendRequest() {
        // Get voc
        request.setUrl(vocUrlPrefix + "languages");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                JsonValue languagesDatas = jsonReader.parse(httpResponse.getResultAsString());
                for (JsonValue language : languagesDatas){
                    languages.add(new Language(language.getString("lName"), language.getInt("lId")));
                }
                for (Language language : languages) {
                    Gdx.app.log("ANGRY - Language", "" + language.getName());
                }
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
