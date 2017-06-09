package net.sqindia.movhaulagent.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Salman on 19-05-2017.
 */

public class Config_Utils {


    public static final String TAG = "tagH";
    public static final String WEB_URL ="http://104.197.80.225:3030/" ;


    public static String makeRequest(String url, String json) {
        Log.v(TAG, "URL-->" + url);
        Log.v(TAG, "input-->" + json);


        try {
            Log.v(TAG, "inside-->");

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null) {
                String result = convertInputStreamToString(inputStream);
                Log.e(TAG, "output-->" + result);
                return result;
            } else {
                Log.e(TAG, "output-->" + inputStream);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String makeRequest1(String url, String json,String id,String token) {
        Log.e(TAG, "URL-->" + url);
        Log.e(TAG, "input-->" + json);


        try {
            Log.v(TAG, "inside-->");

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("id",id);
            httpPost.setHeader("sessiontoken",token);

            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);


            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null) {
                String result = convertInputStreamToString(inputStream);
                Log.e(TAG, "output-->" + result);
                return result;
            } else {
                Log.e(TAG, "output-->" + inputStream);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JSONObject getData(String url, String id, String token) throws JSONException {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setHeader("id",id);
            httppost.setHeader("sessiontoken",token);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("tag", "Error in http connection " + e.toString());
            result = "sam";
            is = null;
            return jArray;

        }

        // Convert response to string

        if (is.equals(null)) {

            result = "sam";
            jArray = new JSONObject(result);
            return jArray;

        } else {


            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
                Log.e("tag", "Error converting result " + e.toString());
                result = "sam";
            }

            try {

                jArray = new JSONObject(result);
            } catch (JSONException e) {
                Log.e("tag", result);
                Log.e("tag", jArray.toString());
                Log.e("tag", "Error parsing data " + e.toString());


            }

            return jArray;
        }

    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        System.out.println(" OUTPUT -->" + result);
        return result;
    }



    public String states[] = new String[]{
            "Abia State",
            "Adamawa State",
            "Akwa Ibom State",
            "Anambra State",
            "Bauchi State",
            "Bayelsa State",
            "Benue State",
            "Borno State",
            "Cross River State",
            "Delta State",
            "Ebonyi State",
            "Edo State",
            "Ekiti State",
            "Enugu State",
            "FCT",
            "Gombe State",
            "Imo State",
            "Jigawa State",
            "Kaduna State",
            "Kano State",
            "Katsina State",
            "Kebbi State",
            "Kogi State",
            "Kwara State",
            "Lagos State",
            "Nasarawa State",
            "Niger State",
            "Ogun State",
            "Ondo State",
            "Osun State",
            "Oyo State",
            "Plateau State",
            "Rivers State",
            "Sokoto State",
            "Taraba State",
            "Yobe State",
            "Zamfara State"
    };


    public String AbiaState[] = new String[]{"Abadam",
            "Abaji",
            "Abak",
            "Abakaliki",
            "Aba North",
            "Aba South",
            "Abeokuta North",
            "Abeokuta South",
            "Abi",
            "Aboh Mbaise",
            "Abua/Odual",
            "Adavi",
            "Ado Ekiti",
            "Ado-Odo/Ota",
            "Afijio",
            "Afikpo North",
            "Afikpo South (Edda)"};


    public String AdamawaState[] = new String[]{

            "Agaie",
            "Agatu",
            "Agwara",
            "Agege",
            "Aguata",
            "Ahiazu Mbaise",
            "Ahoada East",
            "Ahoada West",
            "Ajaokuta",
            "Ajeromi-Ifelodun",
            "Ajingi",
            "Akamkpa",
            "Akinyele",
            "Akko",
            "Akoko-Edo",
            "Akoko North-East",
            "Akoko North-West",
            "Akoko South-West",
            "Akoko South-East",
            "Akpabuyo",
            "Akuku-Toru"

    };

    public String AkwaIbomState[] = new String[]{
            "Akure North",
            "Akure South",
            "Akwanga",
            "Albasu",
            "Aleiro",
            "Alimosho",
            "Alkaleri",
            "Amuwo-Odofin",
            "Anambra East",
            "Anambra West",
            "Anaocha",
            "Andoni",
            "Aninri",
            "Aniocha North",
            "Aniocha South",
            "Anka",
            "Ankpa",
            "Apa",
            "Apapa",
            "Ado",
            "Ardo Kola",
            "Arewa Dandi",
            "Argungu",
            "Arochukwu",
            "Asa",
            "Asari-Toru",
            "Askira/Uba",
            "Atakunmosa East",
            "Atakunmosa West",
            "Atiba",
            "Atisbo"};


    public String AnambraState[] = new String[]{

            "Augie",
            "Auyo",
            "Awe",
            "Awgu",
            "Awka North",
            "Awka South",
            "Ayamelum",
            "Aiyedaade",
            "Aiyedire",
            "Babura",
            "Badagry",
            "Bagudo",
            "Bagwai",
            "Bakassi",
            "Bokkos",
            "Bakori",
            "Bakura",
            "Balanga",
            "Bali",
            "Bama",
            "Bade"

    };


    public String BauchiState

            [] = new String[]{

            "Barkin Ladi",
            "Baruten",
            "Bassa",
            "Bassa",
            "Batagarawa",
            "Batsari",
            "Bauchi",
            "Baure",
            "Bayo",
            "Bebeji",
            "Bekwarra",
            "Bende",
            "Biase",
            "Bichi",
            "Bida",
            "Billiri",
            "Bindawa",
            "Binji",
            "Biriniwa",
            "Birnin Gwari"


    };


    public String BayelsaState
            [] = new String[]{
            "Birnin Kebbi",
            "Birnin Kudu",
            "Birnin Magaji/Kiyaw",
            "Biu",
            "Bodinga",
            "Bogoro",
            "Boki",
            "Boluwaduro"


    };


    public String BenueState
            [] = new String[]{
            "Bomadi",
            "Bonny",
            "Borgu",
            "Boripe",
            "Bursari",
            "Bosso",
            "Brass",
            "Buji",
            "Bukkuyum",
            "Buruku",
            "Bungudu",
            "Bunkure",
            "Bunza",
            "Burutu",
            "Bwari",
            "Calabar Municipal",
            "Calabar South",
            "Chanchaga",
            "Charanchi",
            "Chibok",
            "Chikun",
            "Dala",
            "Damaturu"


    };


    public String BornoState
            [] = new String[]{
            "Damban",
            "Dambatta",
            "Damboa",
            "Dandi",
            "Dandume",
            "Dange Shuni",
            "Danja",
            "Dan Musa",
            "Darazo",
            "Dass",
            "Daura",
            "Dawakin Kudu",
            "Dawakin Tofa",
            "Degema",
            "Dekina",
            "Demsa",
            "Dikwa",
            "Doguwa",
            "Doma",
            "Donga",
            "Dukku",
            "Dunukofia",
            "Dutse",
            "Dutsi",
            "Dutsin Ma",
            "Eastern Obolo",
            "Ebonyi"


    };

    public String CrossRiverState
            [] = new String[]{
            "Edati",
            "Ede North",
            "Ede South",
            "Edu",
            "Ife Central",
            "Ife East",
            "Ife North",
            "Ife South",
            "Efon",
            "Yewa North",
            "Yewa South",
            "Egbeda",
            "Egbedore",
            "Egor",
            "Ehime Mbano",
            "Ejigbo",
            "Ekeremor",
            "Eket"


    };

    public String DeltaState
            [] = new String[]{

            "Ekiti",
            "Ekiti East",
            "Ekiti South-West",
            "Ekiti West",
            "Ekwusigo",
            "Eleme",
            "Emuoha",
            "Emure",
            "Enugu East",
            "Enugu North",
            "Enugu South",
            "Epe",
            "Esan Central",
            "Esan North-East",
            "Esan South-East",
            "Esan West",
            "Ese Odo",
            "Esit Eket",
            "Essien Udim",
            "Etche",
            "Ethiope East",
            "Ethiope West",
            "Etim Ekpo",
            "Etinan",
            "Eti Osa"

    };

    public String EbonyiState
            [] = new String[]{

            "Etsako Central",
            "Etsako East",
            "Etsako West",
            "Etung",
            "Ewekoro",
            "Ezeagu",
            "Ezinihitte",
            "Ezza North",
            "Ezza South",
            "Fagge",
            "Fakai",
            "Faskari",
            "Fika"

    };

    public String EdoState
            [] = new String[]{
            "Fufure",
            "Funakaye",
            "Fune",
            "Funtua",
            "Gabasawa",
            "Gada",
            "Gagarawa",
            "Gamawa",
            "Ganjuwa",
            "Ganye",
            "Garki",
            "Garko",
            "Garun Mallam",
            "Gashaka",
            "Gassol",
            "Gaya",
            "Gayuk",
            "Gezawa"


    };

    public String EkitiState
            [] = new String[]{
            "Gbako",
            "Gboko",
            "Gbonyin",
            "Geidam",
            "Giade",
            "Giwa",
            "Gokana",
            "Gombe",
            "Gombi",
            "Goronyo",
            "Grie",
            "Gubio",
            "Gudu",
            "Gujba",
            "Gulani",
            "Guma"


    };

    public String EnuguState
            [] = new String[]{

            "Gumel",
            "Gummi",
            "Gurara",
            "Guri",
            "Gusau",
            "Guzamala",
            "Gwadabawa",
            "Gwagwalada",
            "Gwale",
            "Gwandu",
            "Gwaram",
            "Gwarzo",
            "Gwer East",
            "Gwer West",
            "Gwiwa",
            "Gwoza",
            "Hadejia"

    };

    public String FCTState[] = new String[]{
            "Hawul",
            "Hong",
            "Ibadan North",
            "Ibadan North-East",
            "Ibadan North-West",
            "Ibadan South-East"
    };

    public String GombeState
            [] = new String[]{
            "Ibadan South-West",
            "Ibaji",
            "Ibarapa Central",
            "Ibarapa East",
            "Ibarapa North",
            "Ibeju-Lekki",
            "Ibeno",
            "Ibesikpo Asutan",
            "Ibi",
            "Ibiono-Ibom",
            "Idah"    };

    public String ImoState
            [] = new String[]{
            "Idanre",
            "Ideato North",
            "Ideato South",
            "Idemili North",
            "Idemili South",
            "Ido",
            "Ido Osi",
            "Ifako-Ijaiye",
            "Ifedayo",
            "Ifedore",
            "Ifelodun",
            "Ifelodun",
            "Ifo",
            "Igabi",
            "Igalamela Odolu",
            "Igbo Etiti",
            "Igbo Eze North",
            "Igbo Eze South",
            "Igueben",
            "Ihiala",
            "Ihitte/Uboma",
            "Ilaje",
            "Ijebu East",
            "Ijebu North",
            "Ijebu North East",
            "Ijebu Ode",
            "Ijero"


    };

    public String JigawaState
            [] = new String[]{
            "Ijumu",
            "Ika",
            "Ika North East",
            "Ikara",
            "Ika South",
            "Ikeduru",
            "Ikeja",
            "Ikenne",
            "Ikere",
            "Ikole",
            "Ikom",
            "Ikono",
            "Ikorodu",
            "Ikot Abasi",
            "Ikot Ekpene",
            "Ikpoba Okha",
            "Ikwerre",
            "Ikwo",
            "Ikwuano",
            "Ila",
            "Ilejemeje",
            "Ile Oluji/Okeigbo",
            "Ilesa East",
            "Ilesa West",
            "Illela",
            "Ilorin East",
            "Ilorin South"


    };


    public String KadunaState
            [] = new String[]{
            "Ilorin West",
            "Imeko Afon",
            "Ingawa",
            "Ini",
            "Ipokia",
            "Irele",
            "Irepo",
            "Irepodun",
            "Irepodun",
            "Irepodun/Ifelodun",
            "Irewole",
            "Isa",
            "Ise/Orun",
            "Iseyin",
            "Ishielu",
            "Isiala Mbano",
            "Isiala Ngwa North",
            "Isiala Ngwa South",
            "Isin",
            "Isi Uzo",
            "Isokan",
            "Isoko North",
            "Isoko South"


    };

    public String KanoState
            [] = new String[]{

            "Isu",
            "Isuikwuato",
            "Itas/Gadau",
            "Itesiwaju",
            "Itu",
            "Ivo",
            "Iwajowa",
            "Iwo",
            "Izzi",
            "Jaba",
            "Jada",
            "Jahun",
            "Jakusko",
            "Jalingo",
            "Jama'are",
            "Jega",
            "Jema'a",
            "Jere",
            "Jibia",
            "Jos East",
            "Jos North",
            "Jos South",
            "Kabba/Bunu",
            "Kabo",
            "Kachia",
            "Kaduna North",
            "Kaduna South",
            "Kafin Hausa",
            "Kafur",
            "Kaga",
            "Kagarko",
            "Kaiama",
            "Kaita",
            "Kajola",
            "Kajuru",
            "Kala/Balge",
            "Kalgo",
            "Kaltungo",
            "Kanam",
            "Kankara",
            "Kanke",
            "Kankia",
            "Kano Municipal",
            "Karasuwa"

    };

    public String KatsinaState
            [] = new String[]{

            "Karaye",
            "Karim Lamido",
            "Karu",
            "Katagum",
            "Katcha",
            "Katsina",
            "Katsina-Ala",
            "Kaura",
            "Kaura Namoda",
            "Kauru",
            "Kazaure",
            "Keana",
            "Kebbe",
            "Keffi",
            "Khana",
            "Kibiya",
            "Kirfi",
            "Kiri Kasama",
            "Kiru",
            "Kiyawa",
            "Kogi",
            "Koko/Besse",
            "Kokona",
            "Kolokuma/Opokuma",
            "Konduga",
            "Konshisha",
            "Kontagora",
            "Kosofe",
            "Kaugama",
            "Kubau",
            "Kudan",
            "Kuje",
            "Kukawa",
            "Kumbotso"

    };

    public String KebbiState
            [] = new String[]{
            "Kumi",
            "Kunchi",
            "Kura",
            "Kurfi",
            "Kusada",
            "Kwali",
            "Kwande",
            "Kwami",
            "Kware",
            "Kwaya Kusar",
            "Lafia",
            "Lagelu",
            "Lagos Island",
            "Lagos Mainland",
            "Langtang South",
            "Langtang North",
            "Lapai",
            "Lamurde",
            "Lau",
            "Lavun",
            "Lere"


    };

    public String KogiState
            [] = new String[]{
            "Logo",
            "Lokoja",
            "Machina",
            "Madagali",
            "Madobi",
            "Mafa",
            "Magama",
            "Magumeri",
            "Mai'Adua",
            "Maiduguri",
            "Maigatari",
            "Maiha",
            "Maiyama",
            "Makarfi",
            "Makoda",
            "Malam Madori",
            "Malumfashi",
            "Mangu",
            "Mani",
            "Maradun",
            "Mariga"


    };

    public String KwaraState
            [] = new String[]{
            "Makurdi",
            "Marte",
            "Maru",
            "Mashegu",
            "Mashi",
            "Matazu",
            "Mayo Belwa",
            "Mbaitoli",
            "Mbo",
            "Michika",
            "Miga",
            "Mikang",
            "Minjibir",
            "Misau",
            "Moba",
            "Mobbar"


    };

    public String LagosState
            [] = new String[]{
            "Mubi North",
            "Mubi South",
            "Mokwa",
            "Monguno",
            "Mopa Muro",
            "Moro",
            "Moya",
            "Mkpat-Enin",
            "Municipal Area Council",
            "Musawa",
            "Mushin",
            "Nafada",
            "Nangere",
            "Nasarawa",
            "Nasarawa",
            "Nasarawa Egon",
            "Ndokwa East",
            "Ndokwa West",
            "Nembe",
            "Ngala"


    };

    public String NasarawaState
            [] = new String[]{

            "Nganzai",
            "Ngaski",
            "Ngor Okpala",
            "Nguru",
            "Ningi",
            "Njaba",
            "Njikoka",
            "Nkanu East",
            "Nkanu West",
            "Nkwerre",
            "Nnewi North",
            "Nnewi South",
            "Nsit-Atai"

    };

    public String NigerState
            [] = new String[]{
            "Nsit-Ibom",
            "Nsit-Ubium",
            "Nsukka",
            "Numan",
            "Nwangele",
            "Obafemi Owode",
            "Obanliku",
            "Obi",
            "Obi",
            "Obi Ngwa",
            "Obio/Akpor",
            "Obokun",
            "Obot Akara",
            "Obowo",
            "Obubra",
            "Obudu",
            "Odeda",
            "Odigbo",
            "Odogbolu",
            "Odo Otin",
            "Odukpani",
            "Offa",
            "Ofu",
            "Ogba/Egbema/Ndoni",
            "Ogbadibo"

    };

    public String OgunState
            [] = new String[]{
            "Ogbaru",
            "Ogbia",
            "Ogbomosho North",
            "Ogbomosho South",
            "Ogu/Bolo",
            "Ogoja",
            "Ogo Oluwa",
            "Ogori/Magongo",
            "Ogun Waterside",
            "Oguta",
            "Ohafia",
            "Ohaji/Egbema",
            "Ohaozara",
            "Ohaukwu",
            "Ohimini",
            "Orhionmwon",
            "Oji River",
            "Ojo",
            "Oju",
            "Okehi"


    };

    public String OndoState
            [] = new String[]{
            "Okene",
            "Oke Ero",
            "Okigwe",
            "Okitipupa",
            "Okobo",
            "Okpe",
            "Okrika",
            "Olamaboro",
            "Ola Oluwa",
            "Olorunda",
            "Olorunsogo",
            "Oluyole",
            "Omala",
            "Omuma",
            "Ona Ara",
            "Ondo East",
            "Ondo West",
            "Onicha"


    };

    public String OsunState
            [] = new String[]{
            "Onitsha North",
            "Onitsha South",
            "Onna",
            "Okpokwu",
            "Opobo/Nkoro",
            "Oredo",
            "Orelope",
            "Oriade",
            "Ori Ire",
            "Orlu",
            "Orolu",
            "Oron",
            "Orsu",
            "Oru East",
            "Oruk Anam",
            "Orumba North",
            "Orumba South",
            "Oru West",
            "Ose",
            "Oshimili North",
            "Oshimili South",
            "Oshodi-Isolo",
            "Osisioma",
            "Osogbo",
            "Oturkpo",
            "Ovia North-East",
            "Ovia South-West",
            "Owan East",
            "Owan West",
            "Owerri Municipal"


    };

    public String OyoState
            [] = new String[]{
            "Owerri North",
            "Owerri West",
            "Owo",
            "Oye",
            "Oyi",
            "Oyigbo",
            "Oyo",
            "Oyo East",
            "Oyun",
            "Paikoro",
            "Pankshin",
            "Patani",
            "Pategi",
            "Port Harcourt",
            "Potiskum",
            "Qua'an Pan",
            "Rabah",
            "Rafi",
            "Rano",
            "Remo North",
            "Rijau",
            "Rimi",
            "Rimin Gado",
            "Ringim",
            "Riyom",
            "Rogo",
            "Roni",
            "Sabon Birni",
            "Sabon Gari",
            "Sabuwa",
            "Safana",
            "Sagbama",
            "Sakaba"


    };
    public String PlateauState
            [] = new String[]{
            "Saki East",
            "Saki West",
            "Sandamu",
            "Sanga",
            "Sapele",
            "Sardauna",
            "Shagamu",
            "Shagari",
            "Shanga",
            "Shani",
            "Shanono",
            "Shelleng",
            "Shendam",
            "Shinkafi",
            "Shira",
            "Shiroro",
            "Shongom"


    };
    public String RiversState
            [] = new String[]{
            "Shomolu",
            "Silame",
            "Soba",
            "Sokoto North",
            "Sokoto South",
            "Song",
            "Southern Ijaw",
            "Suleja",
            "Sule Tankarkar",
            "Sumaila",
            "Suru",
            "Surulere",
            "Surulere",
            "Tafa",
            "Tafawa Balewa",
            "Tai",
            "Takai",
            "Takum",
            "Talata Mafara",
            "Tambuwal",
            "Tangaza",
            "Tarauni",
            "Tarka"


    };
    public String SokotoState
            [] = new String[]{

            "Tarmuwa",
            "Taura",
            "Toungo",
            "Tofa",
            "Toro",
            "Toto",
            "Chafe",
            "Tsanyawa",
            "Tudun Wada",
            "Tureta",
            "Udenu",
            "Udi",
            "Udu",
            "Udung-Uko",
            "Ughelli North",
            "Ughelli South",
            "Ugwunagbo",
            "Uhunmwonde",
            "Ukanafun",
            "Ukum",
            "Ukwa East",
            "Ukwa West",
            "Ukwuani"

    };
    public String TarabaState
            [] = new String[]{

            "Umuahia North",
            "Umuahia South",
            "Umu Nneochi",
            "Ungogo",
            "Unuimo",
            "Uruan",
            "Urue-Offong/Oruko",
            "Ushongo",
            "Ussa",
            "Uvwie",
            "Uyo",
            "Uzo-Uwani",
            "Vandeikya",
            "Wamako",
            "Wamba",
            "Warawa"

    };
    public String YobeState
            [] = new String[]{

            "Warji",
            "Warri North",
            "Warri South",
            "Warri South West",
            "Wasagu/Danko",
            "Wase",
            "Wudil",
            "Wukari",
            "Wurno",
            "Wushishi",
            "Yabo",
            "Yagba East",
            "Yagba West",
            "Yakuur",
            "Yala",
            "Yamaltu/Deba",
            "Yankwashi"

    };
    public String ZamfaraState[] = new String[]{

            "Yauri",
            "Yenagoa",
            "Yola North",
            "Yola South",
            "Yorro",
            "Yunusari",
            "Yusufari",
            "Zaki",
            "Zango",
            "Zangon Kataf",
            "Zaria",
            "Zing",
            "Zurmi",
            "Zuru"

    };

    public HashMap<String,String[]> city_hash = new HashMap<>();
    public HashMap<String, String[]> getCity_hash() {
        city_hash.put("Abia State",this.AbiaState);
        city_hash.put("Adamawa State",this.AdamawaState);
        city_hash.put("Akwa Ibom State",this.AkwaIbomState);
        city_hash.put("Anambra State",this.AnambraState);
        city_hash.put("Bauchi State",this.BauchiState);
        city_hash.put("Bayelsa State",this.BayelsaState);
        city_hash.put("Benue State",this.BenueState);
        city_hash.put("Borno State",this.BornoState);
        city_hash.put("Cross River State",this.CrossRiverState);
        city_hash.put("Delta State",this.DeltaState);
        city_hash.put("Ebonyi State",this.EbonyiState);
        city_hash.put("Edo State",this.EdoState);
        city_hash.put("Ekiti State",this.EkitiState);
        city_hash.put("Enugu State",this.EnuguState);
        city_hash.put("FCT",this.FCTState);
        city_hash.put("Gombe State",this.GombeState);
        city_hash.put("Imo State",this.ImoState);
        city_hash.put("Jigawa State",this.JigawaState);
        city_hash.put("Kaduna State",this.KadunaState);
        city_hash.put("Kano State",this.KanoState);
        city_hash.put("Katsina State",this.KatsinaState);
        city_hash.put("Kebbi State",this.KebbiState);
        city_hash.put("Kogi State",this.KogiState);
        city_hash.put("Kwara State",this.KwaraState);
        city_hash.put("Lagos State",this.LagosState);
        city_hash.put("Nasarawa State",this.NasarawaState);
        city_hash.put("Niger State",this.NigerState);
        city_hash.put("Ogun State",this.OgunState);
        city_hash.put("Ondo State",this.OndoState);
        city_hash.put("Osun State",this.OsunState);
        city_hash.put("Oyo State",this.OyoState);
        city_hash.put("Plateau State",this.PlateauState);
        city_hash.put("Rivers State",this.RiversState);
        city_hash.put("Sokoto State",this.SokotoState);
        city_hash.put("Taraba State",this.TarabaState);
        city_hash.put("Yobe State",this.YobeState);
        city_hash.put("Zamfara State",this.ZamfaraState);


        return city_hash;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
}
