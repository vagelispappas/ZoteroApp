package com.example.zoteroapp.model;

import java.io.Serializable;

import android.util.Log;

import com.example.zoteroapp.R;


public class Item implements Serializable {
	/**
	 * 
	 */
	//this is semi-auto-generated serial version number
	private static final long serialVersionUID = 6256705452261647692L;


	private  String title;
	private  String published;
	private  String zapi_key;
	private  String itemType;
	private  String author;
	private  String abstractNote;
	private  String url;
	private  String childrenNum;
	private  String date_added;
	private  String DOI;
	private  String linkMode;
	private  String contentType;
	private  String charset;
	private  String filename;
	private  String firstName;
	private  String lastName;
	private  String parentCollectionKey;
	private  String numberOfCollections;
	
	private  String ISSN;
	private  String ISBN;
	private  String pages;
	private  String publisher;
	
	private  String shortTitle;
	private  String date_modified;
	private  String blog_title;
	
	public  boolean isOpened;
	public  int level=0;
	public  int hasChild;
	
	Item item;

	//default constructor
	public Item(){

	}
	
	public Item(String title , String key , String numOfCollection , String parentCollection){
		
		this.setTitle(title);
		this.setZapi_key(key);
		this.setNumberOfCollections(numOfCollection);
		this.setParentCollectionKey(parentCollection);
		
	}

	public Item(String title , String published, String key,String type,String s,String author){
		this.setTitle(title);
		this.setPublished(published);
		this.setZapi_key(key);
		this.setItemType(type);
		this.setChildrenNum(s);
		this.setAuthor(author);
		
	
	}
	
	
	public Item(String title , String published , String key , String itemType, String author , 
			String abstractNote,String url) {
		this.setTitle(title);
		this.setPublished(published);
		this.setZapi_key(key);
		this.setItemType(itemType);
		this.setAuthor(author);
		this.setAbstractNote(abstractNote);
		this.setUrl(url);

	}
	public Item(String title , String type , String date_Added , String key , String children , String firstName , String lastName,String doi , String url , 
			String linkMode , String contentType , String charSet , String filename,String abstractNote , String ISSN , String ISBN , String pages , String publisher,String blog_title , 
			String short_title , String date_modified){
		this.setItemType(type);
		this.setDate_added(date_Added);
		this.setZapi_key(key);
		this.setChildrenNum(children);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDOI(doi);
		this.setUrl(url);
		this.setLinkMode(linkMode);
		this.setCharset(charSet);
		this.setContentType(contentType);
		this.setFilename(filename);
		this.setAbstractNote(abstractNote);
		this.setTitle(title);
		this.setISSN(ISSN);
		this.setISBN(ISBN);
		this.setPublisher(publisher);
		this.setPages(pages);
		this.setBlog_title(blog_title);
		this.setShortTitle(short_title);
		this.setDate_modified(date_modified);
		
		
	}
	public int connecterRetrieve(String s){
		
		s = getChildrenNum();
		
		if(!"0".equals(s)){
			
			return R.drawable.clip;
		}
		
		
		return 0;
	}

