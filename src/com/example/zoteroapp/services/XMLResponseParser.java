package com.example.zoteroapp.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.net.ParseException;
import android.util.Log;
import android.util.Xml;

import com.example.zoteroapp.model.Item;

//This class use XMLPullParse approach for handling the XML responses
public class XMLResponseParser {

	//field with namespaces
	static final  String ATOM_NAMESPACE = "http://www.w3.org/2005/Atom";
	static final String Z_NAMESPACE = "http://zotero.org/ns/api";
	//field , if we do not use namespaces
	static final String ns = null;

	private static final int TAG_TITLE= 1;
	private static final int TAG_PUBLISHED= 2;
	private static final int TAG_KEY=3;
	private static final int TAG_TYPE=4;
	private static final int TAG_CHILD=5;
	private static final int TAG_AUTHOR=6;



	InputStream input;

	public XMLResponseParser() {
		// TODO Auto-generated constructor stub
	}

	//parse an atom feed , return a list of item objects
	//@param atom feed as an inputstream
	//InputStream in
	public List<Item> parse(String xmlToParse) throws XmlPullParserException , IOException, ParseException {
		Log.d(getClass().getCanonicalName(), "parse()");
		try{
			XmlPullParser parser = Xml.newPullParser();
			//don't use namespaces
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			//parser.setInput(in,null);
			parser.setInput(new StringReader(xmlToParse));
			parser.nextTag();
			return readFeed(parser);
		}finally {
			//in.close();
			Log.d(getClass().getCanonicalName(), "parse the atom feed");
		}

	}
	//decode a feed attached to an xmlpullparser
	//return a list of items
	private List<Item> readFeed(XmlPullParser parser)throws XmlPullParserException , IOException, ParseException{
		List<Item> items = new ArrayList<Item>();

		parser.require(XmlPullParser.START_TAG, ns, "feed");

		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals("entry")){
				items.add(readItem(parser));
			}
			else{
				skip(parser);
			}
		}
		return items;
	}
	//parses the content of an entry 
	private Item readItem(XmlPullParser parser)throws XmlPullParserException , IOException, ParseException {
		parser.require(XmlPullParser.START_TAG, ns, "entry");
		String title = null;
		String key = null;
		String publish = null;
		String type = null;
		String children = null;
		String author = null;

		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if(name.equals("title")){
				Log.i(getClass().getSimpleName(),"i found TITLE label" );
				title = readTag(parser,TAG_TITLE);

			}
			else if(name.equals("zapi:key")){
				Log.i(getClass().getSimpleName(),"i found zapi KEY label" );
				key = readTag(parser, TAG_KEY);

			}
			else if(name.equals("zapi:itemType")){
				Log.i(getClass().getSimpleName(),"i found zapi ITEMTYPE label" );

				type = readTag(parser, TAG_TYPE);

			}
			else if(name.equals("zapi:numCollections")){
				Log.i(getClass().getSimpleName(),"i found zapi childNum label" );

				children = readTag(parser, TAG_CHILD);

			}
			else if(name.equals("zapi:creatorSummary")){
				Log.i(getClass().getSimpleName(),"i found zapi AUTHOR label" );

				author = readTag(parser, TAG_AUTHOR);

			}
			
			else if(name.equals("published")){
				//Time t = new Time();
				//t.parse3339(readTag(parser,TAG_PUBLISHED));
				//publish =  t.toMillis(true);
				publish = readTag(parser, TAG_PUBLISHED);
				Log.i(getClass().getSimpleName(),"i found PUBLISHED label" );
			}
			//else if(name.equals("zapi:itemType")){
			//key = readTag(parser, TAG_TYPE);
			//}
			else{
				skip(parser);
			}
		}
		return new Item(title,publish,key, type,children,author); 

	}
	//read the value of a tag
	private String readTag(XmlPullParser parser , int tagType)
			throws XmlPullParserException , IOException, ParseException{
		@SuppressWarnings("unused")
		String tag = null;
		@SuppressWarnings("unused")
		String endtag=null;

		switch(tagType){
		case TAG_TITLE:
			return readBasicTag(parser,"title");
		case TAG_PUBLISHED:
			return readBasicTag(parser,"published");
		case TAG_KEY:
			return readBasicTag(parser, "zapi:key");
		case TAG_TYPE:
			return readBasicTag(parser,"zapi:itemType");
		case TAG_CHILD:
			return readBasicTag(parser, "zapi:numCollections");
		case TAG_AUTHOR:
			return readBasicTag(parser, "zapi:creatorSummary");
			
		default:
			throw new IllegalArgumentException("unknown tag type"+tagType);
		}
	}
	//read a basic tag without nested elements
	private String readBasicTag(XmlPullParser parser, String tag)
			throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, tag);
		String result = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, tag);
		return result;
	}
	//extract the text value of a tag
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = null;
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			Log.i(getClass().getSimpleName(),"Here is the TEXT "+result );
			parser.nextTag();
		}
		return result;
	}

	/**
	 * Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
	 * if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
	 * finds the matching END_TAG (as indicated by the value of "depth" being 0).
	 */
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
	//read link tags in a feed
	@SuppressWarnings("unused")
	private String readAlternateLink(XmlPullParser parser) throws IOException, XmlPullParserException{
		String link = null;
		parser.require(XmlPullParser.START_TAG, ns, "link");
		String tag = parser.getName();
		String relType = parser.getAttributeValue(null, "rel");
		if(relType.equals("alternate")||relType.equals("self")||relType.equals("self")){
			link = parser.getAttributeValue(null, "href");
		}
		while(true){
			if(parser.nextTag()==XmlPullParser.END_TAG)
				break;
		}
		return link;
	}


}
