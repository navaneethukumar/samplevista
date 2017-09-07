package com.spire.crm.entity.cache.lib.test;

import spire.commons.cache.service.CacheProvider;
import spire.commons.data.service.DataProvider;
import spire.commons.data.service.handlers.AbstractDataServiceHandler;
import spire.talent.entity.demandservice.beans.RequisitionBean;

/**
 * Created by Manikanta on 21/12/16.
 */


public class RequistionsAbstractServiceHandler extends
		AbstractDataServiceHandler<RequisitionBean> {

	public RequistionsAbstractServiceHandler(CacheProvider cacheProvider,
			DataProvider dataStore) {
		super(cacheProvider, dataStore);
	}
}
