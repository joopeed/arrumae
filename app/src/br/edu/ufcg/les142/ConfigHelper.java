package br.edu.ufcg.les142;

import com.parse.ConfigCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;

/**
 * Created by Rodrigo on 27/11/2014.
 */

public class ConfigHelper {
    private ParseConfig config;
    private long configLastFetchedTime;

    public void fetchConfigIfNeeded() {
        final long configRefreshInterval = 60 * 60; // 1 hour

        if (config == null ||
                System.currentTimeMillis() - configLastFetchedTime > configRefreshInterval) {
            // Set the config to current, just to load the cache
            config = ParseConfig.getCurrentConfig();

            // Set the current time, to flag that the operation started and prevent double fetch
            ParseConfig.getInBackground(new ConfigCallback() {
                @Override
                public void done(ParseConfig parseConfig, ParseException e) {
                    if (e == null) {
                        config = parseConfig;
                        configLastFetchedTime = System.currentTimeMillis();
                    } else {
                        configLastFetchedTime = 0;
                    }
                }
            });
        }
    }
}