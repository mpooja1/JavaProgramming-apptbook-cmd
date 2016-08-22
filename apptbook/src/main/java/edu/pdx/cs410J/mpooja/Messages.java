package edu.pdx.cs410J.mpooja;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * Used to get the count of mappings
     * @param count total number of appointmentbooks
     * @return string message of count of mappings
     */
    public static String getMappingCount( int count )
    {
        return String.format( "Server contains %d key/value pairs", count );
    }

    /**
     * Used to get particular key value pair
     * @param owner ownername
     * @param book appointmentbook
     * @return string message of owner and book key value pairs
     */

    public static String formatKeyValuePair( String owner, String book )
    {
        return String.format("  %s -> %s", owner, book);
    }

    /**
     * Used to print if any argument is missing
     * @param parameterName parameter name that is missing
     * @return string message if any parameter is missing
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * Used to display mappings
     * @param owner ownername
     * @param book appointmentbook
     * @return string message for owner-book mapping
     */
    public static String mappedKeyValue( String owner, String book )
    {
        return String.format( "Mapped %s to %s", owner, book );
    }

    /**
     * Used to delete all mappings
     * @return string message if all the mappings are deleted
     */
    public static String allMappingsDeleted() {
        return "All mappings have been deleted";
    }
}
