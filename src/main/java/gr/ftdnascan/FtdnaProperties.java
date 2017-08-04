package gr.ftdnascan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class FtdnaProperties {
	
	private Properties props = new Properties();
	
	public FtdnaProperties() {
		initProperties();
	}
	
	private void initProperties() {
		
		try ( final InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties") ) {
			System.out.println( input.toString() );
			
		    // load a properties file
			props.load(input);

		    // print all properties
		    Set<String> propNames = props.stringPropertyNames();
		    for(String name: propNames) {
		    	System.out.println(name);
		    }
		    
		} catch (IOException | NullPointerException ex ) {
			System.out.println( "ERROR: " + ex.getMessage() );
		}		
	}
	
	public String getProperty( String name ) {
		return props.getProperty( name );
	}

	public void printProperties() {
		
		try ( final InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties") ) {
			System.out.println( input.toString() );
			
			Properties prop = new Properties();
			
		    // load a properties file
			prop.load(input);

		    // get the property value and print it out
		    System.out.println(prop.getProperty("database"));
		    System.out.println(prop.getProperty("dbuser"));
		    System.out.println(prop.getProperty("dbpassword"));
		} catch (IOException | NullPointerException ex ) {
			System.out.println( "ERROR: " + ex.getMessage() );
		}
	}
}
