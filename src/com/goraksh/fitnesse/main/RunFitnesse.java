package com.goraksh.fitnesse.main;

import fitnesse.ComponentFactory;
import fitnesse.FitNesse;
import fitnesse.FitNesseContext;
import fitnesse.wiki.FileSystemPage;

public class RunFitnesse {

	public static void main(String args[]) throws Exception {
		RunFitnesse runFitnesse = new RunFitnesse();
		//runFitnesse.start();
	}
/*
	public void start() throws Exception {
		FitNesseContext context = loadContext();
		FitNesse fitnesse = new FitNesse(context);
		fitnesse.applyUpdates();
		boolean started = fitnesse.start();
		if (started)
			//printStartMessage(context);
			System.out.println("started");
	} */

	/*
	 * http://johannesbrodwall.com/2008/03/19/some-fitnesse-tricks-classpath-and-debugging/
	 * http://www.fitnesse.org/FitNesse.UserGuide.LogFiles
	 * 
	protected FitNesseContext loadContext() throws Exception {
		FitNesseContext context = new FitNesseContext();
		ComponentFactory componentFactory = new ComponentFactory(
				context.rootPath);
		context.port = 80;
		context.rootPath = "./src/main/fitnesse";
		context.rootPagePath = "FitNesseRoot";
		;
		context.rootPagePath = context.rootPath + "/" + context.rootPageName;
		context.root = componentFactory.getRootPage(FileSystemPage.makeRoot(
				context.rootPath, context.rootPageName));
		context.responderFactory = new ResponderFactory(context.rootPagePath);
		context.logger = null;
		context.authenticator = componentFactory
				.getAuthenticator(new PromiscuousAuthenticator());
		context.htmlPageFactory = componentFactory
				.getHtmlPageFactory(new HtmlPageFactory());

		context.responderFactory.addResponder("test",
				InheritClasspathTestResponder.class);

		String extraOutput = componentFactory
				.loadResponderPlugins(context.responderFactory);
		extraOutput += componentFactory.loadWikiWidgetPlugins();
		extraOutput += componentFactory.loadContentFilter();
		return context;
	}
	*/
}
