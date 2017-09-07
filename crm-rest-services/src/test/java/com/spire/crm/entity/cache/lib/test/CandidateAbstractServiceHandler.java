package com.spire.crm.entity.cache.lib.test;

import spire.commons.cache.service.CacheProvider;
import spire.commons.data.service.DataProvider;
import spire.commons.data.service.handlers.AbstractDataServiceHandler;
import spire.match.entities.CandidateExt;

/**
 * Created by Manikanta on 21/12/16.
 */


public class CandidateAbstractServiceHandler extends
		AbstractDataServiceHandler<CandidateExt> {

	public CandidateAbstractServiceHandler(CacheProvider cacheProvider,
			DataProvider dataStore) {
		super(cacheProvider, dataStore);
	}
}
