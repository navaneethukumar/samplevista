package com.spire.crm.entity.attachments.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.testng.Assert;

import spire.crm.profiles.entity.Attachment;

import com.spire.common.AssetFileDecode;
import com.spire.common.ProfileHelper;
import com.spire.common.TestData;
import com.spire.crm.restful.entity.consumers.AttachmentsEntityConsumer;

/**
 * @author Manaswini
 *
 */
public class AttachmentsEntityValidation {

	static DataFactory factory = new DataFactory();
	final static AttachmentsEntityConsumer attachmentsEntityConsumer = new AttachmentsEntityConsumer();
	public static Attachment attachment = null;

	/**
	 * CreateAsset
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static Attachment createAttachment(TestData data) throws IOException {
		String candidateId = ProfileHelper.createProfile();
		String path = "./src/main/resources/SampleResumeFile.doc";
		File file;
		final FormDataContentDisposition fileDetail = FormDataContentDisposition
				.name(factory.getRandomWord() + System.currentTimeMillis())
				.fileName(path).build();
		file = new File(fileDetail.getFileName());
		InputStream uploadInputStream = new FileInputStream(file);
		MultiPart multiPart = new MultiPart();
		FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file",
				new File(fileDetail.getFileName()),
				MediaType.APPLICATION_OCTET_STREAM_TYPE);

		multiPart.bodyPart(fileDataBodyPart);
		multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
		attachment = attachmentsEntityConsumer.createAttachment(
				fileDetail.getName(), candidateId, multiPart);
		File attachments = attachmentsEntityConsumer
				.getAttachmentById(attachment.getAttachmentid());
		validateCreateAttachment(fileDetail, file, attachments);
		List<Attachment> listAttachments = attachmentsEntityConsumer
				.listAttachments(attachment.getCandidateId());
		validateListAttachments(listAttachments);
		return attachment;
	}

	/**
	 * listAttachment
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static void listAttachmment(TestData data) throws IOException {
		attachment = AttachmentsEntityValidation.createAttachment(data);
	}

	/**
	 * updateAttachment
	 * 
	 * @param data
	 * @return labelResponse
	 * @throws IOException
	 */
	public static void updateAttachment(TestData data) throws IOException {
		attachment = AttachmentsEntityValidation.createAttachment(data);
		Attachment updateAttachment = new Attachment();
		updateAttachment.setName(factory.getRandomWord()
				+ System.currentTimeMillis());
		updateAttachment.setCandidateId(attachment.getCandidateId());
		updateAttachment.setAttachmentid(attachment.getAttachmentid());
		Response response = attachmentsEntityConsumer
				.updateAttachment(updateAttachment);
		Assert.assertEquals(response.getStatus(), 200,
				"Attachment is not updated");
		List<Attachment> attachments = attachmentsEntityConsumer
				.listAttachments(updateAttachment.getCandidateId());
		validateUpdateAttachments(updateAttachment, attachments);
	}

	/**
	 * deleteAttachment
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static void deleteAttachment(TestData data) throws IOException {
		attachment = AttachmentsEntityValidation.createAttachment(data);
		Response response = attachmentsEntityConsumer
				.deleteAttachment(attachment.getAttachmentid());
		Assert.assertEquals(response.getStatus(), 200,
				"Attachment is not deleted");
		List<Attachment> attachments = attachmentsEntityConsumer
				.listAttachments(attachment.getCandidateId());
		Assert.assertTrue(attachments.isEmpty(), "Attachment is not deleted");
	}

	/**
	 * getAttachmentsById
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static void getAttachmentsById(TestData data) throws IOException {
		attachment = AttachmentsEntityValidation.createAttachment(data);

	}

	/**
	 * getAttachmentsByLink
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static File getAttachmentsByLink(TestData data) throws IOException {
		return null;

	}

	/**
	 * validateCreateAsset
	 * 
	 * @param fileDetail
	 * @throws IOException
	 */
	public static void validateCreateAttachment(
			FormDataContentDisposition fileDetail, File file, File assets)
			throws IOException {
		Assert.assertNotNull(attachment.getAttachmentid(),
				"Attachment is not created");
		Assert.assertNotEquals(attachment.getSize(), "0",
				"creating attachment but file is not uploading");
		AssetFileDecode assetFileDecode = new AssetFileDecode();
		File decodeFile = assetFileDecode.decodeBase64File(assets);
		Assert.assertTrue(FileUtils.contentEquals(file, decodeFile),
				"The files differ!");
		Assert.assertEquals(FileUtils.readFileToString(file, "utf-8"),
				FileUtils.readFileToString(decodeFile, "utf-8"),
				"The files differ!");
	}

	/**
	 * validateListAttachments
	 * 
	 * @param assetId
	 * @param assets
	 */
	public static void validateListAttachments(List<Attachment> attachments) {
		for (Attachment attachment1 : attachments) {
			Assert.assertEquals(attachment.getAttachmentid(),
					attachment1.getAttachmentid(),
					"Showing wrong attachment details");
			Assert.assertEquals(attachment.getName(), attachment1.getName(),
					"Showing wrong attachment details");

		}
	}

	/**
	 * validateListAttachments
	 * 
	 * @param assetId
	 * @param assets
	 */
	public static void validateUpdateAttachments(Attachment updateAttachment,
			List<Attachment> attachments) {
		for (Attachment attachment1 : attachments) {
			Assert.assertEquals(updateAttachment.getAttachmentid(),
					attachment1.getAttachmentid(),
					"Showing wrong attachment details");
			Assert.assertEquals(updateAttachment.getName(),
					attachment1.getName(), "Showing wrong attachment details");

		}
	}
}
