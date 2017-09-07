package com.spire.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import spire.commons.labels.beans.Label;
import spire.commons.labels.common.CollectionEntity;

import com.google.gson.Gson;
import com.spire.crm.restful.entity.consumers.LabelsEntityServiceConsumer;

public class LabelHelper {

	String baseURl = "http://52.201.31.119:8182/spire-biz/labels-entity/api/labels";
	String removeEndPoint = baseURl + "/_list?isAllLabels=true";

	public void removeBulk(Label labelDetails) {
		LabelsEntityServiceConsumer labelEntityServiceConsumer = new LabelsEntityServiceConsumer();

		Label labelResponse = labelEntityServiceConsumer
				.listLabels_isAllLabels(labelDetails, "true", "1000", "0");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		labelEntityServiceConsumer.removeLabel(labelResponse);

	}

	public static void main(String args[]) {
		Gson gson = new Gson();
		Label labelDetails = new Label();
		String labelIdList = "E:/Project_Docs/LabelDs.csv";
		BufferedReader br = null;
		String line = null;
		List<String> labelidsList = new ArrayList<String>();
		LabelHelper obj = new LabelHelper();

		try {
			br = new BufferedReader(new FileReader(labelIdList));
			while ((line = br.readLine()) != null) {
				CollectionEntity<String> LabelIds = new CollectionEntity<String>();
				String data[] = line.split(",");

				labelidsList.add(data[0]);

				LabelIds.addItem(data[0]);

				labelDetails.setLabelIds(LabelIds);

				obj.removeBulk(labelDetails);

				System.out.println("Done >>>>" + gson.toJson(labelDetails));
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println("Done >>>>" + gson.toJson(labelidsList));
	}

}
