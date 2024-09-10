package uk.ac.sheffield.com1003.assignment;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;

import java.util.*;

/**
 * SKELETON IMPLEMENTATION
 */
public class QueryParser extends AbstractQueryParser {

    // Default implementation to be provided

    /**
     * Parses a list of query tokens and returns a list of Query objects.
     *
     * @param queryTokens a list of query tokens
     * @return a list of Query objects
     * @throws IllegalArgumentException if the query tokens are invalid or incomplete
     */
    @Override
    public List<Query> readQueries(List<String> queryTokens) throws IllegalArgumentException {
        // Check that the input list is not null or empty
        if (queryTokens == null || queryTokens.isEmpty()) {
            throw new IllegalArgumentException("Input query tokens list is null or empty");
        }

        // Initialize an empty list of queries and a League object
        List<Query> queries = new ArrayList<>();
        League league;

        // Initialize a list of SubQueries
        List<SubQuery> subQueries;

        // Iterate through the query tokens
        for (int i = 0; i < queryTokens.size(); i++) {
            String queryToken = queryTokens.get(i);

            // Check if the current token is "select"
            if (queryToken.equals("select")) {
                // Check that there are enough tokens after "select" to determine the league
                if (i + 2 >= queryTokens.size()) {
                    throw new IllegalArgumentException("Invalid query: not enough tokens after 'select'");
                }

                // Initialize a new list of SubQueries
                subQueries = new ArrayList<>();

                // Determine the league based on the next few tokens
                if (queryTokens.get(i + 2).equals("or")) {
                    league = League.ALL;
                } else if (queryTokens.get(i + 1).equals("liga")) {
                    league = League.LIGA;
                } else if (queryTokens.get(i + 1).equals("epl")) {
                    league = League.EPL;
                } else {
                    // If the league is invalid, throw an exception
                    throw new IllegalArgumentException("Invalid query: invalid league");
                }

                // Look for the "where" token to start the first SubQuery
                int valueOfWhere = -1;
                for (int j = i + 1; j < queryTokens.size(); j++) {
                    if (queryTokens.get(j).equals("where")) {
                        valueOfWhere = j;
                        if (valueOfWhere + 3 >= queryTokens.size()) {
                            throw new IllegalArgumentException("Invalid query: not enough tokens after 'where'");
                        }
                        try {
                            subQueries.add(new SubQuery(PlayerProperty.fromName(queryTokens.get(valueOfWhere + 1)), queryTokens.get(valueOfWhere + 2), Double.parseDouble(queryTokens.get(valueOfWhere + 3))));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid query: invalid numeric value in SubQuery");
                        }
                        break;
                    }
                }

                // Create a SubQuery for each "and" token encountered
                for (int j = valueOfWhere; j < queryTokens.size(); j++) {
                    if (queryTokens.get(j).equals("select")) {
                        break;
                    }
                    if (queryTokens.get(j).equals("and")) {
                        if (j + 3 >= queryTokens.size()) {
                            throw new IllegalArgumentException("Invalid query: not enough tokens after 'and'");
                        }
                        try {
                            subQueries.add(new SubQuery(PlayerProperty.fromName(queryTokens.get(j + 1)), queryTokens.get(j + 2), Double.parseDouble(queryTokens.get(j + 3))));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid query: invalid numeric value in SubQuery");
                        }
                    }
                }

                // Create a new Query object and add it to the list of queries
                queries.add(new Query(subQueries, league));
            }
        }

        // Return the list of queries
        return queries;
    }


}





