package org.apache.bikerenting1.support;







import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

import org.json.JSONObject;
    public class TransformerSupport {
        public static void main(String[] args){

            System.out.println(getGoogleApiParsedData("M St & New Jersey Ave SE"));
        }
        public static String getGoogleApiParsedData(String address) {
            StringBuffer response = new StringBuffer();
            try {
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+ address.replaceAll(" ", "+").replaceAll("&", "%26") +"&key=AIzaSyC6olfykqlejxR4C064QcJpgBupqwl7jD4";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }

            } catch (Exception e) {
                System.out.println(e);
            }
            return getFormattedAddress(response.toString()) + ":" + getLatLong(response.toString());
        }
                //print in String
                // System.out.println(response.toString());


                private static String getFormattedAddress(String response){

                    // Get full Address from response
                    JSONObject resultsObject = new JSONObject(response).getJSONArray("results").getJSONObject(0);
                    String formattedAddress = resultsObject.get("formatted_address").toString();
                    return formattedAddress;
                }


                private static String getLatLong(String response){

                    // Get Lat and Long from response
                    JSONObject resultsObject = new JSONObject(response).getJSONArray("results").getJSONObject(0);
                    JSONObject location = resultsObject.getJSONObject("geometry").getJSONObject("location");
                    String latitude = location.get("lat").toString();
                    String longitude = location.get("lng").toString();

                    return latitude + " " + longitude;
                }








            }











