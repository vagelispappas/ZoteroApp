package com.example.zoteroapp.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.example.zoteroapp.model.Item;



public class XMLSingleItemParser {

	static final String ns = null;



	public  final String TAG = getClass().getSimpleName();

	public XMLSingleItemParser() {
		// TODO Auto-generated constructor stub
	}

	public List<Item> parse(String xmlToParse) throws XmlPullParserException , IOException, ParseException {
		Log.d(getClass().getCanonicalName(), "parse()");
		try{
			XmlPullParser parser = Xml.newPullParser();
			//we don't use namespaces
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			//parser.setInput(in,null);
			parser.setInput(new StringReader(xmlToParse));

			parser.nextTag();
			//Log.d(getClass().getName()," Start Tag "+parser.getName());

			return parseContent(parser);

		}catch(Exception e){
			Log.d(TAG,"Error while parsing "+e);
		}
		return null;
	}
	public List<Item> parseContent(XmlPullParser parser) throws XmlPullParserException, IOException{
		//Log.d(TAG,"method parseContent()");
		List<Item> items = new ArrayList<Item>();
		// find the tag <table>
		while(!"table".equalsIgnoreCase(parser.getName())){
			parser.next();
			//Log.d(TAG, "passing the tag === "+parser.getName());
		}
		items.add(parseTable(parser));
		return items;
	}
	//find the tag <tr> and compare the attribute value "class"
	public Item parseTable(XmlPullParser parser) throws XmlPullParserException, IOException{
		//Log.d(TAG,"method parseContent()");
		
		//parser.nextTag();
		//Log.d(TAG,"method parseContent() the nextTag "+parser.getName()+" and attribute value " +parser.getAttributeValue(null, "class"));
		parser.require(XmlPullParser.START_TAG, null, "table");
		//Log.d(TAG,"Start Tag "+parser.getName());
		//Log.d(TAG,"Attribute "+parser.getAttributeValue(null, "class"));
		String td = "td";
		String author = null;
		String type = null;
		String URL = null;
		String abctractNote=null;
		while(parser.next()!=XmlPullParser.END_DOCUMENT){

			//Log.d(TAG, "passing the tag === "+parser.getName());
			if("tr".equalsIgnoreCase(parser.getName()) && "creator".equalsIgnoreCase(parser.getAttributeValue(null, "class"))){
				//Log.d(TAG,"method parseContent() find creator======  "+parser.getAttributeValue(null, "class"));
				while(!td.equals(parser.getName())){
					parser.next();
					//Log.d(TAG,"The tag name inside tr: "+parser.getName());
				}
				parser.next();
				author = parser.getText();
				//Log.d(TAG,"The text of td is: ============"+parser.getText());
			}
			else if("tr".equalsIgnoreCase(parser.getName()) && "url".equalsIgnoreCase(parser.getAttributeValue(null, "class"))){
				//Log.d(TAG,"method parseContent() find url======  "+parser.getAttributeValue(null, "class"));
				while(!td.equals(parser.getName())){
					parser.next();
					//Log.d(TAG,"The tag name inside tr: "+parser.getName());
				}
				parser.next();
				URL = parser.getText();
				//Log.d(TAG,"The text of td is: ============"+parser.getText());
			}
			else if("tr".equalsIgnoreCase(parser.getName()) && "abstractNote".equalsIgnoreCase(parser.getAttributeValue(null, "class"))){
				//Log.d(TAG,"method parseContent() find abstract======  "+parser.getAttributeValue(null, "class"));
				while(!td.equals(parser.getName())){
					parser.next();
					//Log.d(TAG,"The tag name inside tr: "+parser.getName());
				}
				parser.next();
				abctractNote = parser.getText();
				//Log.d(TAG,"The text of td is: ============"+parser.getText());
			}
			else if("tr".equalsIgnoreCase(parser.getName()) && "itemType".equalsIgnoreCase(parser.getAttributeValue(null, "class"))){
				//Log.d(TAG,"method parseContent() find abstract======  "+parser.getAttributeValue(null, "class"));
				while(!td.equals(parser.getName())){
					parser.next();
					//Log.d(TAG,"The tag name inside tr: "+parser.getName());
				}
				parser.next();
				type = parser.getText();
				//Log.d(TAG,"The text of td is: ============"+parser.getText());
			}
		}

		return new Item(null,null,null,type,author,abctractNote,URL);
	}

}//end of class
