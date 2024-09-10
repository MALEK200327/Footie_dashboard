package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.AbstractPlayerCatalog;
import uk.ac.sheffield.com1003.assignment.codeprovided.PlayerEntry;
import uk.ac.sheffield.com1003.assignment.codeprovided.PlayerProperty;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChart;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.RadarAxisValues;

import java.util.*;

/**
 * SKELETON IMPLEMENTATION
 */

public class RadarChart extends AbstractRadarChart {
    public RadarChart(AbstractPlayerCatalog playerCatalog, List<PlayerEntry> filteredPlayerEntriesList,
                      List<PlayerProperty> playerRadarChartProperties) {
        super(playerCatalog, filteredPlayerEntriesList, playerRadarChartProperties);
    }

    @Override
    /**
     * Updates the contents of the radar chart with the given player properties and filtered player entries.
     * @param radarChartPlayerProperties The player properties to display on the radar chart.
     * @param filteredPlayerEntriesList The list of player entries to use for calculating the radar axis values.
     */
    public void updateRadarChartContents(List<PlayerProperty> radarChartPlayerProperties, List<PlayerEntry> filteredPlayerEntriesList) {
        // Set the properties and entries to the class variables
        this.playerRadarChartProperties = radarChartPlayerProperties;
        this.filteredPlayerEntries = filteredPlayerEntriesList;

        // Initialize the radar axes values map
        this.radarAxesValues = new HashMap<>();

        // If there are no player entries, return an empty map
        if (filteredPlayerEntriesList.isEmpty()) {
            return;
        }

        // Calculate the minimum, maximum, and average values for each player property
        for (PlayerProperty playerProperty : radarChartPlayerProperties) {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            double sum = 0;

            // Iterate through each player entry to find the minimum, maximum, and average values
            for (PlayerEntry playerEntry : filteredPlayerEntriesList) {
                double value = playerEntry.getProperty(playerProperty);

                if (value < min) {
                    min = value;
                }

                if (value > max) {
                    max = value;
                }

                sum += value;
            }

            double average = sum / filteredPlayerEntriesList.size();
            RadarAxisValues radarAxisValues = new RadarAxisValues(min, max, average);
            radarAxesValues.put(playerProperty, radarAxisValues);
        }
    }


    /**
     * This method returns the list of PlayerProperty objects that represent the properties to be included in the
     * player radar chart. If no properties have been set, it throws a NoSuchElementException
     */
    @Override
    public List<PlayerProperty> getPlayerRadarChartProperties() throws NoSuchElementException {
        return playerRadarChartProperties;

    }

    /**
     * This method returns a map of PlayerProperty objects to RadarAxisValues objects. Each PlayerProperty object
     * represents a property to be included in the player radar chart, and each RadarAxisValues object represents
     * the minimum, maximum, and average values for that property. If no properties have been set, it throws a
     * NoSuchElementException
     */
    @Override
    public Map<PlayerProperty, RadarAxisValues> getRadarPlotAxesValues() throws NoSuchElementException {
        return radarAxesValues;
    }

    /**
     * This method returns the AbstractPlayerCatalog object that is being used to populate the player radar chart.
     */
    @Override
    public AbstractPlayerCatalog getPlayerCatalog() {
        return playerCatalog;
    }

    /**
     * This method returns the list of PlayerEntry objects that are being used to populate the player radar chart.
     */
    @Override
    public List<PlayerEntry> getFilteredPlayerEntries() {
        return filteredPlayerEntries;
    }

}

