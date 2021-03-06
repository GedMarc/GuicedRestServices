package com.guicedee.guicedservlets.rest;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import com.fasterxml.jackson.jakarta.rs.json.JacksonXmlBindJsonProvider;
import com.guicedee.guicedservlets.rest.implementations.JAXBMarshaller;

import java.util.*;

public class RESTContext
{
	private static final List<String> pathServices = new ArrayList<>();
	private static final List<String> providers = new ArrayList<>(List.of(JacksonJsonProvider.class.getCanonicalName(),
	                                                                   JacksonXmlBindJsonProvider.class.getCanonicalName(),
	                                                                   JAXBMarshaller.class.getCanonicalName(),
	                                                                   "org.apache.cxf.jaxrs.provider.JAXBElementProvider",
	                                                                   "org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInvoker",
	                                                                   "com.guicedee.guicedservlets.rest.services.JavaTimeTypesParamConverterProvider",
	                                                                   "org.apache.cxf.jaxrs.validation.JAXRSParameterNameProvider"));
	private static final List<String> inInterceptors = new ArrayList<>(List.of("org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor"
	                                                                       ));
	private static final List<String> outInterceptors = new ArrayList<>();
	private static final List<String> outFaultInterceptors = new ArrayList<>();
	private static final List<String> properties = new ArrayList<>();
	private static final List<String> features = new ArrayList<>();
	private static final List<String> applications = new ArrayList<>();

	private static boolean useSaml = false;
	private static boolean useAtom = false;
	private static boolean useAegis = false;


	/**
	 * Provides the url that the module will use to provide Web Services.
	 * Does not default to module name, default to "rest"
	 * <p>
	 *
	 * <p>
	 * e.g. http://localhost/WebServices/helloworld
	 */
	public static String baseWSUrl = "rest";
	/**
	 * Whether to register all the providers found on the path.. warning - this has a tendency to enable oauth everywhere
	 */
	public static boolean autoRegisterProviders = false;

	public static String renderServices(Collection<String> values)
	{
		StringBuilder sb = new StringBuilder();
		for (String pathService : values)
		{
			sb.append(pathService + ",");
		}
		if (!values.isEmpty())
		{
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static List<String> getPathServices()
	{
		return pathServices;
	}

	public static List<String> getFeatures()
	{
		return features;
	}

	public static List<String> getProviders()
	{
		return providers;
	}

	public static List<String> getInInterceptors()
	{
		return inInterceptors;
	}

	public static List<String> getOutInterceptors()
	{
		return outInterceptors;
	}

	public static List<String> getProperties()
	{
		return properties;
	}

	public static List<String> getApplications()
	{
		return applications;
	}

	public static boolean isUseSaml() {
		return useSaml;
	}

	public static void setUseSaml(boolean useSaml) {
		RESTContext.useSaml = useSaml;
	}

	public static boolean isUseAtom() {
		return useAtom;
	}

	public static void setUseAtom(boolean useAtom) {
		RESTContext.useAtom = useAtom;
	}

	public static boolean isUseAegis() {
		return useAegis;
	}

	public static void setUseAegis(boolean useAegis) {
		RESTContext.useAegis = useAegis;
	}

	/**
	 * Getter for property 'outFaultInterceptors'.
	 *
	 * @return Value for property 'outFaultInterceptors'.
	 */
	public static List<String> getOutFaultInterceptors()
	{
		return outFaultInterceptors;
	}
}
