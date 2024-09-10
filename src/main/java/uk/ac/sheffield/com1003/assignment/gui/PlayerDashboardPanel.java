package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * SKELETON IMPLEMENTATION
 */

public class PlayerDashboardPanel extends AbstractPlayerDashboardPanel {

    // Constructor
    public PlayerDashboardPanel(AbstractPlayerCatalog playerCatalog) {
        super(playerCatalog);
    }

    @Override
    public void populatePlayerDetailsComboBoxes() {
        // clear combo boxes
        comboPlayerNames.removeAllItems();
        comboPositions.removeAllItems();
        comboNations.removeAllItems();
        comboTeams.removeAllItems();

        // create sets of unique values
        Set<String> playerNamesSet = new HashSet<>();
        Set<String> positionsSet = new HashSet<>();
        Set<String> nationsSet = new HashSet<>();
        Set<String> teamsSet = new HashSet<>();


        // add unique values to sets
        for (PlayerEntry entry : filteredPlayerEntriesList) {
            playerNamesSet.add(entry.getPlayerName());
            positionsSet.add(entry.getNation());
            nationsSet.add(entry.getPosition());
            teamsSet.add(entry.getTeam());
        }


        // add elements of sets to combo boxes
        for (String playerName : playerNamesSet) {
            comboPlayerNames.addItem(playerName);
        }
        for (String nation : nationsSet) {
            comboNations.addItem(nation);
        }
        for (String position : positionsSet) {
            comboPositions.addItem(position);
        }
        for (String team : teamsSet) {
            comboTeams.addItem(team);
        }

        updateStatistics();
        updatePlayerCatalogDetailsBox();
    }

    /**
     * addListeners method - adds relevant actionListeners to the GUI components
     */
    @SuppressWarnings("unused")
    @Override
    public void addListeners() {
        // add action listener for buttonAddFilter
        buttonAddFilter.addActionListener(e -> {
            addFilter();// call the addFilter method when buttonAddFilter is clicked
            executeQuery();
            populatePlayerDetailsComboBoxes();
        });

        // add action listener for buttonClearFilters
        buttonClearFilters.addActionListener(e -> {
            clearFilters(); // call the clearFilters method when buttonClearFilters is clicked
        });

        // add item listener for comboLeagueTypes
        comboLeagueTypes.addItemListener(e -> {
            selectedLeagueType = League.fromName(Objects.requireNonNull(comboLeagueTypes.getSelectedItem()).toString()); // get the selected item from comboLeagueTypes
            executeQuery(); // call the executeQuery method when comboLeagueTypes selection is changed
            populatePlayerDetailsComboBoxes();
        });

        // add item listener for comboPlayerNames
        comboPlayerNames.addItemListener(e -> {
            selectedPlayerName = Objects.requireNonNull(comboPlayerNames.getSelectedItem()).toString(); // get the selected item from comboPlayerNames
            addFilter();
            executeQuery(); // call the executeQuery method when comboPlayerNames selection is changed
            populatePlayerDetailsComboBoxes();
        });

        // add item listener for comboNations
        comboNations.addItemListener(e -> {
            selectedPlayerNation = Objects.requireNonNull(comboNations.getSelectedItem()).toString(); // get the selected item from comboNations
            addFilter();
            executeQuery(); // call the executeQuery method when comboNations selection is changed
            populatePlayerDetailsComboBoxes();
        });

        // add item listener for comboPositions
        comboPositions.addItemListener(e -> {
            selectedPlayerPosition = Objects.requireNonNull(comboPositions.getSelectedItem()).toString(); // get the selected item from comboPositions
            addFilter();
            executeQuery(); // call the executeQuery method when comboPositions selection is changed
            populatePlayerDetailsComboBoxes();
        });

        // add item listener for comboTeams
        comboTeams.addItemListener(e -> {
            selectedTeam = Objects.requireNonNull(comboTeams.getSelectedItem()).toString(); // get the selected item from comboTeams
            addFilter();
            executeQuery(); // call the executeQuery method when comboTeams selection is changed
            populatePlayerDetailsComboBoxes();

        });

        // add item listener for comboRadarChartCategories
        comboRadarChartCategories.addItemListener(e -> {
            updateRadarChart(); // call the updateRadarChart method when comboRadarChartCategories selection is changed
        });
    }

    /**
     * clearFilters method - clears all filters from the subQueryList ArrayList and updates
     * the relevant GUI components
     */
    @Override
    public void clearFilters() {
        subQueriesTextArea.setText(null); // clear the subQueriesTextArea
        subQueryList.clear(); // clear all filters from the subQueryList ArrayList
        selectedLeagueType = League.ALL; // set the selectedLeagueType to ALL
        comboLeagueTypes.setSelectedItem(League.ALL.getName()); // set the selected item of comboLeagueTypes to ALL
        comboPlayerNames.setSelectedItem(null); // set the selected item of comboPlayerNames to null
        selectedPlayerName = null; // set the selectedPlayerName to null
        comboNations.setSelectedItem(null); // set the selected item of comboNations to null
        selectedPlayerNation = null; // set the selectedPlayerNation to null
        comboPositions.setSelectedItem(null); // set the selected item of comboPositions to null
        selectedPlayerPosition = null; // set the selectedPlayerPosition to null
        comboTeams.setSelectedItem(null); // set the selected item of comboTeams to null
        selectedTeam = null; // set the selectedTeam to null
        executeQuery(); // re-execute the query to show all player entries
        populatePlayerDetailsComboBoxes(); // update the player details combo boxes with the new data
    }

