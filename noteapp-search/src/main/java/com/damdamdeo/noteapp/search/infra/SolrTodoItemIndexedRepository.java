package com.damdamdeo.noteapp.search.infra;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;

import com.damdamdeo.noteapp.search.domain.DefaultTodoItem;
import com.damdamdeo.noteapp.search.domain.TodoItemIndexedRepository;

@Dependent
public class SolrTodoItemIndexedRepository implements TodoItemIndexedRepository {

	@Inject
	private SolrClient solrClient;

	@Override
	public List<DefaultTodoItem> search(final String description) {
		final SolrQuery solrQuery = new SolrQuery();
		solrQuery.setHighlight(true).setHighlightSnippets(1);
		solrQuery.setParam("hl.fl", "*");
		solrQuery.setParam("hl.fragsize", "0");
		solrQuery.addSort("score", ORDER.desc);
		final String wordsQuery = Arrays.asList(description.split(" "))
				.stream()
				.map(word -> word + "~1")
				.collect(Collectors.joining(" OR "));
		solrQuery.setQuery(String.format("description_txt_fr:(%s)", wordsQuery));
		try {
			return solrClient.query(solrQuery)
					.getResults()
					.stream()
					.map(solrDocument -> new DocumentObjectBinder().getBean(DefaultTodoItem.class, solrDocument))
					.collect(Collectors.toList());
		} catch (IllegalStateException | SolrServerException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DefaultTodoItem findByTodoId(final String todoId) {
		try {
			return Optional.ofNullable(solrClient.getById(todoId))
					.map(solrDocument -> new DocumentObjectBinder().getBean(DefaultTodoItem.class, solrDocument))
					.orElseThrow(() -> new IllegalStateException(String.format("Unknown todo item '%s'", todoId)));
		} catch (IllegalStateException | SolrServerException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DefaultTodoItem save(final DefaultTodoItem todoItem) {
		try {
			solrClient.addBean(todoItem);
			solrClient.commit();
			return todoItem;
		} catch (IOException | SolrServerException e) {
			throw new RuntimeException(e);
		}
	}

}