	public int imageRetriever(String type){
			
		type = getItemType();
		
		String message = " Eimai mesa sthn imageRtriver() ";
		Log.d(getClass().getSimpleName(), message );
		
		Log.d(getClass().getSimpleName()," To type einai "+type);

		
		if (type == null || type.equals(""))
			return R.drawable.folder;
		
		if (type.equals("artwork"))
			return R.drawable.picture;
		// if (type.equals("audioRecording")) return
		// R.drawable.ic_menu_close_clear_cancel;
		// if (type.equals("bill")) return
		// R.drawable.ic_menu_close_clear_cancel;
		// if (type.equals("blogPost")) return
		// R.drawable.ic_menu_close_clear_cancel;
		if (type.equals("book"))
			return R.drawable.book;
		if (type.equals("bookSection"))
			return R.drawable.book_open;
		// if (type.equals("case")) return
		// R.drawable.ic_menu_close_clear_cancel;
		// if (type.equals("computerProgram")) return
		// R.drawable.ic_menu_close_clear_cancel;
		// if (type.equals("conferencePaper")) return
		// R.drawable.ic_menu_close_clear_cancel;
		if (type.equals("dictionaryEntry"))
			return R.drawable.page_white_width;
		if (type.equals("document"))
			return R.drawable.page_white;
		// if (type.equals("email")) return
		// R.drawable.ic_menu_close_clear_cancel;
		if (type.equals("encyclopediaArticle"))
			return R.drawable.page_white_text_width;
		if (type.equals("film"))
			return R.drawable.film;
		// if (type.equals("forumPost")) return
		// R.drawable.ic_menu_close_clear_cancel;
		// if (type.equals("hearing")) return
		// R.drawable.ic_menu_close_clear_cancel;
		if (type.equals("instantMessage"))
			return R.drawable.comment;
		// if (type.equals("interview")) return
		// R.drawable.ic_menu_close_clear_cancel;
		if (type.equals("journalArticle"))
			return R.drawable.page_white_text;
		if (type.equals("letter"))
			return R.drawable.email;
		if (type.equals("magazineArticle"))
			return R.drawable.layout;
		if (type.equals("manuscript"))
			return R.drawable.script;
		if (type.equals("map"))
			return R.drawable.map;
		if (type.equals("newspaperArticle"))
			return R.drawable.newspaper;
		// if (type.equals("patent")) return
		// R.drawable.ic_menu_close_clear_cancel;
		// if (type.equals("podcast")) return
		// R.drawable.ic_menu_close_clear_cancel;
		if (type.equals("presentation"))
			return R.drawable.page_white_powerpoint;
		// if (type.equals("radioBroadcast")) return
		// R.drawable.ic_menu_close_clear_cancel;
		if (type.equals("report"))
			return R.drawable.report;
		// if (type.equals("statute")) return
		// R.drawable.ic_menu_close_clear_cancel;
		if (type.equals("thesis"))
			return R.drawable.report_user;
		if (type.equals("tvBroadcast"))
			return R.drawable.television;
		if (type.equals("videoRecording"))
			return R.drawable.film;
		if (type.equals("webpage"))
			return R.drawable.page;

		// Not item types, but still something
		if (type.equals("collection"))
			return R.drawable.folder;
		 if (type.equals("application/pdf"))
			 return R.drawable.page_white_acrobat;
		 if (type.equals("note"))
			 return R.drawable.note;
		 
		 if(type.equals("blogPost"))
			 return R.drawable.layout;
		// if (type.equals("snapshot")) return
		// R.drawable.ic_menu_close_clear_cancel;
		// if (type.equals("link")) return
		// R.drawable.ic_menu_close_clear_cancel;

		// Return something generic if all else fails
		return R.drawable.page_white;
		
		

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getZapi_key() {
		return zapi_key;
	}

	public void setZapi_key(String zapi_key) {
		this.zapi_key = zapi_key;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAbstractNote() {
		return abstractNote;
	}

	public void setAbstractNote(String abstractNote) {
		this.abstractNote = abstractNote;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate_added() {
		return date_added;
	}

	public void setDate_added(String date_added) {
		this.date_added = date_added;
	}

	public String getDOI() {
		return DOI;
	}

	public void setDOI(String dOI) {
		DOI = dOI;
	}

	public String getLinkMode() {
		return linkMode;
	}

	public void setLinkMode(String linkMode) {
		this.linkMode = linkMode;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(String childrenNum) {
		this.childrenNum = childrenNum;
	}

	public String getParentCollectionKey() {
		return parentCollectionKey;
	}

	public void setParentCollectionKey(String parentCollectionKey) {
		this.parentCollectionKey = parentCollectionKey;
	}

	public String getNumberOfCollections() {
		return numberOfCollections;
	}

	public void setNumberOfCollections(String numberOfCollections) {
		this.numberOfCollections = numberOfCollections;
	}

	public String getISSN() {
		return ISSN;
	}

	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getDate_modified() {
		return date_modified;
	}

	public void setDate_modified(String date_modified) {
		this.date_modified = date_modified;
	}

	public String getBlog_title() {
		return blog_title;
	}

	public void setBlog_title(String blog_title) {
		this.blog_title = blog_title;
	}

}
