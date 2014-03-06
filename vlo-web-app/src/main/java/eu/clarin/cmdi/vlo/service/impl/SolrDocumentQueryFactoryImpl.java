/*
 * Copyright (C) 2014 CLARIN
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.clarin.cmdi.vlo.service.impl;

import eu.clarin.cmdi.vlo.FacetConstants;
import eu.clarin.cmdi.vlo.pojo.QueryFacetsSelection;
import eu.clarin.cmdi.vlo.service.SolrDocumentQueryFactory;
import org.apache.solr.client.solrj.SolrQuery;

/**
 *
 * @author twagoo
 */
public class SolrDocumentQueryFactoryImpl extends AbstractSolrQueryFactory implements SolrDocumentQueryFactory {
    
    @Override
    public SolrQuery createDocumentQuery(QueryFacetsSelection selection, int first, int count) {
        // make a query to get all documents that match the selection criteria
        final SolrQuery query = getDefaultDocumentQuery();
        // apply selection
        addQueryFacetParameters(query, selection);
        // set offset and limit
        query.setStart(first);
        query.setRows(count);
        return query;
    }
    
    @Override
    public SolrQuery createDocumentQuery(String docId) {
        // make a query to look up a specific document by its ID
        final SolrQuery query = getDefaultDocumentQuery();
        // consider all documents
        query.setQuery(SOLR_SEARCH_ALL);
        // filter by ID
        query.addFilterQuery(createFilterQuery(FacetConstants.FIELD_ID, docId));
        // one result max
        query.setRows(1);
        return query;
        
    }
    
    private SolrQuery getDefaultDocumentQuery() {
        SolrQuery query = new SolrQuery();
        query.setFields(FacetConstants.FIELD_NAME, 
                FacetConstants.FIELD_ID, 
                FacetConstants.FIELD_DESCRIPTION, 
                FacetConstants.FIELD_COLLECTION,
                FacetConstants.FIELD_RESOURCE,
                FacetConstants.FIELD_LANDINGPAGE);
        query.setSort(SolrQuery.SortClause.asc(FacetConstants.FIELD_NAME));
        return query;
    }
}