    @Override
    public void updateRadarChart() {
        // TODO implement

    }

    /**
     * updateStats method - updates the table with statistics after any changes which may
     * affect the JTable which holds the statistics
     */
    @Override
    public void updateStatistics() {
        statisticsTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        int fieldWidth = 50;
        String[] labels = {"Minimum", "Maximum", "Average"};

        // Print the column labels
        statisticsTextArea.setText(String.format("%-" + fieldWidth + "s", ""));
        for (PlayerProperty playerProperty : PlayerProperty.values()) {
            statisticsTextArea.append(String.format("%-" + fieldWidth + "s", playerProperty.getName()));
        }
        statisticsTextArea.append("\n");

        // Print the data rows
        for (String label : labels) {
            statisticsTextArea.append(String.format("%-" + fieldWidth + "s", label));
            for (PlayerProperty playerProperty : PlayerProperty.values()) {
                double value;
                if (label.equals("Minimum")) {
                    value = playerCatalog.getMinimumValue(playerProperty, filteredPlayerEntriesList);
                } else if (label.equals("Maximum")) {
                    value = playerCatalog.getMaximumValue(playerProperty, filteredPlayerEntriesList);
                } else {
                    value = playerCatalog.getMeanAverageValue(playerProperty, filteredPlayerEntriesList);
                }
                statisticsTextArea.append(String.format("%-" + fieldWidth + ".2f", value));
            }
            statisticsTextArea.append("\n");
        }
    }


    /**
     * updatePlayerCatalogDetailsBox method - updates the list of players when changes are made
     */
    @Override
    public void updatePlayerCatalogDetailsBox() {
        filteredPlayerEntriesTextArea.setText(""); // clear the filteredPlayerEntriesTextArea
        int fieldWidth = 60; // set the field width to 60
        filteredPlayerEntriesTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)); // set the font of the text area to monospaced

        filteredPlayerEntriesTextArea.append("League Type" + " ".repeat(fieldWidth - "League Type".length()));
        filteredPlayerEntriesTextArea.append("ID" + " ".repeat(fieldWidth - "ID".length()));

        // FilteredPlayerEntriesTextAreaComboBoxes
        for (PlayerDetail playerDetail : PlayerDetail.values()) {
            filteredPlayerEntriesTextArea.append(playerDetail.getName() + " ".repeat(fieldWidth - playerDetail.getName().length()));
        }

        for (PlayerProperty playerProperty : PlayerProperty.values()) {
            filteredPlayerEntriesTextArea.append(playerProperty.getName() + " ".repeat(fieldWidth - playerProperty.getName().length()));
        }

        for (PlayerEntry playerEntry : filteredPlayerEntriesList) {
            filteredPlayerEntriesTextArea.append("\n");
            filteredPlayerEntriesTextArea.append(playerEntry.getLeagueType().getName() + " ".repeat(fieldWidth - playerEntry.getLeagueType().getName().length()));
            filteredPlayerEntriesTextArea.append(playerEntry.getId() + " ".repeat(fieldWidth - String.valueOf(playerEntry.getId()).length()));

            for (PlayerDetail playerDetail : PlayerDetail.values()) {
                filteredPlayerEntriesTextArea.append(playerEntry.getDetail(playerDetail) + " ".repeat(fieldWidth - playerEntry.getDetail(playerDetail).length()));
            }


            for (PlayerProperty playerProperty : PlayerProperty.values()) {
                filteredPlayerEntriesTextArea.append(playerEntry.getProperty(playerProperty) + " ".repeat(fieldWidth - String.valueOf(playerEntry.getProperty(playerProperty)).length()));
            }

        }
    }


    /**
     * executeQuery method - applies chosen query to the relevant list
     */
    @Override
    public void executeQuery() {
        Query query = new Query(subQueryList, selectedLeagueType);
        filteredPlayerEntriesList = query.executeQuery(playerCatalog);
        System.out.println(filteredPlayerEntriesList.size());

    }

    /**
     * Adds a new sub-query to the list of sub queries.
     * Validates the value entered is a valid integer and greater than 0.
     *
     * @throws IllegalArgumentException if the value is not a valid integer or is less than or equal to 0.
     */
    @Override
    public void addFilter() throws IllegalArgumentException {
        int valueEntered;
        try {
            valueEntered = Integer.parseInt(value.getText());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Value must be a valid integer");
        }
        if (valueEntered <= 0) {
            throw new IllegalArgumentException("Value must be greater than 0");
        }
        String operators = (String) comboOperators.getSelectedItem();
        PlayerProperty playerProperties = PlayerProperty.fromPropertyName((String) comboQueryProperties.getSelectedItem());
        SubQuery subQuery = new SubQuery(playerProperties, operators, valueEntered);
        subQueriesTextArea.append(subQuery.toString());
        subQueryList.add(subQuery);

    }


    @Override
    public boolean isMinCheckBoxSelected() {

        return minCheckBox.isSelected();
    }

    @Override
    public boolean isMaxCheckBoxSelected() {
        return maxCheckBox.isSelected();
    }

    @Override
    public boolean isAverageCheckBoxSelected() {
        return averageCheckBox.isSelected();
    }


}
