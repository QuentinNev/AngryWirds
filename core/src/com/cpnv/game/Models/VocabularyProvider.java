package com.cpnv.game.Models;

import com.badlogic.gdx.Net;

public class VocabularyProvider {
    private String vocUrlPrefix;
    private Net.HttpRequest http;

    public void create() {
        http = new Net.HttpRequest(Net.HttpMethods.GET);
        vocUrlPrefix = "http://voxerver.mycpnv.ch/api/v1/";
    }

    public void getLanguages() {
        http.setUrl(vocUrlPrefix + "languages");

    }

    public void getVoc(Integer vocId) {
        http.setUrl(vocUrlPrefix + "voc/" + vocId);

    }

    public void getVocs() {
        http.setUrl(vocUrlPrefix + "vocs");

    }

    public void getVocs(Integer lang1, Integer lang2) {
        http.setUrl(vocUrlPrefix + "vocs/" + lang1 + "/" +  lang2);

    }

    public void getFullVocs() {
        http.setUrl(vocUrlPrefix + "fullvocs");

    }

    public void getAssignments(Integer studentToken) {
        http.setUrl(vocUrlPrefix + "assignments" + studentToken);

    }


}
