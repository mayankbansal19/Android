package com.example.questionaire.json;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser
{
    private JSONObject jsonParse = new JSONObject();
    
    public JsonParser(String jsonStringToParse)
    {
        try
        {
            jsonParse = new JSONObject(jsonStringToParse);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        
    }
}