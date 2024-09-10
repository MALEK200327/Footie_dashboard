package uk.ac.sheffield.com1003.assignment;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * SKELETON IMPLEMENTATION
 */
public class PlayerCatalog extends AbstractPlayerCatalog {
    /**
     * Constructor
     */
    public PlayerCatalog(String eplFilename, String ligaFilename) {
        super(eplFilename, ligaFilename);
    }

    @Override
    public PlayerPropertyMap parsePlayerEntryLine(String line) throws IllegalArgumentException {
        String[] tokens = line.split(",");

        // check that the line has the correct number of tokens
        if (tokens.length != PlayerDetail.values().length + PlayerProperty.values().length) {
            throw new IllegalArgumentException("Invalid line: " + line);
        }

        // create a new property map
        PlayerPropertyMap propertyMap = new PlayerPropertyMap();

        // add player details to the map
        propertyMap.putDetail(PlayerDetail.PLAYER, tokens[0]);
        propertyMap.putDetail(PlayerDetail.POSITION, tokens[1]);
        propertyMap.putDetail(PlayerDetail.NATION, tokens[2]);
        propertyMap.putDetail(PlayerDetail.TEAM, tokens[3]);

        // add player properties to the map
        for (int i = 0; i < PlayerProperty.values().length; i++) {
            double value = Double.parseDouble(tokens[4 + i]);
            propertyMap.put(PlayerProperty.values()[i], value);
        }

        return propertyMap;
    }

    /* This method updates the player catalog for the "ALL" league by combining
       the player entries from the "EPL" and "LIGA" leagues. It does so by
       creating a new list of all player entries and then adding it to the
       playerEntriesMap with the key "ALL". This method assumes that the player
       entries for each league have already been added to the playerEntriesMap */
    @Override
    public void updatePlayerCatalog() {
        List<PlayerEntry> allPlayerEntries = new ArrayList<>();
        allPlayerEntries.addAll(playerEntriesMap.get(League.EPL));
        allPlayerEntries.addAll(playerEntriesMap.get(League.LIGA));
        playerEntriesMap.put(League.ALL, allPlayerEntries);
    }

    // This method calculates the minimum value of a given player property among all the players in a given player entry list.
    @Override
    public double getMinimumValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList) throws NoSuchElementException {
        return playerEntryList.stream().mapToDouble(w -> w.getProperty(playerProperty)).min().getAsDouble();
    }

    // This method calculates the maximum value of a given player property among all the players in a given player entry list.
    @Override
    public double getMaximumValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList) throws NoSuchElementException {
        return playerEntryList.stream().mapToDouble(w -> w.getProperty(playerProperty)).max().getAsDouble();
    }

    // This method calculates the average value of a given player property among all the players in a given player entry list.
    @Override
    public double getMeanAverageValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList) throws NoSuchElementException {
        return playerEntryList.stream().mapToDouble(w -> w.getProperty(playerProperty)).average().getAsDouble();
    }

    // this method gets the first 5 player entries for a given league
    @Override
    public List<PlayerEntry> getFirstFivePlayerEntries(League type) {
        return playerEntriesMap.get(type).subList(0, 5);
    }


}
