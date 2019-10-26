package com.guicedee.guicedservlets.rest;

import com.google.inject.Singleton;
import org.apache.cxf.jaxrs.lifecycle.PerRequestResourceProvider;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.logger.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class GuicedCXFNonSpringJaxrsServlet extends CXFNonSpringJaxrsServlet {
	private static final Logger log = LogFactory.getLog("GuicedCXFNonSpringJaxrsServlet");




	protected Map<Class<?>, java.util.Map<String, List<String>>> getServiceClasses(ServletConfig servletConfig, boolean modelAvailable, String splitChar) throws
			ServletException {
		String serviceBeans = RESTContext.renderPathServices();
		if (serviceBeans == null) {
			if (modelAvailable) {
				return Collections.emptyMap();
			} else {
				return new HashMap<>();
			}
		} else {
			String[] classNames = serviceBeans.split(splitChar);
			Map<Class<?>, Map<String, List<String>>> map = new HashMap<>();
			int len$ = classNames.length;
			for(int i$ = 0; i$ < len$; ++i$) {
				String cName = classNames[i$];
				Map<String, List<String>> props = new HashMap<>();
				String theName = this.getClassNameAndProperties(cName, props);
				if (theName.length() != 0) {
					Class<?> cls = this.loadClass(theName);
					map.put(cls, props);
				}
			}
			if (map.isEmpty()) {
				log.warning("No JaxRS Resource Class was found");

			}
			return map;
		}
	}


	protected Map<Class<?>, ResourceProvider> getResourceProviders(ServletConfig servletConfig, Map<Class<?>, Map<String, List<String>>> resourceClasses) throws ServletException {
		String scope = servletConfig.getServletContext().getInitParameter("jaxrs.scope");
		if (scope != null && !"singleton".equals(scope) && !"prototype".equals(scope)) {
			throw new ServletException("Only singleton and prototype scopes are supported");
		} else {
			boolean isPrototype = "prototype".equals(scope);
			Map<Class<?>, ResourceProvider> map = new HashMap();
			Iterator i$ = resourceClasses.entrySet().iterator();

			while(i$.hasNext()) {
				Map.Entry<Class<?>, Map<String, List<String>>> entry = (Map.Entry)i$.next();
				Class<?> c = entry.getKey();
				map.put(c, isPrototype ? new PerRequestResourceProvider(c) : new SingletonResourceProvider(this.createSingletonInstance(c, (Map)entry.getValue(), servletConfig), true));
			}
			return map;
		}
	}


	protected Object createSingletonInstance(Class<?> cls, Map<String, List<String>> props, ServletConfig sc) throws ServletException {
		try {
			return GuiceContext.get(cls);
		}catch(Throwable T)
		{
			log.log(Level.SEVERE,"Unable to construct instance for cxf",T);
			return null;
		}
	}

	private String getClassNameAndProperties(String cName, Map<String, List<String>> props) {
		String theName = cName.trim();
		int ind = theName.indexOf("(");
		if (ind != -1 && theName.endsWith(")")) {
			props.putAll(parseMapListSequence(theName.substring(ind + 1, theName.length() - 1)));
			theName = theName.substring(0, ind).trim();
		}
		return theName;
	}
/*
	protected Class<?> loadClass(String cName, String classType) throws ServletException {
		if(cName == null)
			return null;
		return GuiceContext.instance()
						   .getScanResult()
						   .loadClass(cName, true);
	}*/
}