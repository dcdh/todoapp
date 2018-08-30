package com.damdamdeo.todoapp.search.infra;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

@ApplicationScoped
public class SolrConfiguration {

	@Inject
	@ConfigurationValue("solr.baseSolrUrl")
	private String baseSolrUrl;

	@Produces
	public SolrClient produceSolrClient() {
		return new HttpSolrClient.Builder()
				.withBaseSolrUrl(baseSolrUrl)
				.build();
	}

}
