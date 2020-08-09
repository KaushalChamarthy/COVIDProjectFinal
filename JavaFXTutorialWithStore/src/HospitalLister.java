import java.net.*;
import java.io.*;
import java.util.ArrayList;


class URLConnectionReader {
    // This tool simply returns a page's text as a String
    public String pageTextRequester(String urlToConnect) throws Exception {
        URL obj = new URL(urlToConnect);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine=in.readLine()) !=null){
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}

class lattitudeLongitudeLocatorObject{
    public double[] positionRequester(String address) throws Exception {
        URLConnectionReader un = new URLConnectionReader();
        String[] addressWordArray = address.split(" ");
        String urlCompatibleAddress = "";
        for (String word:addressWordArray) {
            urlCompatibleAddress+=(word);
            urlCompatibleAddress+=("%20");

        }

        String urlToVisit = "https://api.tomtom.com/search/2/geocode/"+urlCompatibleAddress+".json?key=QiGAL9HZTbrizZEp087HHcDWzcqk8TMv";
        String page = un.pageTextRequester(urlToVisit);

        if(page.contains("error"))
        {
            try {
                Thread.sleep(1000);
                page = un.pageTextRequester(urlToVisit);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        int lattitudeIndex = page.indexOf("lat") +5;
        int lattitudeIndexEnd = page.indexOf("lon")-2;
        String lattitude = page.substring(lattitudeIndex, lattitudeIndexEnd);

        int longitudeIndex = page.indexOf("lon") +5;
        int longitudeIndexEnd = page.indexOf("viewport") -3;

        String longitude = page.substring(longitudeIndex, longitudeIndexEnd);


        double lon = Double.parseDouble(longitude);
        double lat = Double.parseDouble(lattitude);

        return new double[]{lat, lon};



    }
}

class CoordinateProximityHospitalLocatorObject {

    public ArrayList<ArrayList<String>> nearestHospitalsRequester(double lattitude, double longitude, int numberOfResults) throws Exception {
        ArrayList<String> urgentCareNames = new ArrayList<String>();
        ArrayList<String> freeFormAddress = new ArrayList<String>();
        ArrayList<String> phoneNumbers = new ArrayList<String>();
        ArrayList<String> distanceAway = new ArrayList<String>();

        URLConnectionReader un = new URLConnectionReader();
        String urlToVisit = "https://api.tomtom.com/search/2/categorySearch/.json?limit="+String.valueOf(numberOfResults)+"&lat=" +String.valueOf(lattitude) + "&lon=" + String.valueOf(longitude) + "&radius=48000&categorySet=7391&key=QiGAL9HZTbrizZEp087HHcDWzcqk8TMv";
        System.out.println(urlToVisit);
        String page = un.pageTextRequester(urlToVisit);

        String splitTerm = "freeformAddress";
        String pageN = page;
        String[] locationList = pageN.split(splitTerm);
        int counter = 0;
        for (String result : locationList) {
            if (counter == 0) {
                counter++;
            } else {
//              getting address
                result = result.substring(3);
                int end_of_address = result.indexOf("\"");
                String address = result.substring(0, end_of_address);

                freeFormAddress.add(address);

            }
        }

        splitTerm = "\\{\"name\"";
        pageN = page;
        String[] nameList = pageN.split(splitTerm);
        counter = 0;
        for (String nameResult : nameList) {
            if (counter == 0) {
                counter++;
            } else {
//              getting name
                String result = nameResult.substring(2);
                int end_of_name = result.indexOf("\"");
                String name = result.substring(0, end_of_name);
                urgentCareNames.add(name);

            }
        }

        splitTerm = "phone";
        pageN = page;
        String[] phoneList = pageN.split(splitTerm);
        counter = 0;
        for (String phoneResult : phoneList) {
            if (counter == 0) {
                counter++;
            } else {
//              getting phone
                String result = phoneResult.substring(3);
                int end_of_phone = result.indexOf("\"");
                String phone = result.substring(0, end_of_phone);

                phoneNumbers.add(phone);
            }
        }

        splitTerm = "\"dist\"";
        pageN = page;
        String[] distList = pageN.split(splitTerm);
        counter = 0;
        for (String distResult : distList) {
            if (counter == 0) {
                counter++;
            } else {
//              getting distance
                String result = distResult.substring(1);
                int end_of_distance = result.indexOf(",\"info\"") -1 ;
                String distance = result.substring(0, end_of_distance);
                distanceAway.add(distance);
            }
        }


        ArrayList<ArrayList<String>> hospitalsInfo = new ArrayList<ArrayList<String>>();
        hospitalsInfo.add(freeFormAddress);
        hospitalsInfo.add(urgentCareNames);
        hospitalsInfo.add(phoneNumbers);
        hospitalsInfo.add(distanceAway);

        return hospitalsInfo;

    }
}

class NearestHospitalsLocator {
    public ArrayList<ArrayList<String>> nearestHospitalsToYou(String address, int numberOfResults) throws Exception {
        lattitudeLongitudeLocatorObject latlon = new lattitudeLongitudeLocatorObject();
        double[] lattilongi = latlon.positionRequester(address);
        double lattitude = lattilongi[0];
        double longitude = lattilongi[1];
        CoordinateProximityHospitalLocatorObject bob = new CoordinateProximityHospitalLocatorObject();
        ArrayList<ArrayList<String>> twoD = bob.nearestHospitalsRequester(lattitude, longitude, numberOfResults);

        return twoD;

    }
}
public class HospitalLister{

    public ArrayList<ArrayList<String>> optimalHospitalLocator(double optimalDistance, String address, int numberOfResults) throws Exception {

        NearestHospitalsLocator locat = new NearestHospitalsLocator();
        ArrayList<ArrayList<String>> twoDimensionalList = locat.nearestHospitalsToYou(address, numberOfResults);



        ArrayList<String> names = twoDimensionalList.get(1);
        ArrayList<String> addresses = twoDimensionalList.get(0);
        ArrayList<String> numbers = twoDimensionalList.get(2);



        ArrayList<String> distancesInMeters = twoDimensionalList.get(3);


        ArrayList<Double> distancesAwayFromOptimalDistance =new ArrayList<Double>();


        for (String stringDistanceInMeters:distancesInMeters) {
            double distanceInMeters = Double.parseDouble(stringDistanceInMeters);
            double distanceAwayFromOptimalDistance = distanceInMeters - optimalDistance;
            if (distanceAwayFromOptimalDistance < 0){distanceAwayFromOptimalDistance*=-1;}

            distancesAwayFromOptimalDistance.add(distanceAwayFromOptimalDistance);



        }
        ArrayList<Double> copyDistancesAwayFromOptimalDistance = (ArrayList<Double>) distancesAwayFromOptimalDistance.clone();
        copyDistancesAwayFromOptimalDistance.sort(null);

        ArrayList<String> finalNames = new ArrayList<String>();
        ArrayList<String> finalAddress = new ArrayList<String>();
        ArrayList<String> finalPhoneNumbers = new ArrayList<String>();
        ArrayList<Double> finalDistanceAway = new ArrayList<Double>();

        for (double optimalDistanceAway:copyDistancesAwayFromOptimalDistance) {
            int sortIndex = distancesAwayFromOptimalDistance.indexOf(optimalDistanceAway);
            finalNames.add(names.get(sortIndex));
            finalAddress.add(addresses.get(sortIndex));
            finalPhoneNumbers.add(numbers.get(sortIndex));

        }
        ArrayList<ArrayList<String>> hospitalsSortedByOptimalDistanceInfo = new ArrayList<ArrayList<String>>();

        hospitalsSortedByOptimalDistanceInfo.add(finalNames);
        hospitalsSortedByOptimalDistanceInfo.add(finalAddress);
        hospitalsSortedByOptimalDistanceInfo.add(finalPhoneNumbers);

        return hospitalsSortedByOptimalDistanceInfo;
    }
}
