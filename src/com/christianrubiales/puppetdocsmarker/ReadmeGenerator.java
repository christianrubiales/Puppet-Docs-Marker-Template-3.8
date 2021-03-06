package com.christianrubiales.puppetdocsmarker;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ReadmeGenerator {

	static String HEADER = "# Puppet Docs Marker 3.8\n"
			+ "Mark what sections you have read from the Puppet Docs 3.8\n\n";
	static String BASE = "https://puppet.com";

	public static void main(String[] args) throws Exception {
		System.out.println(HEADER);
		File file = new File("./index.html");
		Document doc = Jsoup.parse(file, "UTF-8");
		Element main = doc.selectFirst(".documentation-navigation");
		System.out.println("# " + main.selectFirst("h3").text() + "\n");
		main = doc.selectFirst("ul");
		
		for (Element heading : main.children()) {
			if (heading.hasClass("hidden-nav")) {
				System.out.println("## " + heading.selectFirst("strong").text());
			} else {
				Element tag = heading.selectFirst("a");
				System.out.println("## [" + tag.text() + "](" + processLink(tag.attr("href")) + ")");
			}
			
			Element first = heading.selectFirst("ul");
			if (first != null) {
				for (Element li : first.children()) {
					Element second = li.selectFirst("ul");
					if (second == null) {
						Element firstTag = li.selectFirst("a");
						System.out.println("- [ ] [" + firstTag.text() + "]"
								+ "(" + processLink(firstTag.attr("href")) + ")");
					} else {
						System.out.println();
						System.out.println("### " + li.selectFirst("strong").text());
	
						for (Element secondLi : second.getElementsByTag("li")) {
							Element secondTag = secondLi.selectFirst("a");
							System.out.println("- [ ] [" + secondTag.text() + "]"
									+ "(" + processLink(secondTag.attr("href")) + ")");
						}
						System.out.println("## ");
					}
				}
			}
			System.out.println();
		}
	}
	
	static String processLink(String href) {
		return BASE + href;
	}

}
