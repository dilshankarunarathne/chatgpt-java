package tech.altier;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "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";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws JSONException, IOException {
        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine();
            if (input.equals("exit")) break;
            System.out.println("Bot: " + chat(input));
        }
    }

    /**
     * ChatGPT API caller function
     * This method will take a string as input and return a string as output
     * @return response
     */
    public static String chat(String input) throws IOException, JSONException {
        String URL = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(URL).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + API_KEY);

        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", input);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream()))
                .lines()
                .reduce((a, b) -> a + b)
                .get();

        return new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
    }
}
